# Library Management System

A console-based Library Management System built with Core Java, JDBC, and MySQL.

## Tech Stack
- Java
- JDBC
- MySQL

## Project Structuresrc/
├── app/          → Main.java (entry point, menu)
├── dao/          → BookDAO, MemberDAO, BorrowDAO (JDBC logic)
├── model/        → Book, Member, BorrowRecord (entity classes)
├── service/      → BookService, MemberService, BorrowService (business logic)
└── util/         → DBConnection (database connection
## Features
- Add, view, and delete books
- Register, view, and deactivate members
- Issue books to members
- Return books with automatic fine calculation (Rs 2/day after due date)
- Tracks available copies in real time
- Prevents duplicate issue of same book to same member
- Borrow history preserved permanently

## How to Run

### Prerequisites
- Java JDK 8 or above
- MySQL
- MySQL JDBC Connector JAR

### Setup
1. Create the database:
```sql
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20),
    genre VARCHAR(50),
    total_copies INT NOT NULL,
    available_copies INT NOT NULL
);

CREATE TABLE member (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    join_date DATE,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE borrow_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status VARCHAR(20) DEFAULT 'BORROWED',
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
);
```

2. Update `src/util/DBConnection.java` with your MySQL credentials:
```java
private static final String URL = "jdbc:mysql://localhost:3306/library_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

3. Place MySQL JDBC JAR inside `lib/` folder.

### Compile
```bash
javac -cp lib/mysql-connector-j-8.0.33.jar -d out src/util/*.java src/model/*.java src/dao/*.java src/service/*.java src/app/*.java
```

### Run
```bash
java -cp out:lib/mysql-connector-j-8.0.33.jar app.Main
```
On Windows replace `:` with `;`

## Concepts Covered
- DAO pattern
- JDBC with PreparedStatement and ResultSet
- Multi-table relationships with foreign keys
- Business logic separation
- Date handling with LocalDate
- Fine calculation using ChronoUnit
