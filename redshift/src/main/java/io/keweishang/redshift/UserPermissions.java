package io.keweishang.redshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserPermissions implements Comparable<UserPermissions> {
  private String userName;
  private Integer userId;
  private String fullTableName;
  boolean hasSelectPermission;
  boolean hasInsertPermission;
  boolean hasDeletePermission;
  boolean hasUpdatePermission;

  public UserPermissions(
      String userName,
      Integer userId,
      String fullTableName,
      boolean hasSelectPermission,
      boolean hasInsertPermission,
      boolean hasDeletePermission,
      boolean hasUpdatePermission
      ) {
    this.userName = userName;
    this.userId = userId;
    this.fullTableName = fullTableName;
    this.hasSelectPermission = hasSelectPermission;
    this.hasInsertPermission = hasInsertPermission;
    this.hasDeletePermission = hasDeletePermission;
    this.hasUpdatePermission = hasUpdatePermission;
  }

  private int weight() {
    int res = 0;
    if (hasSelectPermission) res++;
    if (hasInsertPermission) res++;
    if (hasDeletePermission) res++;
    if (hasUpdatePermission) res++;
    return res;
  }

  public boolean hasAllPermissions() {
    return hasSelectPermission
        && hasInsertPermission
        && hasDeletePermission
        && hasUpdatePermission;
  }

  public boolean hasNoPermissions() {
    return !hasSelectPermission
        && !hasInsertPermission
        && !hasDeletePermission
        && !hasUpdatePermission;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserPermissions that = (UserPermissions) o;
    return hasSelectPermission == that.hasSelectPermission
        && hasInsertPermission == that.hasInsertPermission
        && hasDeletePermission == that.hasDeletePermission
        && hasUpdatePermission == that.hasUpdatePermission
        && userName.equals(that.userName)
        && userId.equals(that.userId)
        && fullTableName.equals(that.fullTableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        userName,
        userId,
        fullTableName,
        hasSelectPermission,
        hasInsertPermission,
        hasDeletePermission,
        hasUpdatePermission
        );
  }

  @Override
  public int compareTo(UserPermissions o) {
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
      return "GRANT ALL ON " + fullTableName + " TO " + userName + ";";
    } else {
      return "GRANT "
          + String.join(",", permissions)
          + " ON "
          + fullTableName
          + " TO "
          + userName
          + ";";
    }
  }
}
