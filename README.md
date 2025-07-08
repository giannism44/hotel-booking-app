HotelApp 🏨

A Spring Boot web application for managing a hotel. It supports user registration (Clients and Employees), room listings, reservations, and role-based access control.

✨ Features
    - User Registration with Spring Security (Client & Employee roles)
    - Login/Logout functionality
    - Make and manage room reservations
    - Room listing with availability checks
    - Role-based access (Clients can book, Employees can manage)
    - Full CRUD for Clients, Employees, Rooms, Reservations
    - Profile page for logged-in Clients
    - Dynamic navigation based on role

⚖️ Technologies
    - Java 17
    - Spring Boot 
    - Spring Security
    - Spring Data JPA
    - MySQL Database
    - Thymeleaf
    - Lombok
    - Gradle

 📁 Project Structure
       src
     ├── main
     │   ├── java/gr/aueb/cf/hotelapp
     │   │    ├── authentication
     │   │    ├── controller
     │   │    ├── core
     │   │    ├── dto
     │   │    ├── init
     │   │    ├── mapper
     │   │    ├── model
     │   │    ├── repository
     │   │    └── service
     │   └── resources
     │        ├── static
     │        ├── templates
     │        └── application.properties
     └── test

🚀 How to Run (Build & Deploy)
   1. Clone the Repository
        git clone https://github.com/giannism44/hotel-booking-app.git
        cd hotel-booking-app
        
   2. Configure Database
         In src/main/resources/application.properties, set your MySQL credentials:
           spring.datasource.url=jdbc:mysql://localhost:3306/hotelapp
           spring.datasource.username=your_user
           spring.datasource.password=your_password
         Make sure MySQL is running and a database named hotelapp exists (Spring Boot can auto-create tables).
        
   3. Build the Project
      ./gradlew clean build
       Or if you're using IntelliJ: Build > Build Project
        
  4. Run the Application
    ./gradlew bootrun
     Then open your browser:
      http://localhost:8000/
         
🗖️ Default Access
    After registration:
    Clients are redirected to homepage (/)
    Employees are redirected to /management

🛡️ Security
  - Passwords are encrypted using BCrypt
  - Access is restricted based on roles:
        -  /hotel/clients/** - Client & Employee
        -  /hotel/employees/** - Employee only
        -  /hotel/reservations/** - Πελάτης & Υπάλληλος
        -  /hotel/reservations/management/** - Employee only
    
📈 Επιπλέον Δυνατότητες   
    - AJAX έλεγχος διαθεσιμότητας δωματίων σε κρατήσεις

✉️ Author
    Giannis M.
    GitHub: giannism44   
