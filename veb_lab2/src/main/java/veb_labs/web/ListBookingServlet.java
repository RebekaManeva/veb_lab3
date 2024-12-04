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
import veb_labs.service.EventBookingService;

import java.io.IOException;

@WebServlet(name = "listBookings", urlPatterns = "/servlet/listBookings")
public class ListBookingServlet extends HttpServlet {

    private final EventBookingService eventBookingService;
    private final SpringTemplateEngine springTemplateEngine;

    public ListBookingServlet(EventBookingService eventBookingService, SpringTemplateEngine springTemplateEngine) {
        this.eventBookingService = eventBookingService;
        this.springTemplateEngine = springTemplateEngine;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String eventName = req.getParameter("searchText");
        String numTicketsString = req.getParameter("numTickets");
        String attendeeName = req.getParameter("attendeeName");


        if (eventName != null && eventName.isEmpty()) {
            eventName = null;
        }
        if(numTicketsString !=null && numTicketsString.isEmpty()){
            numTicketsString=null;
        }

        if(attendeeName !=null && attendeeName.isEmpty()){
            attendeeName=null;
        }



        if(eventName!=null){
            if(numTicketsString!=null){
                if(attendeeName!=null){
                    context.setVariable("eventBookings",eventBookingService.findByEvent(eventName));
                }
            }else{
                context.setVariable("eventBookings",eventBookingService.findByEvent(eventName));
            }
        }else{
            context.setVariable("eventBookings",eventBookingService.findAll());
        }



        springTemplateEngine.process("listBookingConfirmation.html",context,resp.getWriter());
    }
}
