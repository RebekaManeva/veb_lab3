package veb_labs.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import veb_labs.model.Event;
import veb_labs.model.exception.LocationNotFoundException;
import veb_labs.repository.jpa.EventRepository;
import veb_labs.repository.inMemory.InMemoryEventRepository;
import veb_labs.repository.inMemory.InMemoryLocationRepository;
import veb_labs.service.EventService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final InMemoryEventRepository inMemoryEventRepository;
    private final EventRepository eventRepository;
    private final InMemoryLocationRepository locationRepository;

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> findAllByPopularityScore(double popularityScore) {
        return eventRepository.findAllByPopularityScore(popularityScore);
    }

    @Override
    public List<Event> findAllByDescription(String description) {
        return eventRepository.findAllByDescription(description);
    }

    @Override
    public List<Event> findAllByLocation_Id(Long locationId) {
        return eventRepository.findAllByLocation_Id(locationId);
    }


    @Override
    public List<Event> searchEvents(String text) {
        return inMemoryEventRepository.searchEvents(text);
    }

    @Override
    public List<Event> searchByRating(Double rating) {
        return inMemoryEventRepository.searchByRating(rating);
    }

    @Override
    public Optional<Event> findEventById(Long id) {
        return eventRepository.findEventById(id);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }


    public Optional<Event> fillInEventPropertiesAndSave(Event event, String name, String description, double popularityScore, Long locationId){
        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId)));
        if (eventRepository.findEventById(event.getId()).isPresent()){
            eventRepository.deleteById(event.getId());
        }
        eventRepository.save(event);
        return Optional.of(event);
    }

    public Optional<Event> updateOrCreateEvent(Long eventId,String name, String description, double popularityScore,Long locationId) {
        if (name == null || description == null || popularityScore < 0 || locationId == null){
            throw new IllegalArgumentException();
        }
        Optional<Event> event = findEventById(eventId);
        return fillInEventPropertiesAndSave(event.orElseGet(Event::new), name, description, popularityScore, locationId);
    }
}