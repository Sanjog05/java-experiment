package io.keweishang.redshift;

import java.sql.*;
import java.util.*;

public class QueryTable {

  public static void main(String[] args) throws SQLException {
    DriverManager.registerDriver(new com.amazon.redshift.jdbc42.Driver());
    Properties driverProperties = new Properties();
    driverProperties.put("user", "foo");
    driverProperties.put("password", "bar");
    String redshiftUrl = "jdbc:redshift://hostname:5439/mydb";
    String fullTableName = "schemaName.tableName";

    try (Connection connection = DriverManager.getConnection(redshiftUrl, driverProperties);
        Statement stmt = connection.createStatement()) {

      // Get all users
      Map<String, Integer> users = new HashMap<>();
      ResultSet rs = stmt.executeQuery("select usename, usesysid from pg_user");
      while (rs.next()) {
        users.put(rs.getString(1), rs.getInt(2));
      }

      // Get all groups
      Map<String, List<Integer>> userIdsByGroup = new HashMap<>();
      rs = stmt.executeQuery("select groname, grolist from pg_group");
      while (rs.next()) {
        String groupName = rs.getString(1);
        Array array = rs.getArray(2);
        List<Integer> userIds = new ArrayList<>();
        if (array.toString() != "{}") {
          // if not empty
          Object[] userIdArray = (Object[]) array.getArray();
          for (Object userId : userIdArray) {
            userIds.add((Integer) userId);
          }
        } else {
          //          System.out.println(String.format("Group [%s] does not have any users",
          // groupName));
        }

        userIdsByGroup.put(groupName, userIds);
      }

      // Get each user's groups
      Map<String, List<String>> groupsByUser = new HashMap<>();
      for (Map.Entry<String, Integer> user : users.entrySet()) {
        String userName = user.getKey();
        Integer userId = user.getValue();

        groupsByUser.put(userName, new ArrayList<>());

        for (Map.Entry<String, List<Integer>> tuple : userIdsByGroup.entrySet()) {
          String groupName = tuple.getKey();
          List<Integer> userIds = tuple.getValue();
          if (userIds.contains(userId)) {
            groupsByUser.get(userName).add(groupName);
          }
        }
      }

      //      System.out.println(
      //          "Users who do not belong to a group: "
      //              + groupsByUser.keySet().stream()
      //                  .filter(userName -> groupsByUser.get(userName).isEmpty())
      //                  .collect(Collectors.toList()));

      List<UserPermissions> userPermissionsResult = new ArrayList<>();
      Map<String, GroupPermissions> groupPermissionsMap = new HashMap<>();
      for (Map.Entry<String, Integer> userEntry : users.entrySet()) {
        String userName = userEntry.getKey();
        Integer userId = userEntry.getValue();
        UserPermissions userPermissions = getUserPermissions(userName, userId, fullTableName, stmt);

        if (!userPermissions.hasNoPermissions()) {
          List<String> groups = groupsByUser.get(userName);
          if (groups.isEmpty()) {
            // user does not have groups, grant permission to the user directly
            userPermissionsResult.add(userPermissions);
          } else {
            // user has groups, add user permission to each of its groups
            for (String groupName : groups) {
              GroupPermissions groupPermissions = new GroupPermissions(groupName, fullTableName);
              if (groupPermissionsMap.containsKey(groupName)) {
                groupPermissions = groupPermissionsMap.get(groupName);
              }

              groupPermissions.addUserPermissions(userPermissions);
              groupPermissionsMap.put(groupName, groupPermissions);
            }
          }
        }
      }

      System.out.println(
          String.format(
              "Grant permission to [%s] to individual users: [%d]",
              fullTableName, userPermissionsResult.size()));

      System.out.println(
          String.format(
              "Grant permission to [%s] to groups: [%d]",
              fullTableName, groupPermissionsMap.size()));

      Collections.sort(userPermissionsResult);
      for (UserPermissions userPermissions : userPermissionsResult) {
        System.out.println(userPermissions);
      }

      List<GroupPermissions> groupPermissionsResult = new ArrayList<>(groupPermissionsMap.values());
      Collections.sort(groupPermissionsResult);
      for (GroupPermissions groupPermissions : groupPermissionsResult) {
        System.out.println(groupPermissions);
      }
    }
  }

  private static UserPermissions getUserPermissions(
      String userName, Integer userId, String fullTableName, Statement stmt) throws SQLException {
    ResultSet rs =
        stmt.executeQuery(
            "SELECT\n"
                + "    has_table_privilege('"
                + userName
                + "', '"
                + fullTableName
                + "', 'select') AS user_has_select_permission,\n"
                + "    has_table_privilege('"
                + userName
                + "', '"
                + fullTableName
                + "', 'insert') AS user_has_insert_permission,\n"
                + "    has_table_privilege('"
                + userName
                + "', '"
                + fullTableName
                + "', 'delete') AS user_has_delete_permission,\n"
                + "    has_table_privilege('"
                + userName
                + "', '"
                + fullTableName
                + "', 'update') AS user_has_update_permission;");
    while (rs.next()) {
      return new UserPermissions(
          userName,
          userId,
          fullTableName,
          rs.getBoolean(1),
          rs.getBoolean(2),
          rs.getBoolean(3),
          rs.getBoolean(4));
    }
    return null;
  }
}
