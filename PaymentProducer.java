public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;
import com.google.gson.Gson;
import com.ubuntu.onlineordersystemkafka.DatabaseConnection;
import com.ubuntu.onlineordersystemkafka.Payment;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
/**
*
* @author toor
*/
public class PaymentProducer {
   private static final String TOPIC = "payments";
   
   public static void sendPayment(){
       Scanner scanner = new Scanner(System.in);
       
       System.out.println("🧾 آیدی سفارش (order_id): ");
       int orderId = scanner.nextInt();
       
       scanner.nextLine();
       
       System.out.println("💳 وضعیت پرداخت (PENDING, PAID, FAILED): ");
       String status = scanner.nextLine().toUpperCase();
       
       Timestamp paymentDate = new Timestamp(System.currentTimeMillis());
       
       Payment payment = new Payment(orderId, status, paymentDate);
       
       Gson gson = new Gson();
       
       String json = gson.toJson(payment);
       
       Properties props = new Properties();
       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       
       KafkaProducer<String, String> producer = new KafkaProducer<>(props);
       
       producer.send(new ProducerRecord<>(TOPIC, json), (metadata, exception) -> {
           if (exception == null){
               System.out.println("✅ اطلاعات پرداخت ارسال شد.");
           }else{
               System.out.println("❌ خطا در ارسال پرداخت: " + exception.getMessage());
           }
       });
       
       producer.flush();
       producer.close();
       
       saveToDatabase(payment);
       
   }
   
   public static void saveToDatabase(Payment payment){
       String sql = "INSERT INTO payments (order_id, payment_status, payment_date) VALUES (?, ?, ?)";
       try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, payment.getOrderId());
           stmt.setString(2, payment.getPaymentStatus());
           stmt.setTimestamp(3, payment.getPaymentDate());

           stmt.executeUpdate();
           System.out.println("✅ اطلاعات پرداخت در دیتابیس ذخیره شد.");
       } catch (SQLException e) {
           System.out.println("❌ خطا در ذخیره پرداخت: " + e.getMessage());
       }
   }
   
}
{
    
}
