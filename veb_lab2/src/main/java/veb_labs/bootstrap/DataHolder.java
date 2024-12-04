package veb_labs.bootstrap;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import veb_labs.model.Event;
import veb_labs.model.EventBooking;
import veb_labs.model.Location;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Event> events = new ArrayList<>();
    public static List<EventBooking> eventBookingList = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();


    @PostConstruct
    public void init() {
        locations.add(new Location("NAME1", "ADDRESS1", "235", "DESCRIPTION1"));
        locations.add(new Location("NAME2", "ADDRESS2", "150", "DESCRIPTION2"));
        locations.add(new Location("NAME3", "ADDRESS3", "1200", "DESCRIPTION3"));
        locations.add(new Location("NAME4", "ADDRESS4", "410", "DESCRIPTION4"));
        locations.add(new Location("NAME5", "ADDRESS5", "680", "DESCRIPTION5"));

        events.add(new Event("NAME1", "DESCRIPTION1", 82.0, locations.get(2)));
        events.add(new Event("NAME2", "DESCRIPTION2", 70.5, locations.get(4)));
        events.add(new Event("NAME3", "DESCRIPTION3", 35.0, locations.get(0)));
        events.add(new Event("NAME4", "DESCRIPTION4", 90.0, locations.get(1)));
        events.add(new Event("NAME5", "DESCRIPTION5", 45.0, locations.get(2)));
        events.add(new Event("NAME6", "DESCRIPTION6", 98.5, locations.get(3)));
        events.add(new Event("NAME7", "DESCRIPTION7", 12.0, locations.get(4)));
        events.add(new Event("NAME8", "DESCRIPTION8", 20.0, locations.get(2)));
        events.add(new Event("NAME9", "DESCRIPTION9", 65.0, locations.get(0)));
        events.add(new Event("NAME10", "DESCRIPTION10", 8.5, locations.get(3)));

        eventBookingList = new ArrayList<>();
        eventBookingList.add(new EventBooking("Event1test", "Attendee1", "Address1", 3L));
    }




}
