

### ✅ **پروژه: سیستم مدیریت سفارشات آنلاین با Kafka و MySQL**

---

## 📦 توضیح پروژه

این پروژه یک سیستم مدیریت سفارشات آنلاین است که با استفاده از **Apache Kafka** برای پردازش رویدادهای بلادرنگ و **MySQL** برای ذخیره‌سازی داده طراحی شده است. هدف این پروژه پیاده‌سازی معماری Event-Driven به سبک میکروسرویس با Kafka برای تولیدکنندگان (Producers) و مصرف‌کنندگان (Consumers) است.

---

## ✨ امکانات کلیدی

* ثبت‌نام کاربران و ذخیره‌سازی در Kafka و پایگاه‌داده
* ثبت سفارش به همراه اقلام سفارش
* بروزرسانی موجودی محصولات
* پردازش پرداخت سفارش
* وضعیت حمل‌ونقل سفارش
* طراحی پایگاه‌داده حرفه‌ای با کلیدهای خارجی
* قابلیت توسعه آسان به میکروسرویس‌های جداگانه در آینده

---

## 🗃 ساختار جداول MySQL

### `users`

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### `orders`

```sql
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(255),
    user_id INT,
    total_amount DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### `order_items`

```sql
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
```

### `products`

```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DOUBLE,
    stock_quantity INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### `inventory`

```sql
CREATE TABLE inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    quantity_available INT,
    last_updated TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### `payments`

```sql
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    payment_status ENUM('PENDING', 'PAID', 'FAILED') DEFAULT 'PENDING',
    payment_date TIMESTAMP NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
```

### `shipping`

```sql
CREATE TABLE shipping (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    shipping_status ENUM('PENDING', 'SHIPPED', 'DELIVERED', 'FAILED') DEFAULT 'PENDING',
    shipped_date TIMESTAMP NULL,
    delivery_date TIMESTAMP NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);
```

---

## 🧪 اجزای Kafka

### 🎯 Topics:

* `user-registrations`
* `orders`
* `inventory-updates`
* `payments`
* `shipping`

### 🛠 Producers:

* `UserRegistrationProducer.java`
* `OrderProducer.java`
* `InventoryUpdateProducer.java`
* `PaymentProducer.java`
* `ShippingProducer.java`

### 📥 Consumers:

*(در این فاز هنوز اضافه نشده‌اند، اما قابلیت پیاده‌سازی ساده دارند.)*

---

## 🚀 اجرای پروژه

### پیش‌نیازها:

* Java 17+ یا بالاتر
* Apache Kafka و Zookeeper
* MySQL
* Maven
* NetBeans یا IntelliJ (اختیاری)

### مراحل راه‌اندازی:

1. اجرای Kafka و Zookeeper:

   ```bash
   bin/zookeeper-server-start.sh config/zookeeper.properties
   bin/kafka-server-start.sh config/server.properties
   ```

2. ساختن Topic‌ها:

   ```bash
   kafka-topics.sh --create --topic orders --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
   ```

3. اجرای فایل `OnlineOrderSystemKafka.java`:

   ```bash
   mvn clean install
   java -jar target/OnlineOrderSystemKafka.jar
   ```

---

## ✍ نویسنده

**Toor**


