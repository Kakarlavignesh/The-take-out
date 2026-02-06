# The Takeout - Setup Instructions

## Prerequisites
- **Java 17+** (You have Java 25, which is perfect).
- **Maven**: You need to have Maven installed to build the project, or use an IDE like IntelliJ IDEA that handles it.

## How to Run

1. Open a terminal in the `server` folder:
   ```powershell
   cd server
   ```

2. Run the Spring Boot application:
   ```powershell
   mvn spring-boot:run
   ```
   *Note: If `mvn` is not recognized, you must install Apache Maven or open this project in IntelliJ IDEA/Eclipse.*

3. Once the server says `Started TheTakeoutApplication`, open your browser to:
   [http://localhost:9090](http://localhost:9090)

## Features
- All core features (Menu, Feedback, Admin) are available via the browser.
- The frontend is served directly by the Java server.
- Database is stored in `./data/takeoutdb` (automatically created).
