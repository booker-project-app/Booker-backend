package booker.BookingApp.controller.notifications;

import java.io.IOException;
import java.io.IOException;
import java.util.Map;

import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.dto.users.OwnerDTO;
import booker.BookingApp.dto.users.UserDTO;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.notifications.Notification;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.User;
import booker.BookingApp.service.implementation.GuestService;
import booker.BookingApp.service.implementation.NotificationService;
import booker.BookingApp.service.implementation.OwnerService;
import booker.BookingApp.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    GuestService guestService;

    @Autowired
    OwnerService ownerService;

    // REST enpoint
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/sendMessageRest", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody Notification message) {
        User user = this.userService.findOne(message.getUserId());
        if (user.getRole() == Role.GUEST){
            GuestDTO guest = guestService.getGuestById(user.getId());
            if (guest.isNotificationEnabled()){
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.getUserId(), message);
            }
        } else if (user.getRole() == Role.OWNER) {
            OwnerDTO owner = ownerService.getOwnerById(user.getId());
            switch (message.getType()){
                case RESERVATION_CANCELLATION:
                    if (owner.isCancellationNotificationEnabled()){
                        this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.getUserId(), message);
                    }
                    break;
                case RESERVATION_REQUEST:
                    if (owner.isRequestNotificationEnabled()){
                        this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.getUserId(), message);
                    }
                    break;
                case ACCOMMODATION_RATING:
                    if (owner.isAccNotificationEnabled()){
                        this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.getUserId(), message);
                    }
                    break;
                case OWNER_RATING:
                    if (owner.isRatingNotificationEnabled()){
                        this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.getUserId(), message);
                    }
                    break;
            }

        }
        //this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.getUserId(), message);
        notificationService.save(message);
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
    }

    /*
     * WebSockets endpoint
     *
     * Kao sto smo koristili @RequestMapping za RestController, @MessageMapping se koristi za websocket-e
     *
     * Poruka ce biti poslata svim klijentima koji su pretplatili na /socket-publisher topic,
     * a poruka koja im se salje je messageConverted (simpMessagingTemplate.convertAndSend metoda).
     *
     * Na ovaj endpoint klijenti salju poruke, ruta na koju klijenti salju poruke je /send/message (parametar @MessageMapping anotacije)
     *
     */
    @MessageMapping("/send/message")
    public Map<String, String> broadcastNotification(String message) {
        Map<String, String> messageConverted = parseMessage(message);

        if (messageConverted != null) {
            if (messageConverted.containsKey("toId") && messageConverted.get("toId") != null
                    && !messageConverted.get("toId").equals("")) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("toId"),
                        messageConverted);
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("fromId"),
                        messageConverted);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
            }
        }

        return messageConverted;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> retVal;

        try {
            retVal = mapper.readValue(message, Map.class); // parsiranje JSON stringa
        } catch (IOException e) {
            retVal = null;
        }

        return retVal;
    }
}
