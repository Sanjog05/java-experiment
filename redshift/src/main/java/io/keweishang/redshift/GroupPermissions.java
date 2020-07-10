package io.keweishang.redshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupPermissions implements Comparable<GroupPermissions> {
  private String groupName;
  private String fullTableName;
  boolean hasSelectPermission;
  boolean hasInsertPermission;
  boolean hasUpdatePermission;
  boolean hasDeletePermission;

  public GroupPermissions(String groupName, String fullTableName) {
    this.groupName = groupName;
    this.fullTableName = fullTableName;
  }

  private int weight() {
    int res = 0;
    if (hasSelectPermission) res++;
    if (hasInsertPermission) res++;
    if (hasUpdatePermission) res++;
    if (hasDeletePermission) res++;
    return res;
  }

  public boolean hasAllPermissions() {
    return hasSelectPermission
        && hasDeletePermission
        && hasInsertPermission
        && hasUpdatePermission;
  }

  public void addUserPermissions(UserPermissions userPermissions) {
    if (userPermissions.hasUpdatePermission) hasUpdatePermission = true;
    if (userPermissions.hasInsertPermission) hasInsertPermission = true;
    if (userPermissions.hasDeletePermission) hasDeletePermission = true;
    if (userPermissions.hasSelectPermission) hasSelectPermission = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupPermissions that = (GroupPermissions) o;
    return hasSelectPermission == that.hasSelectPermission
        && hasInsertPermission == that.hasInsertPermission
        && hasUpdatePermission == that.hasUpdatePermission
        && hasDeletePermission == that.hasDeletePermission
        && groupName.equals(that.groupName)
        && fullTableName.equals(that.fullTableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        groupName,
        fullTableName,
        hasSelectPermission,
        hasInsertPermission,
        hasUpdatePermission,
        hasDeletePermission
    );
  }

  @Override
  public int compareTo(GroupPermissions o) {
    return this.weight() - o.weight();
  }

  @Override
  public String toString() {
    List<String> permissions = new ArrayList<>();
    if (hasSelectPermission) permissions.add("SELECT");
    if (hasInsertPermission) permissions.add("INSERT");
    if (hasDeletePermission) permissions.add("DELETE");
    if (hasUpdatePermission) permissions.add("UPDATE");

    if (hasAllPermissions()) {
      return "GRANT ALL ON " + fullTableName + " TO GROUP " + groupName + ";";
    } else {
      return "GRANT "
          + String.join(",", permissions)
          + " ON "
          + fullTableName
          + " TO GROUP "
          + groupName
          + ";";
    }
  }
}
