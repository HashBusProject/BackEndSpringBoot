package com.hashbus.back.service;

import com.hashbus.back.database.data.access.BusDAO;
import com.hashbus.back.database.data.access.JourneyDAO;
import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.exceptions.OrganizerExeption;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Point;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizerService {
    private JourneyDAO journeyDAO;
    private BusDAO busDAO;
    private PointDAO pointDAO;
    public Journey addJourney(Journey journey) {
        if (journey == null) {
            throw new OrganizerExeption("please insert validate data!!");
        }
        if (journeyDAO.insertJourney(journey))
            return journey;
       return null;
    }

    public Journey editJourney(Journey journey){
        if(journey == null){
            throw new OrganizerExeption("please insert validate data!!");
        }
        if( journeyDAO.update(journey) )
            return journey;
        return null;
    }

    public Journey viewJourney(Journey journey) {
        Journey journey1 = journeyDAO.getAllJourney();
        if (journey == null)
            throw new OrganizerExeption("Please insert validate data!!");
        return journey1;
    }

    public boolean deleteJourney(Journey journey){
        Journey journey1 = journeyDAO.getJourney(journey.getId()) ;
        if(journey1 == null)
            throw new OrganizerExeption("this journey does not exist") ;
        return journeyDAO.deleteJourney(journey);
    }
    public boolean addPoint(Point point){
        return pointDAO.insertPoint(point) ;
    }
    public List<Point> viewAllPoint(){
        return pointDAO.getAllPoint();
    }

}
