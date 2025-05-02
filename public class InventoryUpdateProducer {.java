public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;
import com.google.gson.Gson;
import com.ubuntu.onlineordersystemkafka.InventoryUpdate;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;
/**
*
* @author toor
*/
public class InventoryUpdateProducer {
   
   private static final String TOPIC = "inventory-updates";
   
   public static void sendInventoryUpdate(){
       Scanner scanner = new Scanner(System.in);
       
       System.out.println("🛒 لطفاً اطلاعات بروزرسانی موجودی را وارد کنید:");
       
       System.out.println("📦 شناسه محصول (product id): ");
       String productId = scanner.nextLine();
       
       System.out.println("🔢 تعداد جدید موجودی: ");
       int newQuantity = Integer.parseInt(scanner.nextLine());
       
       InventoryUpdate update = new InventoryUpdate(UUID.randomUUID().toString(), productId, newQuantity);
       
       Gson gson = new Gson();
       
       String updateJson = gson.toJson(update);
       
       // kafka setting
       Properties props = new Properties();
       props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       
       KafkaProducer<String, String> producer = new KafkaProducer<>(props);
       
       ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, update.getUpdateId(), updateJson);
       
       
       producer.send(record, (RecordMetadata metadata, Exception exception) -> {
           if (exception == null) {
               System.out.println("✅ موجودی با موفقیت به Kafka ارسال شد: " + update.getUpdateId());
           }else {
               System.out.println("❌ خطا در ارسال موجودی: " + exception.getMessage());
           }
       });
       
       producer.flush();
       producer.close();
       
   }
   
}
{
    
}
