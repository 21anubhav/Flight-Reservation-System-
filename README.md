# Flight Reservation System

> **Deloitte Case Study Implementation** — Version Dated: 12.2025  
> Built strictly following the project specifications, wireframes, and architectural guidelines.

---

## 🛫 Project Overview
The **Flight Reservation System** is an enterprise Spring-based web application enabling users to browse, search, book, and cancel flight tickets with real-time seat tracking and boarding pass generation. Administrators can schedule and manage flights and monitor upcoming vs. completed journeys with passenger metrics and revenue analytics.

---

## 🛠️ Tools and Technologies
- **Framework:** Spring MVC (Spring Boot 2.7.18 LTS)
- **Language:** Java 8+
- **Presentation Layer (UI):** Spring Forms with JSP (Jakarta JSTL, Tomcat Embedded Jasper)
- **Data Layer / ORM:** Spring JDBC (`JdbcTemplate`, `GeneratedKeyHolder`)
- **Database:** MySQL 8.0 & In-Memory MySQL-mode H2 (pre-configured for zero-setup execution)
- **Validation:** JSR-380 Bean Validation (`@Valid`, `@NotBlank`, `@Size`, `@Email`, `@Min`, `@Max`)
- **Build Tool:** Apache Maven 3.8+ (includes portable `mvnw.cmd`)
- **Testing:** JUnit 5, Mockito, Spring MVC Test framework (`MockMvc`)

---

## 📐 Architecture and Data Flow

### Layered Architecture
- **Presentation Layer:** JSPs located under `/WEB-INF/jsp/` powered by Spring MVC Controllers:
  - `HomeController`: Landing page, flight search, and available flight listings.
  - `AuthController`: User registration, secure login, session management, role redirection.
  - `BookingController`: Step 1 (flight selection & seat availability check), Step 2 (sequential multi-passenger wizard), Step 3 (booking confirmation).
  - `AdminController`: Admin dashboard, add flight with unique flight number validation, and upcoming/completed journey tracking.
  - `UserController`: User dashboard, booking history with seat release on cancellation, and boarding pass ticket views.
- **Business Layer:** Modular Spring Services (`UserService`, `FlightService`, `BookingService`).
- **Data Access Layer:** Modular Spring JDBC DAOs (`UserDao`, `FlightDao`, `BookingDao`, `TicketDao`).
- **Exception Handling:** Centralized `@ControllerAdvice` (`GlobalExceptionHandler`) logging errors and displaying user-friendly error views.

---

## 🗄️ Database Structure (Schema)
Matches the specification ER diagram exactly:

```
+-------------------------------------------------------+
|                        FLIGHT                         |
+-------------------------------------------------------+
| flight_id (BIGINT PK)                                 |
| flight_number (VARCHAR 50 UNIQUE)                     |
| source (VARCHAR 50)                                   |
| destination (VARCHAR 50)                              |
| departure_time (VARCHAR 50)                           |
| arrival_time (VARCHAR 50)                             |
| capacity (INT DEFAULT 20)                             |
| price_per_seat (DOUBLE)                               |
+-------------------------------------------------------+
                           | 1
                           |
                           | N
+-------------------------------------------------------+       1 +---------------------+
|                        BOOKING                        |--------<|        USER         |
+-------------------------------------------------------+         +---------------------+
| booking_id (BIGINT PK)                                |         | user_id (BIGINT PK) |
| booking_date (DATE)                                   |         | email (VARCHAR 100) |
| journey_date (DATE)                                   |         | password (VARCHAR)  |
| seats (INT)                                           |         | role (VARCHAR 50)   |
| total_price (DOUBLE)                                  |         +---------------------+
| status (VARCHAR 50: CONFIRMED / CANCELLED)            |
| flight_id (BIGINT FK -> flight.flight_id)             |
| user_id (BIGINT FK -> user.user_id)                   |
+-------------------------------------------------------+
                           | 1
                           |
                           | N
+-------------------------------------------------------+
|                        TICKET                         |
+-------------------------------------------------------+
| pnr_no (BIGINT PK)                                    |
| passenger_name (VARCHAR 50)                           |
| gender (VARCHAR 50)                                   |
| age (INT)                                             |
| journey_date (DATE)                                   |
| seat_no (VARCHAR 20: e.g. A1, A2)                     |
| booking_id (BIGINT FK -> booking.booking_id)          |
| status (VARCHAR 50: CONFIRMED / CANCELLED)            |
+-------------------------------------------------------+
```

---

## 📋 Implemented Features & Wireframe Compliance

