package com.hashbus.back.database.data.access;

import com.hashbus.back.database.mappers.BusMapper;
import com.hashbus.back.model.Bus;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BusDAO {
    private JdbcTemplate jdbcTemplate;
    private BusMapper busMapper;

    public Bus getBusById(long id) {
        return jdbcTemplate.queryForObject("select * from buses where bus_id = ?", new Object[]{id}, busMapper);
    }

    public List<Bus> getAllBuses(){
        return jdbcTemplate.query("select * from  buses ", busMapper);

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
        return jdbcTemplate.update("update buses set isWorking = ?, driver_ID = ? where bus_id = ?",
                bus.getIsWorking() ,
                bus.getDriver().getUserID(),
                bus.getId()
                ) > 0;
    }

    public Integer getNumberOfBuses() {
        return jdbcTemplate.queryForObject("select count(*) from buses ", Integer.class );

    }

    public List<Integer> getIdOfBuses() {
        return jdbcTemplate.query("select bus_id from buses ",
                (rs ,  rowNum) -> rs.getInt("bus_id"));
    }


}
