public /*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.ubuntu.onlineordersystemkafka;

/**
*
* @author toor
*/
public class InventoryUpdate {
   private String updateId;
   private String productId;
   private int newQuantity;
   
   public InventoryUpdate(String updateId, String productId, int newQuantity){
       this.updateId = updateId;
       this.productId = productId;
       this.newQuantity = newQuantity;
   }
   
   public String getUpdateId() {
       return updateId;
   }

   public void setUpdateId(String updateId) {
       this.updateId = updateId;
   }

   public String getProductId() {
       return productId;
   }

   public void setProductId(String productId) {
       this.productId = productId;
   }

   public int getNewQuantity() {
       return newQuantity;
   }

   public void setNewQuantity(int newQuantity) {
       this.newQuantity = newQuantity;
   }

   @Override
   public String toString() {
       return "InventoryUpdate{" +
               "updateId='" + updateId + '\'' +
               ", productId='" + productId + '\'' +
               ", newQuantity=" + newQuantity +
               '}';
   }
   
}
{
    
}
