package booker.BookingApp.controller.requestsAndReservations;

import booker.BookingApp.dto.requestsAndReservations.ReservationDTO;
import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.service.implementation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<ArrayList<ReservationDTO>> getAll() {
        ArrayList<ReservationDTO> reservations = reservationService.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservationDTO = reservationService.getOneById(id);
        if(reservationDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/guest/{guestId}")
    public ResponseEntity<ArrayList<ReservationDTO>> getAllForGuest(@PathVariable Long guestId) {
        ArrayList<ReservationDTO> reservations = reservationService.getAllForGuest(guestId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}")
    public ResponseEntity<ArrayList<ReservationDTO>> getAllForAccommodation(@PathVariable Long accommodationId) {
        ArrayList<ReservationDTO> reservations = reservationService.getAllForAccommodation(accommodationId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReservationDTO> insert(@RequestBody ReservationDTO reservation) {
        ReservationDTO reservationDTO = reservationService.create(reservation);
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ReservationDTO reservationDTO = reservationService.getOneById(id);
        if (reservationDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            reservationService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping(value = "/guest/{guestId}/cancel/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long guestId, @PathVariable Long id){
        reservationService.cancel(guestId, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/owner/{ownerId}/accept/{id}/{accept}")
    public ResponseEntity<Void> declineAccommodation(@PathVariable Long ownerId,
                                                     @PathVariable Long id,
                                                     @PathVariable boolean accept) {
        reservationService.acceptOrDecline(ownerId, id, accept);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
