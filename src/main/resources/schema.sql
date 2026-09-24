-- Deloitte Flight Reservation System Database Schema
-- Matches Page 4 ER Diagram exactly

DROP TABLE IF EXISTS `ticket`;
DROP TABLE IF EXISTS `booking`;
DROP TABLE IF EXISTS `flight`;
DROP TABLE IF EXISTS `user`;

-- Users Table: Stores user accounts and roles.
CREATE TABLE `user` (
    `user_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(50) NOT NULL
);

-- Flights Table: Manages flight info, source/destination, seat capacity, prices.
CREATE TABLE `flight` (
    `flight_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `flight_number` VARCHAR(50) NOT NULL UNIQUE,
    `source` VARCHAR(50) NOT NULL,
    `destination` VARCHAR(50) NOT NULL,
    `departure_time` VARCHAR(50) NOT NULL,
    `arrival_time` VARCHAR(50) NOT NULL,
    `capacity` INT NOT NULL DEFAULT 20,
    `price_per_seat` DOUBLE NOT NULL
);

-- Bookings Table: Tracks each booking transaction.
CREATE TABLE `booking` (
    `booking_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `booking_date` DATE NOT NULL,
    `journey_date` DATE NOT NULL,
    `seats` INT NOT NULL,
    `total_price` DOUBLE NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `flight_id` BIGINT NOT NULL,
    `user_id` BIGINT NOT NULL,
    CONSTRAINT `fk_booking_flight` FOREIGN KEY (`flight_id`) REFERENCES `flight`(`flight_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_booking_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE
);

-- Tickets Table: Holds data for each passenger's ticket.
CREATE TABLE `ticket` (
    `pnr_no` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `passenger_name` VARCHAR(50) NOT NULL,
    `gender` VARCHAR(50) NOT NULL,
    `age` INT NOT NULL,
    `journey_date` DATE NOT NULL,
    `seat_no` VARCHAR(20) NOT NULL,
    `booking_id` BIGINT NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    CONSTRAINT `fk_ticket_booking` FOREIGN KEY (`booking_id`) REFERENCES `booking`(`booking_id`) ON DELETE CASCADE
);
