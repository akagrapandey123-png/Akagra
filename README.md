

# Library Management System (Java + Swing + MySQL)

A simple and user-friendly **Library Management System** built using **Java Swing (GUI)** and **MySQL JDBC**.
This system allows managing books, visitors, issuing/returning books, and viewing issued books inside a graphical interface.

---

##  Features

### Book Management

* Add new books
* View all stored books
* Delete an existing book
* View only *issued* books

### Visitor Management

* Add new visitor
* View all registered visitors

### Book Transactions

* Issue a book to a visitor
* Return an issued book

###  Graphical Interface

Built using **Java Swing**, including:

* Buttons for each operation
* Popup windows for adding/viewing data
* Table display using `JTable`
* Error handling using dialogs

---

##  Project Structure

```
LibraryGUI.java            → Main dashboard window
AddBookGUI.java            → Add new books
AddVisitorGUI.java         → Add new visitors
ShowBooksGUI.java          → Display all books
ShowVisitorsGUI.java       → Display all visitors
ShowIssuedBooksGUI.java    → Display books marked as issued
IssueBookGUI.java          → Issue a book
ReturnBookGUI.java         → Return issued books
DeleteBookGUI.java         → Delete book from database
DB.java                    → Database connection file
```

---

##  Database Setup

Create a MySQL database:

```sql
CREATE DATABASE library_db;
USE library_db;
```

###  Books Table

```sql
CREATE TABLE books (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    isIssued BOOLEAN DEFAULT FALSE
);
```

###  Visitors Table

```sql
CREATE TABLE visitors (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(50)
);
```

---

## Database Connection Settings

Your `DB.java` file:

```java
static final String URL = "jdbc:mysql://localhost:3306/library_db";
static final String USER = "root";
static final String PASS = "Akagra@88511";
```

Change credentials if needed.

---

##  How to Run

1. Install **JDK 8+**
2. Add **MySQL JDBC Driver** to project
   (`mysql-connector-j.jar`)
3. Import all Java files into your IDE
4. Run:

```
LibraryGUI.java
```

---

##  GUI Overview

###  Main Menu

* Add Book
* Add Visitor
* Show Books
* Show Visitors
* Show Issued Books
* Issue Book
* Return Book
* Delete Book

Each button opens a new window (JFrame) for the corresponding operation.

---

##  Example Code Snippets

###  Add Book

Using `PreparedStatement`:

```java
String q = "INSERT INTO books VALUES (?, ?, ?, false)";
ps.setInt(1, id);
ps.setString(2, title);
ps.setString(3, author);
```

### Show Issued Books

Displays only books where `isIssued = true`.

```java
SELECT * FROM books WHERE isIssued = true;
```

---

##  Requirements

* Java (JDK 8 or later)
* MySQL Server
* JDBC Driver (mysql-connector-j)
* IntelliJ / Eclipse / VS Code (optional)

---

##  License

This project is free to use for learning and educational purposes.