| Screen / Feature | Document Page | Implementation Details |
|---|---|---|
| **Home Page** | Page 5 | Search Flights card (From, To) validated via Spring Forms; User Actions card for login/signup; responsive dark theme navbar. |
| **Search Flights** | Page 5 | Lists matching flights with Flight Number badge, Route, Dep/Arr schedule, Fare, and Book button. Redirects unauthenticated users to login with return redirect. |
| **Sign Up Page** | Page 6 | Email, Password, and Role ("User" / "Admin") inputs validated with Spring Forms. Unique email verification. |
| **Login Page** | Page 6 | Secure authentication, role-based redirection (Admin &rarr; Admin Dashboard, User &rarr; User Dashboard), pre-filled demo accounts. |
| **Book Flight - Booking Form** | Page 6 | Displays flight banner (Flight Number, route, fare). Form validates Journey Date and Passengers (1–10). Checks real-time seat availability against capacity minus confirmed bookings. Blocks booking if insufficient seats. |
| **Book Flight - Add Passenger** | Page 7 | Multi-step sequential passenger wizard (`Passenger 1 of N`, `Passenger 2 of N`). Collects Passenger Name, Age, Gender. Last passenger displays "Complete Booking". |
| **Booking Confirmation** | Page 7 | Green checkmark hero badge, Booking ID display, Secured/Email Sent/Ready feature badges, checklist, and buttons for "Go to Dashboard" and "Print Confirmation". |
| **Admin Dashboard** | Page 8 | Displays admin greeting and email, with action cards for "Add New Flight" and "View Flights". |
| **Add Flight Page** | Page 8 | Form validates all required fields, enforces unique flight number, sets default capacity to 20, allows custom daily schedule & fare. |
| **View Flights** | Page 8 | Displays current date and two tabs: "Upcoming Journeys" & "Completed Journey". Shows only flights with bookings. Columns: Flight Number, Journey Date, Total Passengers, Total Bookings, Total Revenue, Days Until Journey. |
| **User Dashboard** | Page 9 | User greeting and email, with direct action cards for "My Bookings" and "Search Flights". |
| **My Bookings Page** | Page 9 | Lists all bookings made by logged-in user. Includes "View Ticket" button and "Cancel" button. When cancelled, confirmed seats are immediately released, status becomes Cancelled, and Cancel button is disabled. |
| **View Tickets** | Page 9 | Generates individual boarding pass cards for every passenger in the booking with Flight Number, Name, Gender, Age, Journey Date, Seat Number (e.g. A1, A2), PNR, and Print feature. |
| **Global Exception Handling** | Page 10 | Centralized `@ControllerAdvice` handling not found, seat unavailable, unauthorized access, and generic errors with logging and custom error view. |
| **Unit Testing** | Page 11 | 25 unit tests across Controller and Service layers using JUnit 5, Mockito, and Spring MockMvc. |

---

## 👥 Preloaded Credentials & Demo Accounts

| Role | Email | Password |
|---|---|---|
| **Admin** | `admin@deloitte.com` | `admin123` |
| **User** | `user@deloitte.com` | `user123` |
| **User 2** | `john.doe@deloitte.com` | `john123` |

---

## 🚀 How to Run the Application

### Option A: Out-of-the-Box with Built-in Maven Wrapper (Recommended)
No local MySQL installation required! The embedded MySQL-mode database initializes automatically with all tables, constraints, and seed data.

```bash
# 1. Navigate to project root
cd flight-reservation-system

# 2. Run unit tests (Milestone 7)
.\mvnw.cmd test

# 3. Start the application
.\mvnw.cmd spring-boot:run
```

Access the application in your browser at:
👉 **`http://localhost:8080/`**

---

### Option B: Using Local MySQL Server
To run against your local MySQL Server 8.0:
1. Ensure your MySQL server is running on `localhost:3306`.
2. Edit `src/main/resources/application-mysql.properties` if your MySQL username/password differs from `root`/`root`.
3. Launch with the `mysql` profile:
```bash
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=mysql
```

---

## 🧪 Running Unit Tests (Milestone 7)
All 25 unit tests cover Login, Signup, Flight Search, Seat Validation, Multi-passenger Booking, and Cancellations:

```bash
.\mvnw.cmd test
```

Test Results Summary:
- `AuthControllerTest`: 10 passed (Signup, Login, Logout, role redirection, validations, duplicate email checks)
- `UserServiceTest`: 5 passed (Authentication, uniqueness, exception assertions)
- `HomeControllerTest`: 3 passed (Home page, search validations, same-city rejection)
- `AdminControllerTest`: 3 passed (Admin security, Add flight, duplicate flight rejection)
- `BookingServiceTest`: 4 passed (Seat availability calculation, booking creation, ticket generation, cancellations)

---

## 📦 Milestone Roadmap & Completion
- [x] **Milestone 1:** Home Page functionality, Search and List Flights functionalities
- [x] **Milestone 2:** Login and Sign-Up functionalities with Spring Form validation
- [x] **Milestone 3:** Book Flight Functionality (Booking Form, Multi-step Add Passenger, Booking Confirmation)
- [x] **Milestone 4:** Admin Dashboard (Add Flights with unique flight number, View running flights with Upcoming/Completed tabs)
- [x] **Milestone 5:** User Dashboard (My Bookings with seat release, View Tickets with boarding passes)
- [x] **Milestone 6:** Implement Global Exception Handling (`@ControllerAdvice`)
- [x] **Milestone 7:** Implement Unit Testing for Login and Sign-Up Screens (JUnit 5 + MockMvc)
- [x] **Milestone 8:** Push the build to GitHub repository
