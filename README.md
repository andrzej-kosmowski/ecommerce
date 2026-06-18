# E-Commerce Application

## 📖 Overview

E-Commerce Application is a console-based online store system developed in Java.

The application allows customers to browse products, add products to a shopping cart, place orders, generate invoices, apply discounts and process multiple orders concurrently.

The project was created using Object-Oriented Programming principles and common software design patterns.

---

# 🚀 Features

| Module | Functionality |
|----------|-------------|
| Product Management | Add, update, remove and browse products |
| Shopping Cart | Add and remove products from cart |
| Orders | Create and process orders |
| Invoices | Generate invoices after order completion |
| Discounts | Percentage and fixed amount discounts |
| Persistence | Save orders to file |
| Multithreading | Process multiple orders concurrently |
| Exception Handling | Custom business exceptions |
| CLI | Console interface for user interaction |

---

# 🏗️ Project Structure

```text
com.ecommerce
│
├── cli
│   ├── EcommerceApplication
│   ├── MenuOption
│   └── InputReader
│
├── domain
│   ├── product
│   ├── cart
│   ├── order
│   └── discount
│
├── repository
│   └── order
│
├── service
│   ├── billing
│   ├── cart
│   ├── order
│   └── product
│
├── exception
│
└── Main
```

---

# 🛒 Product Types

The store supports multiple product types.

## Computer

```java
Product laptop = Computer.builder()
        .id("P1")
        .name("MacBook PRO")
        .price(BigDecimal.valueOf(8999))
        .availableQuantity(10)
        .processor("Apple M5")
        .ram(32)
        .disk(1024)
        .build();
```

### Additional configuration

- Processor
- RAM
- Disk size
- Graphics card

---

## Smartphone

```java
Product smartphone = Smartphone.builder()
        .id("P2")
        .name("iPhone 17 Pro")
        .price(BigDecimal.valueOf(5800))
        .availableQuantity(10)
        .color("Black")
        .batteryCapacity(3998)
        .accessories(List.of(
                Accessory.CASE,
                Accessory.CHARGER
        ))
        .build();
```

### Additional configuration

- Color
- Battery capacity
- Accessories

---

## Electronics

```java
Product mouse = Electronics.builder()
        .id("P3")
        .name("SteelSeries Aerox")
        .price(BigDecimal.valueOf(199))
        .availableQuantity(5)
        .build();
```

---

# 🛍️ Shopping Cart

Adding products to the cart:

```java
cartService.addToCart("P1", 2);
```

Viewing cart contents:

```java
cartService.viewCart();
```

Checkout:

```java
Order order = cartService.checkout(client);
```

---

# 📦 Order Processing

Order lifecycle:

```text
NEW
 ↓
PROCESSING
 ↓
COMPLETED
```

Order processing:

```java
Invoice invoice = orderProcessor.process(order);
```

During processing:

1. Order status changes to `PROCESSING`
2. Invoice is generated
3. Order is saved
4. Order status changes to `COMPLETED`

---

# 💰 Discounts

The application supports the Strategy Pattern for discounts.

## Percentage Discount

```java
DiscountPolicy discount =
        new PercentageDiscount(
                BigDecimal.valueOf(10)
        );
```

Result:

```text
100 PLN → 90 PLN
```

---

## Fixed Amount Discount

```java
DiscountPolicy discount =
        new FixedAmountDiscount(
                BigDecimal.valueOf(20)
        );
```

Result:

```text
100 PLN → 80 PLN
```

---

## No Discount

```java
DiscountPolicy discount =
        new NoDiscount();
```

---

# 🧾 Invoice Generation

Invoices are generated automatically during order processing.

Example invoice number:

```text
INV-20260602-1
```

Invoice contains:

| Field | Description |
|---------|------------|
| Invoice Number | Unique invoice identifier |
| Issue Date | Invoice generation date |
| Customer | Customer information |
| Items | Ordered products |
| Total Price | Final order value |

---

# 💾 Order Persistence

Processed orders are automatically saved to a file.

Example:

```text
ORDER_ID=7f8e9a
CLIENT=john@test.com
TOTAL=9198
STATUS=COMPLETED
ITEMS=MacBook PRO x1
-------------------------
```

---

# ⚡ Multithreading

Multiple orders can be processed simultaneously using ExecutorService.

```java
ExecutorService executorService =
        Executors.newFixedThreadPool(3);

MultiThreadOrderProcessor processor =
        new MultiThreadOrderProcessor(
                orderProcessor,
                executorService
        );
```

---

# ❌ Exception Handling

Custom exceptions used in the project:

| Exception | Description |
|------------|------------|
| ProductNotFoundException | Product does not exist |
| NotEnoughStockException | Insufficient stock |
| EmptyCartException | Checkout with empty cart |
| OrderProcessingException | Order processing failed |
| InvalidOrderStateException | Invalid order status transition |
| InvalidDiscountException | Invalid discount configuration |

---

# 🧪 Testing

The project contains unit tests for:

- Product domain
- Shopping cart
- Orders
- Discounts
- Billing
- Repositories
- Multithreading

Run tests:

```bash
mvn test
```

---

# 🛠️ Technologies

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming language |
| Maven | Build tool |
| Lombok | Boilerplate reduction |
| JUnit 5 | Testing |
| Mockito | Mocking |
| AssertJ | Assertions |

---

# 🎯 Design Patterns

The project uses:

| Pattern | Usage |
|----------|--------|
| Strategy | Discount policies |
| Repository | Order persistence |
| Dependency Injection | Service dependencies |
| Builder | Product creation |
| Single Responsibility Principle | Separation of business logic |

---

# ▶️ Running Application

Clone repository:

```bash
git clone <repository-url>
```

Run tests:

```bash
mvn test
```

Start application:

```bash
mvn compile exec:java
```

or run:

```bash
Main.java
```

from your IDE.

---

# 👨‍💻 Author

Educational project created to practice Java, OOP, testing, design patterns and software architecture.