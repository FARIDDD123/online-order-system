public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;

/**
*
* @author toor
*/
public class Order {
   private String orderId;
   private String userId;
   private double totalAmount;
   
   
   
   public Order(String orderId, String userId, double totalAmount){
       this.orderId = orderId;
       this.userId = userId;
       this.totalAmount = totalAmount;
     
   }
   
   // گتر و ستر ها (Getter و Setter)
   public String getOrderId() {
       return orderId;
   }

   public void setOrderId(String orderId) {
       this.orderId = orderId;
   }

   public String getUserId() {
       return userId;
   }

   public void setUserId(String userId) {
       this.userId = userId;
   }

   public double getTotalAmount() {
       return totalAmount;
   }

   public void setTotalAmount(double totalAmount) {
       this.totalAmount = totalAmount;
   }

  
   
   
   @Override
   public String toString(){
       return "Order{" +
               "orderId='" + orderId + '\'' +
               ", userId='" + userId + '\'' +
               ", Total Amount='" + totalAmount + '\'' +
               '}';
   }
}


{
    
}
