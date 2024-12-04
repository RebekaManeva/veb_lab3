package veb_labs.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import veb_labs.model.Event;
import veb_labs.model.EventBooking;
import veb_labs.model.exception.LocationNotFoundException;
import veb_labs.service.EventBookingService;
import veb_labs.service.EventService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventBookingConfirmation")
public class EventBookingController {
    private final EventBookingService bookingService;

    public EventBookingController(EventBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public String placeBooking(@RequestParam String selectedEventName,
                               @RequestParam Long selectedNumberOfTickets,
                               @RequestParam String attendeeName,
                               HttpServletRequest request) {
        EventBooking currentBooking = bookingService.placeBooking(selectedEventName, attendeeName, request.getLocalAddr(), selectedNumberOfTickets);
        return "redirect:/eventBookingConfirmation?currentBookingId=" + currentBooking.getId();
    }

    @GetMapping
    public String getBookingConfirmationPage(@RequestParam Long currentBookingId,
                                             @RequestParam(required = false)
                                             String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        try {
            EventBooking currentBooking = bookingService.findById(currentBookingId)
                    .orElseThrow(() -> new LocationNotFoundException(currentBookingId));

            List<EventBooking> bookingEvents = bookingService.byUser(currentBooking.getAttendeeName());
            model.addAttribute("bookingEvents", bookingEvents);
            model.addAttribute("currentBooking", currentBooking);

            return "bookingConfirmation";
        } catch (LocationNotFoundException e) {
            return "redirect:/events?error=" + e.getMessage();
        }
    }
}