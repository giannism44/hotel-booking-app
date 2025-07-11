# HotelApp 🏨

A Spring Boot web application for managing a hotel. It supports user registration (Clients and Employees), room listings, reservations, and role-based access control.

---

## ✨ Features

- User Registration with Spring Security (Client & Employee roles)
- Login/Logout functionality
- Make and manage room reservations
- Room listing with availability checks
- Role-based access control:
  - Clients can book rooms
  - Employees can manage the system
- Full CRUD for Clients, Employees, Rooms, and Reservations
- Client profile with personal reservations
- Dynamic navigation menu based on user role

---

## ⚖️ Technologies

- Java 17  
- Spring Boot  
- Spring Security  
- Spring Data JPA  
- MySQL  
- Thymeleaf  
- Lombok  
- Gradle

---

## 🚀 How to Run (Build & Deploy)

### 1. Clone the Repository

```bash
git clone https://github.com/giannism44/hotel-booking-app.git
cd hotel-booking-app
```

### 2. Configure the Database

In `src/main/resources/application.properties`, set your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotelapp?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Europe/Athens
spring.datasource.username=hoteluser
spring.datasource.password=12345
```

> Ensure that MySQL is running and that a database named `hotelapp` exists (Spring Boot can auto-create the tables).

---

### 3. Build the Project

```bash
./gradlew clean build
```

Or from IntelliJ: `Build > Build Project`

---

### 4. Run the Application

```bash
./gradlew bootRun
```

Then open your browser and navigate to:

```
http://localhost:8080/
```

---

## 🗖 Default Access

After registration:

- **Clients** are redirected to the home page `/`
- **Employees** are redirected to `/management`

---

## 🛡️ Security

- Passwords are stored encrypted using BCrypt
- Access is restricted based on roles:

```
/hotel/clients/**                → Client & Employee  
/hotel/employees/**              → Employee only  
/hotel/reservations/**           → Client & Employee  
/hotel/reservations/management/** → Employee only
```

---

## 📈 Bonus Features

- AJAX room availability check on reservation form
- Clients receive a 5% discount after 3 bookings

---

## ✉️ Author

**Giannis M.**  
GitHub: [giannism44](https://github.com/giannism44)

---

