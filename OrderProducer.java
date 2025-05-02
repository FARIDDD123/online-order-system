public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;
import com.google.gson.Gson;
import com.ubuntu.onlineordersystemkafka.DatabaseConnection;
import com.ubuntu.onlineordersystemkafka.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

/**
*
* @author toor
*/
public class OrderProducer {
   private static final String TOPIC = "orders";

   public static void sendOrder() {
       Scanner scanner = new Scanner(System.in);

       System.out.println("📦 لطفاً اطلاعات سفارش را وارد کنید:");

       // Generate UUID for orderId
       String orderId = UUID.randomUUID().toString();
       System.out.println("🆔 شناسه سفارش به صورت خودکار ساخته شد: " + orderId);

       System.out.println("👤 شناسه کاربر (user id):");
       String userId = scanner.nextLine();

       // Check if user exists
       if (!isUserExists(userId)) {
           System.out.println("❌ کاربری با این شناسه وجود ندارد. لطفاً ابتدا ثبت‌نام کنید.");
           return; // Exit the method
       }

       System.out.println("🔢 مبلغ کل سفارش:");
       double totalAmount = Double.parseDouble(scanner.nextLine());

       // Create order object
       Order order = new Order(orderId, userId, totalAmount);

       Gson gson = new Gson();
       String orderJson = gson.toJson(order);

       // Kafka configuration
       Properties props = new Properties();
       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

       KafkaProducer<String, String> producer = new KafkaProducer<>(props);

       ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, order.getOrderId(), orderJson);

       producer.send(record, (RecordMetadata metadata, Exception exception) -> {
           if (exception == null) {
               System.out.println("✅ سفارش به Kafka ارسال شد: " + order.getOrderId());
           } else {
               System.out.println("❌ خطا در ارسال سفارش به Kafka: " + exception.getMessage());
           }
       });

       producer.flush();
       producer.close();

       // Save order to database
       saveOrderToDatabase(order);
   }

   private static boolean isUserExists(String userId) {
       String sql = "SELECT id FROM users WHERE id = ?";

       try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, userId);

           ResultSet rs = stmt.executeQuery();
           return rs.next(); // اگر رکوردی وجود داشت یعنی کاربر هست

       } catch (SQLException e) {
           System.out.println("❌ خطا در بررسی وجود کاربر: " + e.getMessage());
           return false;
       }
   }

   public static void saveOrderToDatabase(Order order) {
       String sql = "INSERT INTO orders (order_id, user_id, total_amount) VALUES (?, ?, ?)";

       try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, order.getOrderId());
           stmt.setString(2, order.getUserId());
           stmt.setDouble(3, order.getTotalAmount());

           stmt.executeUpdate();
           System.out.println("✅ سفارش در دیتابیس ذخیره شد: " + order.getOrderId());

       } catch (SQLException e) {
           System.out.println("❌ خطا در ذخیره سفارش در دیتابیس: " + e.getMessage());
       }
   }
   
}
{
    
}
