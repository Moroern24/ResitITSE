# MovieRater — Java & SQLite Command-Line Application

## Overview

**MovieRater** is a command-line application developed in Java using JDBC for interaction with a SQLite database. The application is designed for managing user data and movie viewing habits based on a provided entity-relationship model. All required functionality is implemented using SQL queries executed via JDBC, in accordance with the assignment specification.

This application is submitted as part of the resit assignment for the course *Introduction to Software Engineering* (PI 8).

---

## Technologies Used

* Java 17 or higher
* SQLite (file-based local database)
* JDBC (Java Database Connectivity)
* IntelliJ IDEA (recommended IDE)

---

## Project Structure

```
movie_rater/
│
├── Main.java                 # Application entry point
├── UserInterface.java        # Command-line interaction logic
├── DatabaseManager.java      # SQLite connection and initialization
│
├── models/                   # Data model classes
│   ├── User.java
│   ├── Movie.java
│   └── ViewingHabit.java
│
├── sql/
│   └── schema.sql            # SQL script to initialize the database schema
│
├── movierater.db             # SQLite database file (generated or provided)
└── README.md                 # Project documentation
```

---

## How to Run the Application

1. Clone or download the project to your local machine.

   ```
   git clone https://github.com/your-username/movierater.git
   ```

2. Open the project in IntelliJ IDEA or any other Java-compatible IDE.

3. Ensure that the SQLite JDBC driver is included in the classpath (for example, by adding `sqlite-jdbc-<version>.jar` to your project libraries).

4. Run the `Main.java` file. On first run, the database schema will be created automatically using the `sql/schema.sql` script.

---

## Implemented Functionalities

| # | Functionality Description                                          |
| - | ------------------------------------------------------------------ |
| 1 | Add a new user to the database                                     |
| 2 | Display all viewing habits of a specific user                      |
| 3 | Change the title of a movie                                        |
| 4 | Delete a record from the `ViewingHabit` table                      |
| 5 | Calculate and display the mean age of users (using SQL)            |
| 6 | Count the number of users who have watched a specific movie        |
| 7 | Display the total number of minutes watched by all users           |
| 8 | Count users who have watched more than one movie                   |
| 9 | Add an `Email` column to the `User` table (if not already present) |

All computations are performed using SQL and not handled manually in Java code.

---

## Database Initialization

* The database schema is defined in `sql/schema.sql`.
* On the first run, the database file `movierater.db` is created and initialized if it does not already exist.
* The application also includes functionality to programmatically add the `Email` column to the `User` table if it is missing.

---

## Authors

* \[Your Full Name], Student ID: \[Your ID]
* \[Teammate Name], Student ID: \[Teammate's ID] (if applicable)

---

## Submission Contents

The submission consists of the following:

* Source code files (`.java`)
* SQLite database file (`movierater.db`)
* SQL schema file (`sql/schema.sql`)
* This `README.md` file
* (Optional) Sample dataset script (`sample_data.sql`)


