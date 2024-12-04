package veb_labs.web.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import veb_labs.model.Event;
import veb_labs.model.EventBooking;
import veb_labs.model.Location;
import veb_labs.model.exception.LocationNotFoundException;
import veb_labs.service.EventBookingService;
import veb_labs.service.EventService;
import veb_labs.service.LocationService;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;
    private final EventBookingService eventBookingService;

    public EventController(EventService eventService, LocationService locationService, EventBookingService eventBookingService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.eventBookingService = eventBookingService;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);

        List<Event> events = eventService.listAll();
        model.addAttribute("events", events);

        return "listEvents";
    }

    @PostMapping
    public String searchEvents(@RequestParam(required = false) String text,
                               @RequestParam(required = false) Double rating,
                               @RequestParam(required = false) Long location_id,
                               Model model) {
        List<Event> events = eventService.listAll();
        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);
        if (!text.isEmpty()) {
//            events = eventService.searchEvents(text);
            events = eventService.findAllByDescription(text);
        }
        if (rating != null) {
            List<Event> temp;
//            temp = eventService.searchByRating(rating);
            temp = eventService.findAllByPopularityScore(rating);
            events = temp.stream().filter(events::contains).toList();
        }

        if (location_id != null) {
            List<Event> temp;
            temp = eventService.findAllByLocation_Id(location_id);
            events = temp.stream().filter(events::contains).toList();
        }

        model.addAttribute("events", events);

        return "listEvents";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.findEventById(id).orElseThrow(() -> new LocationNotFoundException(id));
        eventService.deleteEventById(id);
        return "redirect:/events";
    }

    @GetMapping("/edit-form/{id}")
    public String editEventsPage(@PathVariable Long id, Model model) {
        if (this.eventService.findEventById(id).isPresent()) {
            List<Location> locations = locationService.findAll();
            model.addAttribute("locations", locations);
            model.addAttribute("event", this.eventService.findEventById(id).get());
            return "add-event";
        }
        return "redirect:/events?error=EventNotFound";
    }


    @GetMapping("/add-form")
    public String addEventPage(Model model) {
        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);
        return "add-event";
    }

    @PostMapping("/add")
    public String saveEvent(@RequestParam(required = false) Long eventId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam Double popularityScore,
                            @RequestParam Long locationId) {
        this.eventService.updateOrCreateEvent(eventId, name, description, popularityScore, locationId);
        return "redirect:/events";
    }

    @GetMapping("/details/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.findEventById(id).orElseThrow(() -> new LocationNotFoundException(id));
        model.addAttribute("eventBookings",eventBookingService.findByEvent(event.getName()));
        model.addAttribute("event", event);
        return "eventDetails";
    }

}