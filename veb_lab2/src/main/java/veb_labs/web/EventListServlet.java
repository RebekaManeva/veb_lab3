package veb_labs.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import veb_labs.model.Event;
import veb_labs.service.EventService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventListServlet", urlPatterns = "")
public class EventListServlet extends HttpServlet {

    private final EventService eventService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventListServlet(EventService eventService, SpringTemplateEngine springTemplateEngine) {
        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        List<Event> events = eventService.listAll();

        Object text = req.getParameter("text");
        Object rating = req.getParameter("rating");


        if(text != null) {
            String textStr = text.toString();
            if(!textStr.isEmpty()) {
                events = eventService.searchEvents(textStr);
            }
        }

        if(rating != null){
            String ratingStr = rating.toString();
            if(!ratingStr.isEmpty()){
                List<Event> temp;
                temp = eventService.searchByRating(Double.parseDouble(ratingStr));
                events = temp.stream().filter(events::contains).toList();
            }
        }

        context.setVariable("events", events);

        springTemplateEngine.process("listEvents.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventName = req.getParameter("selectedEventName");
        String numberOfTicketsStr = req.getParameter("selectedNumberOfTickets");
        String attendeeName = req.getSession().getAttribute("name").toString();

        Long numTickets = 0L;

        if (numberOfTicketsStr != null && !numberOfTicketsStr.isEmpty()) {
            numTickets = Long.parseLong(numberOfTicketsStr);
        }

        req.getSession().setAttribute("selectedEventName", eventName);
        req.getSession().setAttribute("numberOfTickets", numTickets);
        req.getSession().setAttribute("attendeeName", attendeeName);

        resp.sendRedirect("/eventBooking");
    }
}