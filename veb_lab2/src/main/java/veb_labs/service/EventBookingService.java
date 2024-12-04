package veb_labs.service;


import veb_labs.model.EventBooking;

import java.util.List;
import java.util.List;
import java.util.Optional;

public interface EventBookingService{
    EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, Long numberOfTickets);

    List<EventBooking> findAll();

    Optional<EventBooking> findById(Long id);

    List<EventBooking> byUser(String attendeeName);

    List<EventBooking> findByEvent(String eventName);
}