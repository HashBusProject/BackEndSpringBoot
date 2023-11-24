package com.hashbus.back.database.data.access;

import com.hashbus.back.model.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class TicketDAO {
    JdbcTemplate jdbcTemplate ;
//    public Ticket getTicketById(long ticket_id) {
////        return jdbcTemplate.queryForObject("select * from ticket where ticket_id = ?  " , new Object [] {ticket_id} , new TicketMapper()) ;
//    }
//    public boolean insertTicket(){
//
//    }
}
