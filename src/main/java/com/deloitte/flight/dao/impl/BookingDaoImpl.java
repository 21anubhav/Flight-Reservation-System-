package com.deloitte.flight.dao.impl;

import com.deloitte.flight.dao.BookingDao;
import com.deloitte.flight.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingDaoImpl implements BookingDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Booking> bookingRowMapper = (rs, rowNum) -> {
        Booking booking = new Booking();
        booking.setBookingId(rs.getLong("booking_id"));
        Date bDate = rs.getDate("booking_date");
        if (bDate != null) booking.setBookingDate(bDate.toLocalDate());
        Date jDate = rs.getDate("journey_date");
        if (jDate != null) booking.setJourneyDate(jDate.toLocalDate());
        booking.setSeats(rs.getInt("seats"));
        booking.setTotalPrice(rs.getDouble("total_price"));
        booking.setStatus(rs.getString("status"));
        booking.setFlightId(rs.getLong("flight_id"));
        booking.setUserId(rs.getLong("user_id"));

        try {
            booking.setCouponCode(rs.getString("coupon_code"));
            booking.setDiscountAmount(rs.getDouble("discount_amount"));
        } catch (Exception ignored) {
        }

        try {
            booking.setFlightNumber(rs.getString("flight_number"));
            booking.setAirline(rs.getString("airline"));
            booking.setSource(rs.getString("source"));
            booking.setDestination(rs.getString("destination"));
            booking.setDepartureTime(rs.getString("departure_time"));
            booking.setArrivalTime(rs.getString("arrival_time"));
            booking.setTerminal(rs.getString("terminal"));
            booking.setGate(rs.getString("gate"));
        } catch (Exception ignored) {
        }
        try {
            booking.setUserEmail(rs.getString("email"));
        } catch (Exception ignored) {
        }
        return booking;
    };

    @Autowired
    public BookingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Booking save(Booking booking) {
        if (booking.getBookingId() == null) {
            String sql = "INSERT INTO booking (booking_date, journey_date, seats, total_price, status, flight_id, user_id, coupon_code, discount_amount) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1, Date.valueOf(booking.getBookingDate()));
                ps.setDate(2, Date.valueOf(booking.getJourneyDate()));
                ps.setInt(3, booking.getSeats());
                ps.setDouble(4, booking.getTotalPrice());
                ps.setString(5, booking.getStatus());
                ps.setLong(6, booking.getFlightId());
                ps.setLong(7, booking.getUserId());
                ps.setString(8, booking.getCouponCode());
                ps.setDouble(9, booking.getDiscountAmount() != null ? booking.getDiscountAmount() : 0.0);
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                booking.setBookingId(key.longValue());
            }
        } else {
            String sql = "UPDATE booking SET booking_date = ?, journey_date = ?, seats = ?, total_price = ?, " +
                         "status = ?, flight_id = ?, user_id = ?, coupon_code = ?, discount_amount = ? WHERE booking_id = ?";
            jdbcTemplate.update(sql, Date.valueOf(booking.getBookingDate()), Date.valueOf(booking.getJourneyDate()),
                    booking.getSeats(), booking.getTotalPrice(), booking.getStatus(),
                    booking.getFlightId(), booking.getUserId(), booking.getCouponCode(),
                    booking.getDiscountAmount(), booking.getBookingId());
        }
        return booking;
    }

    @Override
    public Optional<Booking> findById(Long bookingId) {
        String sql = "SELECT b.booking_id, b.booking_date, b.journey_date, b.seats, b.total_price, b.status, " +
                     "b.flight_id, b.user_id, b.coupon_code, b.discount_amount, " +
                     "f.flight_number, f.airline, f.source, f.destination, f.departure_time, f.arrival_time, f.terminal, f.gate, u.email " +
                     "FROM booking b " +
                     "JOIN flight f ON b.flight_id = f.flight_id " +
                     "JOIN user u ON b.user_id = u.user_id " +
                     "WHERE b.booking_id = ?";
        try {
            Booking booking = jdbcTemplate.queryForObject(sql, bookingRowMapper, bookingId);
            return Optional.ofNullable(booking);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        String sql = "SELECT b.booking_id, b.booking_date, b.journey_date, b.seats, b.total_price, b.status, " +
                     "b.flight_id, b.user_id, b.coupon_code, b.discount_amount, " +
                     "f.flight_number, f.airline, f.source, f.destination, f.departure_time, f.arrival_time, f.terminal, f.gate, u.email " +
                     "FROM booking b " +
                     "JOIN flight f ON b.flight_id = f.flight_id " +
                     "JOIN user u ON b.user_id = u.user_id " +
                     "WHERE b.user_id = ? " +
                     "ORDER BY b.booking_id DESC";
        return jdbcTemplate.query(sql, bookingRowMapper, userId);
    }

    @Override
    public int countBookedSeats(Long flightId, LocalDate journeyDate) {
        String sql = "SELECT COALESCE(SUM(seats), 0) FROM booking " +
                     "WHERE flight_id = ? AND journey_date = ? AND status = 'CONFIRMED'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, flightId, Date.valueOf(journeyDate));
        return count != null ? count : 0;
    }

    @Override
    public void updateStatus(Long bookingId, String status) {
        String sql = "UPDATE booking SET status = ? WHERE booking_id = ?";
        jdbcTemplate.update(sql, status, bookingId);
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT b.booking_id, b.booking_date, b.journey_date, b.seats, b.total_price, b.status, " +
                     "b.flight_id, b.user_id, b.coupon_code, b.discount_amount, " +
                     "f.flight_number, f.airline, f.source, f.destination, f.departure_time, f.arrival_time, f.terminal, f.gate, u.email " +
                     "FROM booking b " +
                     "JOIN flight f ON b.flight_id = f.flight_id " +
                     "JOIN user u ON b.user_id = u.user_id " +
                     "ORDER BY b.booking_id DESC";
        return jdbcTemplate.query(sql, bookingRowMapper);
    }
}
