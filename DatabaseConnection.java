public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
*
* @author toor
*/
public class DatabaseConnection {
   private static final String URL = "jdbc:mysql://localhost:3306/online_order_system";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "f1309D1309@";
   
   private static Connection connection;
   
   private DatabaseConnection(){}
   
   public static Connection getConnection() throws SQLException {
       if (connection == null || connection.isClosed()) {
           try {
               connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
               System.out.println("✅ اتصال به دیتابیس برقرار شد.");
           } catch (SQLException e) {
               System.out.println("❌ خطا در اتصال به دیتابیس: " + e.getMessage());
               throw e;
           }
       }
       return connection;
   }
   
   public static void closeConnection() {
       try {
           if (connection != null && !connection.isClosed()) {
               connection.close();
               System.out.println("🔒 اتصال به دیتابیس بسته شد.");
           }
       } catch (SQLException e) {
           System.out.println("❗ خطا در بستن اتصال دیتابیس: " + e.getMessage());
       }
   }
} {
    
}
