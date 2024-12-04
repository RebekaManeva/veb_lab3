package veb_labs.service.impl;


import org.springframework.stereotype.Service;
import veb_labs.model.EventBooking;
import veb_labs.repository.inMemory.BookingRepository;
import veb_labs.service.EventBookingService;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EventBookingServiceImpl implements EventBookingService {

    private final BookingRepository inMemoryEventBookingRepository;

    public EventBookingServiceImpl(BookingRepository inMemoryEventBookingRepository) {
        this.inMemoryEventBookingRepository = inMemoryEventBookingRepository;
    }


    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, Long numberOfTickets) {
        return inMemoryEventBookingRepository.save(new EventBooking(eventName, attendeeName, attendeeAddress, numberOfTickets));
    }

    @Override
    public List<EventBooking> findAll() {
        return inMemoryEventBookingRepository.findAll();
    }

    @Override
    public Optional<EventBooking> findById(Long id) {
        return inMemoryEventBookingRepository.findById(id);
    }

    @Override
    public List<EventBooking> byUser(String attendeeName) {
        return inMemoryEventBookingRepository.byUser(attendeeName);
    }

    @Override
    public List<EventBooking> findByEvent(String eventName) {
        return inMemoryEventBookingRepository.findByEvent(eventName);
    }
}