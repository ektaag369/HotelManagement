**Description**:
The Hotel Management System is a Java-based application designed to manage hotel reservations. It allows users to reserve rooms, view current reservations, 
get room details based on reservation information, update existing reservations, and delete reservations. This application interacts with a MySQL database to 
store and retrieve reservation data.

**Features**
Reserve a Room
View Reservations
Get Room Number by Reservation ID and Guest Name
Update Reservations
Delete Reservations

**Prerequisites**
Java Development Kit (JDK) 8 or higher
MySQL Database
MySQL Connector/J (JDBC Driver)

**Setup Instructions**

**1.Clone the Repository**
->git clone https://github.com/yourusername/hotel-management-system.git
->cd hotel-management-system

**2.Set Up the Database**
Start your MySQL server.
Create a database named hotel_db:
->CREATE DATABASE hotel_db;

**3.Create the reservations table:**
->USE hotel_db;

->CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    guests_name VARCHAR(100) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
**4.Configure Database Connection**
Update the database connection details in the HotelManagementSystem.java file:

private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
private static final String username = "root";
private static final String password = "yourpassword";

 **5. Compile and Run the Application**

