public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/

package com.ubuntu.onlineordersystemkafka;
import com.ubuntu.onlineordersystemkafka.OrderProducer;
import com.ubuntu.onlineordersystemkafka.InventoryUpdateProducer;
import com.ubuntu.onlineordersystemkafka.UserRegistrationProducer;
import com.ubuntu.onlineordersystemkafka.ShippingProducer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
/**
*
* @author toor
*/
public class OnlineOrderSystemKafka {

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       System.out.println("شروع سیستم مدیریت سفارشات آنلاین... 🚀");
       UserRegistrationProducer.sendUserRegistration();
       
       boolean userExists = false;
       String username = "";
       
       while (!userExists) {
           System.out.print("🔍 لطفاً دوباره نام کاربری خود را برای بررسی وارد کنید: ");
           username = scanner.nextLine();

           userExists = checkUserExists(username);

           if (!userExists) {
               System.out.println("❌ کاربر پیدا نشد. دوباره تلاش کنید.");
           }
       }

       System.out.println("✅ کاربر تأیید شد. ادامه‌ی مراحل در حال اجرا...");

       // ادامه فرآیندها بعد از تأیید کاربر
       OrderProducer.sendOrder();                 // سفارش
       PaymentProducer.sendPayment();             // پرداخت
       ShippingProducer.sendShipping();           // ارسال
       InventoryUpdateProducer.sendInventoryUpdate(); // آپدیت موجودی

       System.out.println("🎉 تمامی مراحل برای کاربر " + username + " با موفقیت انجام شد.");
   }

   private static boolean checkUserExists(String username) {
       String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
       try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setString(1, username);

           try (ResultSet rs = stmt.executeQuery()) {
               if (rs.next()) {
                   return rs.getInt(1) > 0;
               }
           }
       } catch (Exception e) {
           System.out.println("⚠️ خطا در بررسی کاربر: " + e.getMessage());
       }
       return false;
   }
       
}

{
    
}
