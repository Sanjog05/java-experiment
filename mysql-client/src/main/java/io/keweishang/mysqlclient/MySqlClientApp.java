package io.keweishang.mysqlclient;

public class MySqlClientApp {
  public static void main(String[] args) throws Exception {
//    new MySQLAccess().readFromDataBase();
    new MySQLAccess().writeToDataBase("127.0.0.1", 15306, "commerce", "product");
  }
}
