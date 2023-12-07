package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.BusMapper;
import com.hashbus.back.model.Bus;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BusDAO {
    private JdbcTemplate jdbcTemplate;
    private BusMapper busMapper;

    public Bus getBusById(long id) {
        return jdbcTemplate.queryForObject("select * from bus where bus_id = ?", new Object[]{id}, busMapper);
    }

    public Bus getAllBuses(){
        return jdbcTemplate.queryForObject("select * from  buses ", busMapper);

    }
    public boolean insertBus(Bus bus) {
        return jdbcTemplate.update("insert into buses (driver_ID , isWorking ) values (? , ?  )",
                bus.getDriver().getUserID() ,
                bus.getIsWorking()
        ) > 0;
    }

    public boolean deleteBus(long id) {
        return jdbcTemplate.update("delete from buses where bus_id = ?  ",
                id
        ) > 0;
    }

    public boolean updateBus(Bus bus){
        return jdbcTemplate.update("update buses set working = ?, driver_ID = ? where id = ?",
                bus.getId(),
                bus.getDriver().getUserID(),
                bus.getIsWorking()
        ) > 0;
    }


}
