
# 🏧 ATM Transaction API

A **Spring Boot REST API** that simulates core ATM banking operations such as withdrawals, deposits, balance checks, PIN changes, and printed account reports.
This project is designed for learning, demonstration, and backend API development practice.

---

## 🚀 Features

* 💸 **Withdraw Money**
* 💰 **Deposit Money**
* 📊 **Check Account Balance**
* 🔐 **Change ATM PIN**
* 🧾 **Generate Printed Account Report (PDF)**

---

## 🛠️ Technologies Used

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **Hibernate**
* **MySQL**
* **Maven**
* **iText PDF**
* **RESTful APIs**
* **Docker**

---

## 📂 Project Structure

```
ATM_Transaction_API
├── controller
├── service
├── repository
├── entity
├── dto
├── util
├── src/main/resources
├── pom.xml
└── Dockerfile
```

---

## ⚙️ API Functionalities

### 🔹 Withdraw Money

Allows users to withdraw a specific amount from their account if sufficient balance is available.

### 🔹 Deposit Money

Allows users to deposit money into their account.

### 🔹 Check Balance

Returns the current available balance of the account.

### 🔹 Change PIN

Allows users to securely change their ATM PIN after verification.

### 🔹 Printed Account Report

Generates a **PDF account report** containing transaction details.

---

## 📡 API Endpoint Examples

### 🔐 Base URL

```
http://localhost:8080/api/v1/
```

---

### 💸 Withdraw Money

**Endpoint**

```
POST /withdraw/withdrawAmount
```

**Request Body**

```json
{
    "cardNumber":"5144 5966 0261 4649",
    "pin":"4445",
    "expireDate":"2030-10-12",
    "cvvNumber":"661",
    "amount":5000,
    "accountType":"Saving"

}
```

### 💰 Deposit Money

**Endpoint**

```
POST /deposit/depositDetails
POST /deposit/depositMoney
```

**Request Body**

```json
{
  "accountNumber":"1074-7285-4832",
  "amount":12,000,
}
```

### 📊 Check Account Balance

**Endpoint**

```
POST /balance/checkBalance
```

**Request Body**

```json
{
    "cardNumber":"5144 5966 0261 4649",
    "pin":"4445",
    "expireDate":"2030-10-12",
    "cvvNumber":"661",
    "accountType":"Saving"
}

```

### 🔐 Change ATM PIN

**Endpoint**

```
PUT /pin/changePin
```

**Request Body**

```json
{
    "cardNumber":"5144 5966 0261 4649",
    "pin":"4445",
    "newPin":"2395",
    "expireDate":"2030-10-12",
    "cvvNumber":"661",
    "accountType":"Saving"
}

```

### 🧾 Generate Printed Account Report (PDF)

**Endpoint**

```
POST /statement/generateStatement
```

**Request Body**

```json
{
    "cardNumber":"5144 5966 0261 4649",
    "pin":"4445",
    "expireDate":"2030-10-12",
    "cvvNumber":"661",
    "accountType":"Saving"
}

```
**Response**


Content-Type: application/pdf
```

📥 Downloads a **PDF account transaction report**

---

## ❌ Error Response Example

```json
{
  "status": "ERROR",
  "message": "Insufficient balance"
}
```

---

## 🗄️ Database Configuration

Update `application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/atm_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ How to Run the Project

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/ATM_Transaction_API.git
```

### 2️⃣ Build the Project

```bash
mvn clean package
```

### 3️⃣ Run the Application

```bash
java -jar target/ATM_Transaction_API-0.0.1.jar
```

Application runs on:

```
http://localhost:8080
```

---

## 🐳 Docker Support (Optional)

### Build Docker Image

```bash
docker build -t atm-transaction-api .
```

### Run Container

```bash
docker run -d -p 8080:8080 atm-transaction-api
```

---

## 🧪 Testing

You can test APIs using:

* Postman
* Curl

---

## 📌 Future Enhancements

* User authentication & authorization
* Transaction history filtering
* Role-based access
* Swagger API documentation
* Cloud deployment

---

## 👨‍💻 Author

**Sahan Gunasekara**
Java Backend Developer

---

## 📄 License

This project is for **educational and learning purposes**.

---
