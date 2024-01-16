package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.requestsAndReservations.ReservationDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.enums.ReservationStatus;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.service.interfaces.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    AccommodationService accommodationService;
    @Autowired
    AccommodationRepository accommodationRepository;

    @Override
    public ArrayList<ReservationDTO> findAll() {
        ArrayList<ReservationDTO> reservations = new ArrayList<>();
//        ReservationDTO r1 = new ReservationDTO(1L, 1L, 1L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.ACCEPTED, false, 300.0);
//        ReservationDTO r2 = new ReservationDTO(1L, 1L, 2L, null, null, 2, ReservationRequestStatus.DENIED, null, false, 300.0);
//        ReservationDTO r3 = new ReservationDTO(2L, 1L, 3L, null, null, 2, ReservationRequestStatus.WAITING, null, false, 300.0);
//        ReservationDTO r4 = new ReservationDTO(3L, 1L, 4L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.ACCEPTED, false, 300.0);
//        ReservationDTO r5 = new ReservationDTO(6L, 1L, 5L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.ACCEPTED, false, 300.0);
//        ReservationDTO r6 = new ReservationDTO(12L, 1L, 6L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.CANCELED, false, 300.0);
//        ReservationDTO r7 = new ReservationDTO(8L, 1L, 7L, null, null, 2, ReservationRequestStatus.WAITING, null, false, 300.0);
//        ReservationDTO r9 = new ReservationDTO(2L, 1L, 9L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.CANCELED, false, 300.0);
//        ReservationDTO r10 = new ReservationDTO(3L, 1L, 10L, null, null, 2, ReservationRequestStatus.ACCEPTED,ReservationStatus.ACCEPTED, false, 300.0);
//        ReservationDTO r11 = new ReservationDTO(4L, 1L, 11L, null, null, 2, ReservationRequestStatus.DENIED, null, false, 300.0);
//        reservations.add(r1);
//        reservations.add(r2);
//        reservations.add(r3);
//        reservations.add(r4);
//        reservations.add(r5);
//        reservations.add(r6);
//        reservations.add(r7);
//        reservations.add(r9);
//        reservations.add(r10);
//        reservations.add(r11);
        return reservations;
    }

    @Override
    public ArrayList<ReservationDTO> getAllForGuest(Long guestId) {
        ArrayList<ReservationDTO> all = findAll();
        ArrayList<ReservationDTO> guestReservations = new ArrayList<>();
        for (ReservationDTO r : all){
            if(Objects.equals(r.getGuestId(), guestId)){
                guestReservations.add(r);
            }
        }
        return guestReservations;
    }

    @Override
    public ArrayList<ReservationDTO> getAllForAccommodation(Long accommodationId) {
        ArrayList<ReservationDTO> all = findAll();
        ArrayList<ReservationDTO> accommodationReservations = new ArrayList<>();
        for (ReservationDTO r : all){
            if(Objects.equals(r.getAccommodationId(), accommodationId)){
                accommodationReservations.add(r);
            }
        }
        return accommodationReservations;
    }

    @Override
    public ReservationDTO getOneById(Long id) {
        ArrayList<ReservationDTO> all = findAll();
        for (ReservationDTO r : all){
            if(Objects.equals(r.getId(), id)){
                return r;
            }
        }
        return null;
    }

    public void create(ReservationRequestDTO reservationRequest) {
        Reservation reservation = new Reservation();
        reservation.setGuestId(reservationRequest.getGuestId());
        reservation.setAccommodation(accommodationRepository.findById(reservationRequest.getAccommodationId()).orElseGet(null));
        reservation.setFromDate(reservationRequest.getFromDate());
        reservation.setToDate(reservationRequest.getToDate());
        reservation.setNumberOfGuests(reservationRequest.getNumberOfGuests());
        reservation.setRequestStatus(reservationRequest.getStatus());
        reservation.setStatus(ReservationStatus.ACCEPTED);
        reservation.setDeleted(false);
        reservation.setPrice(reservationRequest.getPrice());
        reservationRepository.save(reservation);
        System.out.println(reservation);
        accommodationService.updateAvailabilitiesForAccommodation(reservation.getAccommodation().getId(),
                reservation.getFromDate(), reservation.getToDate());

    }

    @Override
    public void delete(Long id) {
        ArrayList<ReservationDTO> all = findAll();
        for (ReservationDTO r : all){
            if(Objects.equals(r.getId(), id)){
                r.setDeleted(true);
            }
        }
    }

    @Override
    public void cancel(Long guestId, Long reservationId) {

    }

}
