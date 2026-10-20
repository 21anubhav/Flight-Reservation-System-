package com.deloitte.flight.dao.impl;

import com.deloitte.flight.dao.TicketDao;
import com.deloitte.flight.model.Ticket;
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
public class TicketDaoImpl implements TicketDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Ticket> ticketRowMapper = (rs, rowNum) -> {
        Ticket ticket = new Ticket();
        ticket.setPnrNo(rs.getLong("pnr_no"));
        ticket.setPassengerName(rs.getString("passenger_name"));
        ticket.setGender(rs.getString("gender"));
        ticket.setAge(rs.getInt("age"));
        Date jDate = rs.getDate("journey_date");
        if (jDate != null) ticket.setJourneyDate(jDate.toLocalDate());
        ticket.setSeatNo(rs.getString("seat_no"));
        ticket.setBookingId(rs.getLong("booking_id"));
        ticket.setStatus(rs.getString("status"));

        try {
            ticket.setMealPreference(rs.getString("meal_preference"));
            ticket.setBaggage(rs.getString("baggage"));
        } catch (Exception ignored) {
        }

        try {
            ticket.setFlightNumber(rs.getString("flight_number"));
            ticket.setAirline(rs.getString("airline"));
            ticket.setSource(rs.getString("source"));
            ticket.setDestination(rs.getString("destination"));
            ticket.setDepartureTime(rs.getString("departure_time"));
            ticket.setArrivalTime(rs.getString("arrival_time"));
            ticket.setTerminal(rs.getString("terminal"));
            ticket.setGate(rs.getString("gate"));
        } catch (Exception ignored) {
        }
        return ticket;
    };

    @Autowired
    public TicketDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ticket save(Ticket ticket) {
        if (ticket.getPnrNo() == null) {
            String sql = "INSERT INTO ticket (passenger_name, gender, age, journey_date, seat_no, booking_id, status, meal_preference, baggage) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, ticket.getPassengerName());
                ps.setString(2, ticket.getGender());
                ps.setInt(3, ticket.getAge());
                ps.setDate(4, Date.valueOf(ticket.getJourneyDate()));
                ps.setString(5, ticket.getSeatNo());
                ps.setLong(6, ticket.getBookingId());
                ps.setString(7, ticket.getStatus());
                ps.setString(8, ticket.getMealPreference());
                ps.setString(9, ticket.getBaggage());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                ticket.setPnrNo(key.longValue());
            }
        } else {
            String sql = "UPDATE ticket SET passenger_name = ?, gender = ?, age = ?, journey_date = ?, " +
                         "seat_no = ?, booking_id = ?, status = ?, meal_preference = ?, baggage = ? WHERE pnr_no = ?";
            jdbcTemplate.update(sql, ticket.getPassengerName(), ticket.getGender(), ticket.getAge(),
                    Date.valueOf(ticket.getJourneyDate()), ticket.getSeatNo(),
                    ticket.getBookingId(), ticket.getStatus(), ticket.getMealPreference(),
                    ticket.getBaggage(), ticket.getPnrNo());
        }
        return ticket;
    }

    @Override
    public void saveAll(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            save(ticket);
        }
    }

    @Override
    public List<Ticket> findByBookingId(Long bookingId) {
        String sql = "SELECT t.pnr_no, t.passenger_name, t.gender, t.age, t.journey_date, t.seat_no, t.booking_id, t.status, " +
                     "t.meal_preference, t.baggage, " +
                     "f.flight_number, f.airline, f.source, f.destination, f.departure_time, f.arrival_time, f.terminal, f.gate " +
                     "FROM ticket t " +
                     "JOIN booking b ON t.booking_id = b.booking_id " +
                     "JOIN flight f ON b.flight_id = f.flight_id " +
                     "WHERE t.booking_id = ? " +
                     "ORDER BY t.pnr_no ASC";
        return jdbcTemplate.query(sql, ticketRowMapper, bookingId);
    }

    @Override
    public Optional<Ticket> findByPnrNo(Long pnrNo) {
        String sql = "SELECT t.pnr_no, t.passenger_name, t.gender, t.age, t.journey_date, t.seat_no, t.booking_id, t.status, " +
                     "t.meal_preference, t.baggage, " +
                     "f.flight_number, f.airline, f.source, f.destination, f.departure_time, f.arrival_time, f.terminal, f.gate " +
                     "FROM ticket t " +
                     "JOIN booking b ON t.booking_id = b.booking_id " +
                     "JOIN flight f ON b.flight_id = f.flight_id " +
                     "WHERE t.pnr_no = ?";
        try {
            Ticket ticket = jdbcTemplate.queryForObject(sql, ticketRowMapper, pnrNo);
            return Optional.ofNullable(ticket);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateStatusByBookingId(Long bookingId, String status) {
        String sql = "UPDATE ticket SET status = ? WHERE booking_id = ?";
        jdbcTemplate.update(sql, status, bookingId);
    }

    @Override
    public List<String> findBookedSeatNumbers(Long flightId, LocalDate journeyDate) {
        String sql = "SELECT t.seat_no FROM ticket t " +
                     "JOIN booking b ON t.booking_id = b.booking_id " +
                     "WHERE b.flight_id = ? AND b.journey_date = ? AND t.status = 'CONFIRMED'";
        return jdbcTemplate.queryForList(sql, String.class, flightId, Date.valueOf(journeyDate));
    }
}
