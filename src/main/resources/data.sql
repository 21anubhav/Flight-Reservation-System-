-- Initial Seed Data for Flight Reservation System (Enterprise Edition)

-- 1. Users (Admin and User)
INSERT INTO `user` (`email`, `password`, `role`) VALUES 
('admin@deloitte.com', 'admin123', 'ADMIN'),
('user@deloitte.com', 'user123', 'USER'),
('john.doe@deloitte.com', 'john123', 'USER');

-- 2. Flights with Airlines, Terminals, Gates, and Operational Status
INSERT INTO `flight` (`flight_number`, `source`, `destination`, `departure_time`, `arrival_time`, `capacity`, `price_per_seat`, `airline`, `flight_status`, `terminal`, `gate`) VALUES 
('AI101', 'Bangalore', 'Hyderabad', '10:45', '12:00', 20, 5000.0, 'Air India', 'ON TIME', 'T1', 'A12'),
('6E201', 'Bangalore', 'Hyderabad', '18:41', '19:55', 20, 4500.0, 'IndiGo', 'ON TIME', 'T1', 'B04'),
('AI304', 'Bangalore', 'Hyderabad', '17:25', '18:40', 20, 5600.0, 'Air India', 'BOARDING', 'T2', 'G14'),
('UK502', 'Mumbai', 'Delhi', '08:00', '10:15', 20, 6200.0, 'Vistara', 'ON TIME', 'T2', 'C08'),
('SG603', 'Delhi', 'Bangalore', '14:30', '17:15', 20, 5800.0, 'SpiceJet', 'DELAYED 20M', 'T3', 'D22'),
('6E705', 'Kolkata', 'Mumbai', '11:15', '14:00', 20, 5800.0, 'IndiGo', 'ON TIME', 'T1', 'A02'),
('AI808', 'Chennai', 'Goa', '09:30', '11:00', 20, 4200.0, 'Air India', 'SCHEDULED', 'T1', 'G06');

-- 3. Sample Bookings (Upcoming & Completed)
INSERT INTO `booking` (`booking_id`, `booking_date`, `journey_date`, `seats`, `total_price`, `status`, `flight_id`, `user_id`, `coupon_code`, `discount_amount`) VALUES 
(20001, '2025-11-20', '2025-12-05', 2, 8500.0, 'CONFIRMED', 1, 2, 'DELOITTE15', 1500.0),
(20002, '2026-09-01', '2026-10-15', 2, 9000.0, 'CONFIRMED', 2, 2, 'FLY500', 500.0),
(20003, '2026-09-10', '2026-10-20', 1, 4760.0, 'CONFIRMED', 3, 2, 'DELOITTE15', 840.0),
(20004, '2026-09-12', '2026-10-25', 3, 18600.0, 'CONFIRMED', 4, 3, NULL, 0.0);

-- 4. Sample Tickets
INSERT INTO `ticket` (`pnr_no`, `passenger_name`, `gender`, `age`, `journey_date`, `seat_no`, `booking_id`, `status`, `meal_preference`, `baggage`) VALUES 
(100101, 'Anubhav Verma', 'Male', 28, '2025-12-05', '1A', 20001, 'CONFIRMED', 'Vegetarian', '15 kg'),
(100102, 'Pooja Verma', 'Female', 26, '2025-12-05', '1B', 20001, 'CONFIRMED', 'Vegetarian', '15 kg'),

(100103, 'Anubhav Verma', 'Male', 28, '2026-10-15', '2A', 20002, 'CONFIRMED', 'Non-Vegetarian', '15 kg'),
(100104, 'Sneha Roy', 'Female', 25, '2026-10-15', '2B', 20002, 'CONFIRMED', 'Vegetarian', '15 kg'),

(100105, 'Anubhav Verma', 'Male', 28, '2026-10-20', '1A', 20003, 'CONFIRMED', 'Vegetarian', '15 kg'),

(100106, 'John Doe', 'Male', 35, '2026-10-25', '3A', 20004, 'CONFIRMED', 'Non-Vegetarian', '20 kg'),
(100107, 'Jane Doe', 'Female', 32, '2026-10-25', '3B', 20004, 'CONFIRMED', 'Vegetarian', '20 kg'),
(100108, 'Jimmy Doe', 'Male', 8, '2026-10-25', '3C', 20004, 'CONFIRMED', 'Child Meal', '10 kg');
