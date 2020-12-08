package io.keweishang.mysqlclient;

import java.sql.*;

public class MySQLAccess {
  private final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  public void readFromDataBase(String hostname, int port, String schema, String table)
      throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName(DRIVER);
      // Setup the connection with the DB
      connect =
          DriverManager.getConnection(
              "jdbc:mysql://" + hostname + ":" + port + "/" + schema + "?user=root");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement.executeQuery("select * from " + schema + "." + table);
      printResultSet(resultSet);

    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }
  }

  public void writeToDataBase(String hostname, int port, String schema, String table)
      throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName(DRIVER);
      // Setup the connection with the DB
      connect =
          DriverManager.getConnection(
              "jdbc:mysql://" + hostname + ":" + port + "/" + schema + "?user=root");

      // PreparedStatements can use variables and are more efficient
      preparedStatement =
          connect.prepareStatement(
              "insert into "
                  + schema
                  + "."
                  + table
                  + " (sku, description, price) values (?, ?, ?)");
      // Parameters start with 1
      preparedStatement.setString(1, "s18");
      preparedStatement.setString(2, "book");
      preparedStatement.setInt(3, 10);
      preparedStatement.executeUpdate();

    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }
  }

  private void printResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      String sku = resultSet.getString("sku");
      String description = resultSet.getString("description");
      Integer price = resultSet.getInt("price");
      System.out.println("sku: " + sku + " description: " + description + " price: " + price);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
}
