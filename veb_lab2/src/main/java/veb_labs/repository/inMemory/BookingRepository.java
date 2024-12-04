package veb_labs.repository.inMemory;


import org.springframework.stereotype.Repository;
import veb_labs.model.EventBooking;
import veb_labs.bootstrap.DataHolder;

import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepository {

    public List<EventBooking> findAll(){
        return DataHolder.eventBookingList;
    }

    public EventBooking save(EventBooking eventBooking){
        DataHolder.eventBookingList.add(eventBooking);
        return eventBooking;
    }

    public List<EventBooking> byUser(String attendeeName) {
        return DataHolder.eventBookingList.stream().filter(r -> r.getEventName().equals(attendeeName)).toList();
    }


    public List<EventBooking> findByEvent(String eventName) {
        return DataHolder.eventBookingList
                .stream()
                .filter(eventBooking -> eventBooking.getEventName().equals(eventName)).toList();
    }

    public Optional<EventBooking> findById(Long id) {
        return DataHolder.eventBookingList.stream().filter(b -> b.getId().equals(id)).findFirst();
    }
}
