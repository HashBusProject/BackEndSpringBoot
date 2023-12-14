package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.PointMapper;
import com.hashbus.back.database.mappers.TicketMapper;
import com.hashbus.back.model.Ticket;
import com.sun.source.tree.BreakTree;
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
    JdbcTemplate jdbcTemplate ;
    private TicketMapper ticketMapper;

    //    public Ticket getTicketById(long ticket_id) {
////        return jdbcTemplate.queryForObject("select * from ticket where ticket_id = ?  " , new Object [] {ticket_id} , new TicketMapper()) ;
//    }
//    public boolean insertTicket(){
//
//    }
    public List<Ticket> getTicketsByUserId(Integer id) {
        try {
            return jdbcTemplate.query("select * from tickets where user_id = ?", new Object[]{id}, ticketMapper);
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
    public Boolean buyTicket(Integer userId , Integer journeyId){
        return jdbcTemplate.update("insert into tickets (user_id , journey_id) values (? , ? )" ,
                userId ,
                journeyId ) > 0 ;
    }
    public Integer getNumberOfTickets() {
        return jdbcTemplate.queryForObject("select count(*) from tickets",
                Integer.class);
    }
    public List<Ticket> getAllTickets(){
        return jdbcTemplate.query("select * from tickets" , ticketMapper) ;
    }

}
