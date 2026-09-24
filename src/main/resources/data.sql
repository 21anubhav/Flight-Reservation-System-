-- Initial Seed Data for Flight Reservation System

-- 1. Users (Admin and User)
INSERT INTO `user` (`email`, `password`, `role`) VALUES 
('admin@deloitte.com', 'admin123', 'ADMIN'),
('user@deloitte.com', 'user123', 'USER'),
('john.doe@deloitte.com', 'john123', 'USER');

-- 2. Flights (All flights run daily, capacity: 20 by default as per document)
INSERT INTO `flight` (`flight_number`, `source`, `destination`, `departure_time`, `arrival_time`, `capacity`, `price_per_seat`) VALUES 
('AI101', 'Bangalore', 'Hyderabad', '10:45', '20:40', 20, 5000.0),
('AI201', 'Bangalore', 'Hyderabad', '18:41', '20:41', 20, 5500.0),
('AI304', 'Bangalore', 'Hyderabad', '17:25', '17:27', 20, 5600.0),
('AI502', 'Mumbai', 'Delhi', '08:00', '10:15', 20, 6000.0),
('AI603', 'Delhi', 'Bangalore', '14:30', '17:15', 20, 7200.0),
('AI705', 'Kolkata', 'Mumbai', '11:15', '14:00', 20, 5800.0);

-- 3. Sample Bookings (to demonstrate Upcoming Journeys and Completed Journeys in Admin View Flights)
-- Past booking (Completed Journey)
INSERT INTO `booking` (`booking_id`, `booking_date`, `journey_date`, `seats`, `total_price`, `status`, `flight_id`, `user_id`) VALUES 
(20001, '2025-11-20', '2025-12-05', 2, 10000.0, 'CONFIRMED', 1, 2);

-- Upcoming bookings
INSERT INTO `booking` (`booking_id`, `booking_date`, `journey_date`, `seats`, `total_price`, `status`, `flight_id`, `user_id`) VALUES 
(20002, '2026-09-01', '2026-10-15', 2, 11000.0, 'CONFIRMED', 2, 2),
(20003, '2026-09-10', '2026-10-20', 1, 5600.0, 'CONFIRMED', 3, 2),
(20004, '2026-09-12', '2026-10-25', 3, 18000.0, 'CONFIRMED', 4, 3);

-- 4. Sample Tickets
INSERT INTO `ticket` (`pnr_no`, `passenger_name`, `gender`, `age`, `journey_date`, `seat_no`, `booking_id`, `status`) VALUES 
(100101, 'Anubhav Verma', 'Male', 28, '2025-12-05', 'A1', 20001, 'CONFIRMED'),
(100102, 'Pooja Verma', 'Female', 26, '2025-12-05', 'A2', 20001, 'CONFIRMED'),

(100103, 'Anubhav Verma', 'Male', 28, '2026-10-15', 'A1', 20002, 'CONFIRMED'),
(100104, 'Sneha Roy', 'Female', 25, '2026-10-15', 'A2', 20002, 'CONFIRMED'),

(100105, 'Anubhav Verma', 'Male', 28, '2026-10-20', 'A1', 20003, 'CONFIRMED'),

(100106, 'John Doe', 'Male', 35, '2026-10-25', 'A1', 20004, 'CONFIRMED'),
(100107, 'Jane Doe', 'Female', 32, '2026-10-25', 'A2', 20004, 'CONFIRMED'),
(100108, 'Jimmy Doe', 'Male', 8, '2026-10-25', 'A3', 20004, 'CONFIRMED');
