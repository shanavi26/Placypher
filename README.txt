PlacementAppComplete - Instructions

1. Setup MySQL:
   - Install MySQL server.
   - Run sql/create_db.sql to create the database and tables.

2. Add MySQL JDBC driver:
   - Download mysql-connector-java-X.X.X.jar from:
     https://dev.mysql.com/downloads/connector/j/
   - Place it in lib/ folder.
   - Add it to your project classpath.

3. Update database credentials in PlacementApp.java and LoginPage.java:
   String user = "root";
   String pass = "yourpassword";

4. Compile & Run:
   - javac -cp ".;lib/mysql-connector-java-8.1.0.jar" src/LoginPage.java src/PlacementApp.java
   - java -cp ".;lib/mysql-connector-java-8.1.0.jar;src" LoginPage

5. LoginPage collects user info and opens PlacementApp GUI.
   All data is saved into MySQL database 'placement_db'.
