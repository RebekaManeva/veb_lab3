package veb_labs.service;

import veb_labs.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();

    List<Event> findAllByPopularityScore(double popularityScore);
    List<Event> findAllByDescription(String description);
    List<Event> findAllByLocation_Id(Long locationId);

    List<Event> searchEvents(String text);

    List<Event> searchByRating(Double rating);

    Optional<Event> findEventById(Long id);

    void deleteEventById(Long id);

    Optional<Event> updateOrCreateEvent(Long eventId,String name, String description, double popularityScore, Long locationId);
}