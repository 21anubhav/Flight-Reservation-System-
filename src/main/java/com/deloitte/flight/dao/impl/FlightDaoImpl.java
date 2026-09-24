package com.deloitte.flight.dao.impl;

import com.deloitte.flight.dao.FlightDao;
import com.deloitte.flight.dto.FlightJourneySummaryDto;
import com.deloitte.flight.model.Flight;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Repository
public class FlightDaoImpl implements FlightDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Flight> flightRowMapper = (rs, rowNum) -> {
        Flight flight = new Flight();
        flight.setFlightId(rs.getLong("flight_id"));
        flight.setFlightNumber(rs.getString("flight_number"));
        flight.setSource(rs.getString("source"));
        flight.setDestination(rs.getString("destination"));
        flight.setDepartureTime(rs.getString("departure_time"));
        flight.setArrivalTime(rs.getString("arrival_time"));
        flight.setCapacity(rs.getInt("capacity"));
        flight.setPricePerSeat(rs.getDouble("price_per_seat"));
        return flight;
    };

    @Autowired
    public FlightDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Flight save(Flight flight) {
        if (flight.getFlightId() == null) {
            String sql = "INSERT INTO flight (flight_number, source, destination, departure_time, arrival_time, capacity, price_per_seat) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, flight.getFlightNumber());
                ps.setString(2, flight.getSource());
                ps.setString(3, flight.getDestination());
                ps.setString(4, flight.getDepartureTime());
                ps.setString(5, flight.getArrivalTime());
                ps.setInt(6, flight.getCapacity() != null ? flight.getCapacity() : 20);
                ps.setDouble(7, flight.getPricePerSeat());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                flight.setFlightId(key.longValue());
            }
        } else {
            String sql = "UPDATE flight SET flight_number = ?, source = ?, destination = ?, departure_time = ?, arrival_time = ?, " +
                         "capacity = ?, price_per_seat = ? WHERE flight_id = ?";
            jdbcTemplate.update(sql, flight.getFlightNumber(), flight.getSource(), flight.getDestination(),
                    flight.getDepartureTime(), flight.getArrivalTime(), flight.getCapacity(),
                    flight.getPricePerSeat(), flight.getFlightId());
        }
        return flight;
    }

    @Override
    public Optional<Flight> findById(Long flightId) {
        String sql = "SELECT flight_id, flight_number, source, destination, departure_time, arrival_time, capacity, price_per_seat " +
                     "FROM flight WHERE flight_id = ?";
        try {
            Flight flight = jdbcTemplate.queryForObject(sql, flightRowMapper, flightId);
            return Optional.ofNullable(flight);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Flight> findByFlightNumber(String flightNumber) {
        String sql = "SELECT flight_id, flight_number, source, destination, departure_time, arrival_time, capacity, price_per_seat " +
                     "FROM flight WHERE UPPER(flight_number) = UPPER(?)";
        try {
            Flight flight = jdbcTemplate.queryForObject(sql, flightRowMapper, flightNumber);
            return Optional.ofNullable(flight);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Flight> findAll() {
        String sql = "SELECT flight_id, flight_number, source, destination, departure_time, arrival_time, capacity, price_per_seat " +
                     "FROM flight ORDER BY flight_id ASC";
        return jdbcTemplate.query(sql, flightRowMapper);
    }

    @Override
    public List<Flight> findBySourceAndDestination(String source, String destination) {
        String sql = "SELECT flight_id, flight_number, source, destination, departure_time, arrival_time, capacity, price_per_seat " +
                     "FROM flight WHERE LOWER(source) = LOWER(?) AND LOWER(destination) = LOWER(?) ORDER BY departure_time ASC";
        return jdbcTemplate.query(sql, flightRowMapper, source.trim(), destination.trim());
    }

    @Override
    public boolean existsByFlightNumber(String flightNumber) {
        String sql = "SELECT COUNT(*) FROM flight WHERE UPPER(flight_number) = UPPER(?)";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, flightNumber.trim());
        return count != null && count > 0;
    }

    @Override
    public List<FlightJourneySummaryDto> findBookedJourneysSummary(boolean upcoming, LocalDate currentDate) {
        // Only flights for which bookings are done should be displayed here
        String comparison = upcoming ? ">=" : "<";
        String order = upcoming ? "ASC" : "DESC";

        String sql = "SELECT f.flight_id, f.flight_number, b.journey_date, " +
                     "SUM(b.seats) as total_passengers, " +
                     "COUNT(b.booking_id) as total_bookings, " +
                     "SUM(b.total_price) as total_revenue " +
                     "FROM flight f " +
                     "JOIN booking b ON f.flight_id = b.flight_id " +
                     "WHERE b.status = 'CONFIRMED' AND b.journey_date " + comparison + " ? " +
                     "GROUP BY f.flight_id, f.flight_number, b.journey_date " +
                     "ORDER BY b.journey_date " + order + ", f.flight_number ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            FlightJourneySummaryDto dto = new FlightJourneySummaryDto();
            dto.setFlightId(rs.getLong("flight_id"));
            dto.setFlightNumber(rs.getString("flight_number"));
            Date jDate = rs.getDate("journey_date");
            LocalDate journeyDate = jDate != null ? jDate.toLocalDate() : null;
            dto.setJourneyDate(journeyDate);
            dto.setTotalPassengers(rs.getInt("total_passengers"));
            dto.setTotalBookings(rs.getInt("total_bookings"));
            dto.setTotalRevenue(rs.getDouble("total_revenue"));

            if (journeyDate != null) {
                if (journeyDate.isBefore(currentDate)) {
                    dto.setDaysUntilJourney("Completed");
                    dto.setCompleted(true);
                } else if (journeyDate.isEqual(currentDate)) {
                    dto.setDaysUntilJourney("Today");
                    dto.setCompleted(false);
                } else {
                    long days = ChronoUnit.DAYS.between(currentDate, journeyDate);
                    dto.setDaysUntilJourney(days + (days == 1 ? " day" : " days"));
                    dto.setCompleted(false);
                }
            }
            return dto;
        }, Date.valueOf(currentDate));
    }
}
