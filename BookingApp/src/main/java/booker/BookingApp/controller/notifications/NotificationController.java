package booker.BookingApp.controller.notifications;

import booker.BookingApp.dto.notifications.NotificationDTO;
import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.dto.users.OwnerDTO;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.users.User;
import booker.BookingApp.service.implementation.GuestService;
import booker.BookingApp.service.implementation.NotificationService;
import booker.BookingApp.service.implementation.OwnerService;
import booker.BookingApp.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    GuestService guestService;

    @Autowired
    OwnerService ownerService;


    @GetMapping(value = "/{userId}")
    public ResponseEntity<ArrayList<NotificationDTO>> findAllPersonal(@PathVariable Long userId){
        ArrayList<NotificationDTO> personalNotifications = notificationService.findAllForUser(userId);
        return new ResponseEntity<>(personalNotifications, HttpStatus.OK);
    }

    @GetMapping(value = "/settings/{userId}/{id}/{checked}")
    public ResponseEntity<Void> changeSettings(@PathVariable("userId") Long userId,
                                               @PathVariable("id") Long id,
                                               @PathVariable("checked") boolean checked){
        User user = this.userService.findOne(userId);
        if (user.getRole() == Role.GUEST) {
            GuestDTO guest = guestService.getGuestById(user.getId());
            guestService.updateSettings(guest, checked);
        } else if (user.getRole() == Role.OWNER) {
            OwnerDTO owner = ownerService.getOwnerById(user.getId());
            ownerService.updateSettings(owner, id, checked);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
