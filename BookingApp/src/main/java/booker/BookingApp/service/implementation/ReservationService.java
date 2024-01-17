package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.requestsAndReservations.ReservationDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.enums.ReservationStatus;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.service.interfaces.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    AccommodationService accommodationService;
    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    AvailabilityService availabilityService;

    @Override
    public ArrayList<ReservationDTO> findAll() {
        ArrayList<ReservationDTO> reservations = new ArrayList<>();
        ReservationDTO r1 = new ReservationDTO(1L, 1L, 1L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.ACCEPTED, false, 300.0);
        ReservationDTO r2 = new ReservationDTO(1L, 1L, 2L, null, null, 2, ReservationRequestStatus.DENIED, null, false, 300.0);
        ReservationDTO r3 = new ReservationDTO(2L, 1L, 3L, null, null, 2, ReservationRequestStatus.WAITING, null, false, 300.0);
        ReservationDTO r4 = new ReservationDTO(3L, 1L, 4L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.ACCEPTED, false, 300.0);
        ReservationDTO r5 = new ReservationDTO(6L, 1L, 5L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.ACCEPTED, false, 300.0);
        ReservationDTO r6 = new ReservationDTO(12L, 1L, 6L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.CANCELED, false, 300.0);
        ReservationDTO r7 = new ReservationDTO(8L, 1L, 7L, null, null, 2, ReservationRequestStatus.WAITING, null, false, 300.0);
        ReservationDTO r9 = new ReservationDTO(2L, 1L, 9L, null, null, 2, ReservationRequestStatus.ACCEPTED, ReservationStatus.CANCELED, false, 300.0);
        ReservationDTO r10 = new ReservationDTO(3L, 1L, 10L, null, null, 2, ReservationRequestStatus.ACCEPTED,ReservationStatus.ACCEPTED, false, 300.0);
        ReservationDTO r11 = new ReservationDTO(4L, 1L, 11L, null, null, 2, ReservationRequestStatus.DENIED, null, false, 300.0);
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        reservations.add(r4);
        reservations.add(r5);
        reservations.add(r6);
        reservations.add(r7);
        reservations.add(r9);
        reservations.add(r10);
        reservations.add(r11);
        return reservations;
    }

    @Override
    public ArrayList<ReservationDTO> getAllForGuest(Long guestId) {
        List<Reservation> all = reservationRepository.getAllForGuest(guestId);
        ArrayList<ReservationDTO> guestReservations = new ArrayList<>();
        for (Reservation r : all){
            guestReservations.add(ReservationDTO.makeFromReservation(r));
        }
        return guestReservations;
    }

    @Override
    public ArrayList<ReservationDTO> getAllForAccommodation(Long accommodationId) {
        List<Reservation> all = reservationRepository.getAllForAccommodation(accommodationId);
        ArrayList<ReservationDTO> accommodationReservations = new ArrayList<>();
        for (Reservation r : all){
            accommodationReservations.add(ReservationDTO.makeFromReservation(r));
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
    public boolean checkDeadlineExpired(String fromDateString, Accommodation accommodation){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fromDate = sdf.parse(fromDateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromDate);
            calendar.add(Calendar.DAY_OF_MONTH, -accommodation.getDeadline());
            Date deadLineDate = calendar.getTime();

            return deadLineDate.after(new Date());
        } catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    @Override
    public boolean cancel(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        // if reservation is accepted
        if (reservation.getStatus() == ReservationStatus.ACCEPTED) {
            // if deadline for this accommodation is not expired
            if (checkDeadlineExpired(reservation.getFromDate(), reservation.getAccommodation())){
                reservation.setStatus(ReservationStatus.CANCELED);
                reservationRepository.save(reservation);
                accommodationService.returnAvailabilitiesForAccommodation(reservation.getAccommodation().getId(), reservation.getFromDate(), reservation.getToDate());
                return true;
            }
        }
        return false;
    }

}
