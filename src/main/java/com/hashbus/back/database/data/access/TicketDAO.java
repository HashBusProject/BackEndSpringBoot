package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.PointMapper;
import com.hashbus.back.database.mappers.TicketMapper;
import com.hashbus.back.model.Ticket;
import com.sun.source.tree.BreakTree;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

@AllArgsConstructor
@Repository
public class TicketDAO {
    JdbcTemplate jdbcTemplate;
    private TicketMapper ticketMapper;

    public List<Ticket> getTicketsByUserId(Integer id) {
        try {
            return jdbcTemplate.query("select * from tickets where user_id = ? and used=0", new Object[]{id}, ticketMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public Boolean buyTicket(Integer userId, Integer journeyId) {
        return jdbcTemplate.update("insert into tickets (user_id , journey_id, used) values (? , ?, 0)",
                userId,
                journeyId) > 0;
    }

    public Integer getNumberOfTickets() {
        return jdbcTemplate.queryForObject("select count(*) from tickets",
                Integer.class);
    }

    public List<Ticket> getAllTickets() {
        return jdbcTemplate.query("select * from tickets", ticketMapper);
    }

    public Boolean makeTheTicketUsedByUserIdAndJourneyId(Integer userId, Integer journeyId) {
        boolean x =
                jdbcTemplate.update("""
                UPDATE tickets s
                    JOIN (
                        SELECT ticket_ID FROM tickets
                        WHERE journey_ID = ? AND user_ID = ? AND used = 0
                        LIMIT 1
                    ) t ON s.ticket_ID = t.ticket_ID
                SET s.used = 1 where s.used = 0;
                """, journeyId, userId)
                > 0;
        return x;
    }

    public Boolean deleteAllTickets(){
        return jdbcTemplate.update("delete from tickets") > 0 ;
    }

}
