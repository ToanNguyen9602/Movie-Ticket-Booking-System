# Movie Ticket Booking System

This project is a full-stack movie ticket booking system that allows users to browse movies, select seats, order snacks, and complete online payments. The admin has full control over managing movies, showtimes, cinemas, and monitoring ticket sales and income analytics. The project uses Spring Boot for the backend and Angular for the frontend.

## Features

### Admin Panel
- Manage movies, cinemas, showtimes, and ticket prices.
- Oversee ticket sales, income statistics, and user activity.
- Manage and update snacks (e.g., popcorn, drinks) for movie sessions.
- View analytics reports for top movies, users, and overall income.

### Customer Features
- Browse movies by city and cinema.
- Filter showtimes based on the selected movie, cinema, or date.
- Select and book seats with a real-time seating chart.
- Order snacks and drinks while booking movie tickets.
- Pay securely online using VNPay (Sandbox environment).
- View booking history, including tickets and snack orders.

### Core Features
- **Seat selection**: Customers can select their preferred seats from available options.
- **Snack ordering**: Customers can order snacks and drinks along with their movie tickets.
- **Payment integration**: VNPay Sandbox used for secure payment transactions.
- **Real-time updates**: Admin updates on movie and showtime information are immediately reflected for customers.
- **Analytics**: The admin dashboard provides real-time data on income, movie performance, and user activity.

## Database Structure

The database is structured into several modules, including movies, cinemas, users, and ticket orders. Below is an overview of the main tables and their relationships.

### Movies and Showtimes

- `movies`: Stores movie information, such as title, description, and duration.
- `showtimes`: Stores information about movie showtimes at various cinemas.
- `cinemas`: Stores information about cinema locations and screens.

### User and Booking

- `users`: Contains user account information and role assignments (Admin, Customer).
- `bookings`: Stores information about user bookings, including selected seats and snacks.
- `payments`: Manages payment transactions processed via VNPay.
