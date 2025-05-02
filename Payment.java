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
public class Payment {
   private int orderId;
   private String paymentStatus;
   private Timestamp paymentDate;
   
   
   
   public Payment(int orderId, String paymentStatus, Timestamp paymentDate){
       this.orderId = orderId;
       this.paymentStatus = paymentStatus;
       this.paymentDate = paymentDate;
   }
   
   public int getOrderId(){
       return orderId;
   }
   
   
   public String getPaymentStatus(){
       return paymentStatus;
   }
   

   
   public Timestamp getPaymentDate(){
       return paymentDate;
   }
   
   @Override
   public String toString() {
       return "Payment{" +
               "orderId=" + orderId +
               ", paymentStatus='" + paymentStatus + '\'' +
               ", paymentDate=" + paymentDate +
               '}';
   }

}
{
    
}
