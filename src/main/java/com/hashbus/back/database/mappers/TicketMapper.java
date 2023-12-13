package com.hashbus.back.database.mappers;

import com.hashbus.back.database.data.access.JourneyDAO;
import com.hashbus.back.database.data.access.UserDAO;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
@AllArgsConstructor
public class TicketMapper implements RowMapper<Ticket> {
    private JourneyDAO journeyDAO;
    private UserDAO userDAO;
    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("ticket_id"));
        ticket.setJourney(
                journeyDAO.getJourneyById(rs.getInt("journey_id"))
        );
        ticket.setUser(
                userDAO.getUserById(rs.getInt("user_id"))
        );
        return ticket;
    }
}
