public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;
import java.sql.Timestamp;
/**
*
* @author toor
*/
public class Shipping {
   private int orderId;
   private String shippingStatus;
   private Timestamp shippedDate;
   private Timestamp deliveryDate;
   
   
   public Shipping(int orderId, String shippingStatus, Timestamp shippedDate, Timestamp deliveryDate){
       this.orderId = orderId;
       this.shippingStatus = shippingStatus;
       this.shippedDate = shippedDate;
       this.deliveryDate = deliveryDate;
   }
   
   public int getOrderId(){
       return orderId;
   }
   
   public String getShippingStatus(){
       return shippingStatus;
   }
   
   public Timestamp getShippedDate(){
       return shippedDate;
   }
   
   public Timestamp getDeliveryDate(){
       return deliveryDate;
   }
   
   @Override
   public String toString() {
       return "Shipping{" +
               "orderId=" + orderId +
               ", shippingStatus='" + shippingStatus + '\'' +
               ", shippedDate=" + shippedDate +
               ", deliveryDate=" + deliveryDate +
               '}';
   }
}
{
    
}
