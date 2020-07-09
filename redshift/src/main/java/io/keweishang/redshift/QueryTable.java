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
        Array array = rs.getArray(2);
        List<Integer> userIds = new ArrayList<>();
        if (array.toString() != "{}") {
          // if not empty
          Object[] userIdArray = (Object[]) array.getArray();
          for (Object userId : userIdArray) {
            userIds.add((Integer) userId);
          }
        }

        userIdsByGroup.put(rs.getString(1), userIds);
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

      // Print out each user's groups
      for (Map.Entry<String, List<String>> tuple : groupsByUser.entrySet()) {
        String userName = tuple.getKey();
        List<String> groupNames = tuple.getValue();
        System.out.println(String.format("user: %s, groups: %s", userName, groupNames));
      }
    }
  }
}
