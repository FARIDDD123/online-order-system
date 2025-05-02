public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;
import com.google.gson.Gson;
import com.ubuntu.onlineordersystemkafka.DatabaseConnection;
import com.ubuntu.onlineordersystemkafka.Shipping;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
/**
*
* @author toor
*/
public class ShippingProducer {
   private static final String TOPIC = "shipping";

   public static void sendShipping() {
       Scanner scanner = new Scanner(System.in);

       System.out.print("🧾 آیدی سفارش (order_id): ");
       int orderId = scanner.nextInt();
       scanner.nextLine();

       System.out.print("📦 وضعیت ارسال (PENDING, SHIPPED, DELIVERED, FAILED): ");
       String status = scanner.nextLine().toUpperCase();

       Timestamp shippedDate = new Timestamp(System.currentTimeMillis());

       System.out.print("📬 تاریخ تحویل (yyyy-MM-dd): ");
       String deliveryStr = scanner.nextLine();
       Timestamp deliveryDate = Timestamp.valueOf(deliveryStr + " 00:00:00");

       Shipping shipping = new Shipping(orderId, status, shippedDate, deliveryDate);
       Gson gson = new Gson();
       String json = gson.toJson(shipping);

       Properties props = new Properties();
       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

       KafkaProducer<String, String> producer = new KafkaProducer<>(props);
       producer.send(new ProducerRecord<>(TOPIC, json), (metadata, exception) -> {
           if (exception == null) {
               System.out.println("✅ اطلاعات ارسال ذخیره شد.");
           } else {
               System.out.println("❌ خطا در Kafka: " + exception.getMessage());
           }
       });

       producer.flush();
       producer.close();

       saveToDatabase(shipping);
   }

   private static void saveToDatabase(Shipping shipping) {
       String sql = "INSERT INTO shipping (order_id, shipping_status, shipped_date, delivery_date) VALUES (?, ?, ?, ?)";
       try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, shipping.getOrderId());
           stmt.setString(2, shipping.getShippingStatus());
           stmt.setTimestamp(3, shipping.getShippedDate());
           stmt.setTimestamp(4, shipping.getDeliveryDate());

           stmt.executeUpdate();
           System.out.println("✅ ذخیره در دیتابیس انجام شد.");
       } catch (SQLException e) {
           System.out.println("❌ خطا در دیتابیس: " + e.getMessage());
       }
   }
}
{
    
}
