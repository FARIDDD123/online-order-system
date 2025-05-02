public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;
import com.google.gson.Gson;
import com.ubuntu.onlineordersystemkafka.DatabaseConnection;
import com.ubuntu.onlineordersystemkafka.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Properties;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
*
* @author toor
*/
public class UserRegistrationProducer {
   private static final String TOPIC = "user-registrations";
   
   public static void sendUserRegistration(){
       Scanner scanner = new Scanner(System.in);
       System.out.println("👤 لطفاً اطلاعات ثبت‌نام کاربر را وارد کنید:");

       
       System.out.println("👤 نام کاربری (username):");
       String userName = scanner.nextLine();
       
       System.out.println("📧 ایمیل:");
       String email = scanner.nextLine();
       
       System.out.println("🔑 رمز عبور:");
       String password = scanner.nextLine();
       
       String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
       
       User user = new User(userName, email, hashedPassword);
       
       Gson gson = new Gson();
       String userJson = gson.toJson(user);
       
       
       // kafka settings
       Properties props = new Properties();
       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       
       
       KafkaProducer<String, String> producer = new KafkaProducer<>(props);
       
       ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, userJson);
       
       producer.send(record, (RecordMetadata metadata, Exception exception) -> {
           if (exception == null) {
               System.out.println("✅ اطلاعات کاربر به Kafka ارسال شد: " + user.getUserName());
           } else {
               System.out.println("❌ خطا در ارسال اطلاعات کاربر به Kafka: " + exception.getMessage());
           }
       });

       producer.flush();
       producer.close();

       // ذخیره در دیتابیس
       saveUserToDatabase(user);
       
   }
   
   private static void saveUserToDatabase(User user) {
       String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

       try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

           stmt.setString(1, user.getUserName());
           stmt.setString(2, user.getEmail());
           stmt.setString(3, user.getPassword());

           stmt.executeUpdate();
           System.out.println("✅ اطلاعات کاربر در دیتابیس ذخیره شد: " + user.getUserName());

       } catch (SQLException e) {
           System.out.println("❌ خطا در ذخیره اطلاعات کاربر در دیتابیس: " + e.getMessage());
       }
   }
   
   
}
{
    
}
