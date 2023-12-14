package com.hashbus.back.service;

import com.hashbus.back.database.data.access.BusDAO;
import com.hashbus.back.database.data.access.JourneyDAO;
import com.hashbus.back.database.data.access.PointDAO;
import com.hashbus.back.database.data.access.TicketDAO;
import com.hashbus.back.exceptions.OrganizerExeption;
import com.hashbus.back.model.Journey;
import com.hashbus.back.model.Point;
import com.hashbus.back.model.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrganizerService {
    private JourneyDAO journeyDAO;
    private BusDAO busDAO;
    private TicketDAO ticketDAO  ;
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

    public List<Journey> getAllJourneys() {
        List < Journey >  journeys = journeyDAO.getAllJourneys();
        return journeys;
    }

    public boolean deleteJourney(Journey journey){
        Journey journey1 = journeyDAO.getJourneyById(journey.getId()) ;
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
    public Integer getNumberOfJourneys(){
        return journeyDAO.getNumberOfJourneys();
    }
    public Integer getNumberOfTickets(){
        return ticketDAO.getNumberOfTickets() ;
    }

    public List<Ticket> getAllTickets(){
        return ticketDAO.getAllTickets() ;
    }

}
