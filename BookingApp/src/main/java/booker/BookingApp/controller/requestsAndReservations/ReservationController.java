package booker.BookingApp.controller.requestsAndReservations;

import booker.BookingApp.dto.requestsAndReservations.ReservationDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.dto.users.GuestDTO;
import booker.BookingApp.service.implementation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    @Autowired
    ReservationService reservationService;


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

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<ReservationDTO> insert(@RequestBody ReservationRequestDTO reservationRequest) {
        reservationService.create(reservationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PutMapping(value = "/guest/cancel/{id}", consumes = "application/json")
    public ResponseEntity<Boolean> cancel(@PathVariable("id") Long id){
        boolean cancelled = reservationService.cancel(id);
        System.out.println("rezultat otkazivanja: " + cancelled);
        return new ResponseEntity<>(cancelled, HttpStatus.OK);
    }
}
