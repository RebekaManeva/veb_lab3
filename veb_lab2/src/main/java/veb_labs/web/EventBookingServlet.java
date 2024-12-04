package veb_labs.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import veb_labs.model.EventBooking;
import veb_labs.service.EventBookingService;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "EventBookingServlet", urlPatterns = "/eventBooking")
public class EventBookingServlet extends HttpServlet {
    private final EventBookingService eventBookingService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventBookingServlet(EventBookingService eventBookingService, SpringTemplateEngine springTemplateEngine) {
        this.eventBookingService = eventBookingService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        HttpSession session = req.getSession();
        String eventName = session.getAttribute("selectedEventName").toString();
        String attendeeName = session.getAttribute("attendeeName").toString();
        String numTickets = session.getAttribute("numberOfTickets").toString();

        String clientIpAddress = req.getRemoteAddr();

        context.setVariable("attendeeName", attendeeName);
        context.setVariable("eventName", eventName);
        context.setVariable("numTickets", numTickets);
        context.setVariable("clientIpAddress", clientIpAddress);

        eventBookingService.placeBooking(eventName, attendeeName, clientIpAddress, Long.parseLong(numTickets));

        List<EventBooking> bookings = eventBookingService.byUser(attendeeName);

        context.setVariable("bookings",bookings);
        springTemplateEngine.process("bookingConfirmation.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}