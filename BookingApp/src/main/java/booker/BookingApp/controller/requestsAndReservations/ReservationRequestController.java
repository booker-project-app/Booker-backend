package booker.BookingApp.controller.requestsAndReservations;

import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.model.accommodation.Filter;
import booker.BookingApp.service.interfaces.IReservationRequestService;
import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationRequestController {
    @Autowired
    IReservationRequestService service;

    //create a request
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationRequestDTO> insert(@Valid @RequestBody ReservationRequestDTO request) throws Exception {
        ReservationRequestDTO requestDTO = service.create(request);
        if(requestDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(requestDTO, HttpStatus.CREATED);
    }

    //find all requests for owner
    @GetMapping(value = "/owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> findOwnersRequests(@PathVariable Long ownerId)
    {
        ArrayList<ReservationRequestDTO> requests = service.findOwnersRequests(ownerId);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //find all requests for owner, filter by status
    @PostMapping(value = "/owner/{ownerId}/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> findOwnersRequestsByStatus(@PathVariable Long ownerId,
                                                                                       @RequestBody ArrayList<Filter> filters)
    {
        ArrayList<ReservationRequestDTO> requests = service.findOwnersRequests(ownerId);
        ArrayList<ReservationRequestStatus> adequateTypes = new ArrayList<>();
        for(Filter filter : filters) {
            if(filter.getName().equals("accepted")) {
                adequateTypes.add(ReservationRequestStatus.ACCEPTED);
            } else if(filter.getName().equals("denied")) {
                adequateTypes.add(ReservationRequestStatus.DENIED);
            } else if(filter.getName().equals("waiting")) {
                adequateTypes.add(ReservationRequestStatus.WAITING);
            }
        }
        requests = service.applyFilters(requests, adequateTypes);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //   find all requests for owner, search
    //   /api/requests/owner/5/search/12.12.2023./Modern
    @GetMapping(value = "/owner/{ownerId}/search/{date}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> searchOwnersRequests(@PathVariable Long ownerId,
                                                                                 @PathVariable String date,
                                                                                 @PathVariable String name) throws IOException {
        ArrayList<ReservationRequestDTO> requests = service.searchForOwner(ownerId, date, name);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //   searching accommodations with filters, path looks like
    //   /api/requests/owner/5/search/12.12.2023/Modern/filter
    //   and request body contains json with filter array
    @PostMapping(value = "/owner/{ownerId}/search/{date}/{name}/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> searchAndFilterOwnersRequests(@PathVariable Long ownerId,
                                                                                          @PathVariable String date,
                                                                                          @PathVariable String name,
                                                                                          @RequestBody ArrayList<Filter> filters) throws IOException {
        ArrayList<ReservationRequestDTO> requests = service.searchForOwner(ownerId, date, name);
        ArrayList<ReservationRequestStatus> adequateTypes = new ArrayList<>();
        for(Filter filter : filters) {
            if(filter.getName().equals("accepted")) {
                adequateTypes.add(ReservationRequestStatus.ACCEPTED);
            } else if(filter.getName().equals("denied")) {
                adequateTypes.add(ReservationRequestStatus.DENIED);
            } else if(filter.getName().equals("waiting")) {
                adequateTypes.add(ReservationRequestStatus.WAITING);
            }
        }
        requests = service.applyFilters(requests, adequateTypes);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //find all requests for guest
    @GetMapping(value = "/guest/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> findGuestsRequests(@PathVariable Long guestId)
    {
        ArrayList<ReservationRequestDTO> requests = service.findGuestsRequests(guestId);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //find all requests for guest, filter by status
    @PostMapping(value = "/guest/{guestId}/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> findGuestsRequestsByStatus(@PathVariable Long guestId,
                                                                                       @RequestBody ArrayList<Filter> filters)
    {
        ArrayList<ReservationRequestDTO> requests = service.findGuestsRequests(guestId);
        ArrayList<ReservationRequestStatus> adequateTypes = new ArrayList<>();
        for(Filter filter : filters) {
            if(filter.getName().equals("accepted")) {
                adequateTypes.add(ReservationRequestStatus.ACCEPTED);
            } else if(filter.getName().equals("denied")) {
                adequateTypes.add(ReservationRequestStatus.DENIED);
            } else if(filter.getName().equals("waiting")) {
                adequateTypes.add(ReservationRequestStatus.WAITING);
            }
        }
        requests = service.applyFilters(requests, adequateTypes);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //   find all requests for guest, search
    //   /api/requests/guest/5/search?date=12.12.2023.&name=Modern
    @GetMapping(value = "/guest/{guestId}/search/{date}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> searchGuestsRequests(@PathVariable Long guestId,
                                                                                 @PathVariable String date,
                                                                                 @PathVariable String name) throws IOException {
        ArrayList<ReservationRequestDTO> requests = service.search(guestId, date, name);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //   searching accommodations with filters, path looks like
    //   /api/requests/guest/5/search?date=12.12.2023.&name=Modern?status=WAITING
    //   and request body contains json with filter array
    @PostMapping(value = "/guest/{guestId}/search/{date}/{name}/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<ReservationRequestDTO>> searchAndFilterGuestsRequests(@PathVariable Long guestId,
                                                                                          @PathVariable String date,
                                                                                          @PathVariable String name,
                                                                                          @RequestBody ArrayList<Filter> filters) throws IOException {
        ArrayList<ReservationRequestDTO> requests = service.search(guestId, date, name);
        ArrayList<ReservationRequestStatus> adequateTypes = new ArrayList<>();
        for(Filter filter : filters) {
            if(filter.getName().equals("accepted")) {
                adequateTypes.add(ReservationRequestStatus.ACCEPTED);
            } else if(filter.getName().equals("denied")) {
                adequateTypes.add(ReservationRequestStatus.DENIED);
            } else if(filter.getName().equals("waiting")) {
                adequateTypes.add(ReservationRequestStatus.WAITING);
            }
        }
        requests = service.applyFilters(requests, adequateTypes);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    //cancel request for guest
    @DeleteMapping(value = "/guest/{guestId}/cancel-request/{requestId}")
    public ResponseEntity<Void> cancelRequest(@PathVariable Long guestId, @PathVariable Long requestId) {
        service.cancelRequest(guestId, requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //accept or decline request for owner
    @PutMapping(value = "/owner/accept_reservation/{accept}", consumes = "application/json")
    public ResponseEntity<String> acceptOrDeclineReservationRequest(@PathVariable("accept") boolean accept,
                                                                  @RequestBody ReservationRequestDTO reservationRequestDTO) {
        boolean finished = service.acceptOrDecline(accept, reservationRequestDTO);
        if (finished){
            if(accept){
                return new ResponseEntity<>("Reservation request is approved!\nReservation created!", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Reservation request is denied!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Error with accommodation availability for this request!", HttpStatus.OK);
    }
}
