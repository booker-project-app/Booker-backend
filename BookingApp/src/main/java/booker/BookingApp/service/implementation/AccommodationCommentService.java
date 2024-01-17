package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.AccommodationCommentDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationCommentDTO;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.AccommodationComment;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.User;
import booker.BookingApp.repository.AccommodationCommentRepository;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.service.interfaces.IAccommodationCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Service
public class AccommodationCommentService implements IAccommodationCommentService {
    @Autowired
    private AccommodationCommentRepository accommodationCommentRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Override
    public AccommodationComment findOne(Long id) {
        return accommodationCommentRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccommodationComment> findAll() {
        return accommodationCommentRepository.findAll();
    }

    @Override
    public List<AccommodationComment> findAllForAccommodation(Long accommodationId) {
        return accommodationCommentRepository.findAllForAccommodation(accommodationId);
    }

    @Override
    public void remove(Long id) {
        accommodationCommentRepository.deleteById(id);
    }

//    public AccommodationComment save(AccommodationComment accommodationComment) {
//        return accommodationCommentRepository.save(accommodationComment);
//    }

    @Override
    public AccommodationCommentDTO create(CreateAccommodationCommentDTO createAccommodationCommentDTO) {
        AccommodationComment accommodationComment = new AccommodationComment();
        accommodationComment.setContent(createAccommodationCommentDTO.getContent());
        accommodationComment.setReported(false);
        accommodationComment.setDate(new Date());
        accommodationComment.setDeleted(false);
        accommodationComment.setApproved(false);
        accommodationComment.setRating(createAccommodationCommentDTO.getRating());




        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                User user = (User) principal;
                List<Reservation> reservations = reservationRepository.findAllForGuestInAccommodation(user.getId(), createAccommodationCommentDTO.getAccommodationId());
                for (Reservation r : reservations) {
                    System.out.println(r.toString());
                }
                if (reservationRepository.findAllForGuestInAccommodation(user.getId(), createAccommodationCommentDTO.getAccommodationId()).size() != 0) {
                    Reservation lastReservation = reservationRepository.findLastPastReservationForGuestInAccommodation(user.getId(), createAccommodationCommentDTO.getAccommodationId());
                    System.out.println(lastReservation.toString());
                    if(hasPassedFiveMinutesFromEnd(lastReservation.getToTime())) {
                        throw new RuntimeException("5 minutes passed");
                    }
                } else {
                    throw new RuntimeException("The guest has no uncancelled reservations. Commenting is not allowed.");
                }





                accommodationComment.setUser(user);
            } else {
                // Handle the case where the principal is not an instance of User
                throw new RuntimeException("Unexpected principal type: " + principal.getClass());
            }
        } else {
            // Handle the case where there is no authentication
            throw new RuntimeException("User not authenticated");
        }

        Accommodation accommodation = accommodationRepository.findById(createAccommodationCommentDTO.getAccommodationId()).orElseGet(null);
        accommodationComment.setAccommodation(accommodation);

        accommodationCommentRepository.save(accommodationComment);
        AccommodationCommentDTO accommodationCommentDTO = AccommodationCommentDTO.createFromAccommodationComment(accommodationComment);
        return accommodationCommentDTO;
    }

    @Override
    public AccommodationCommentDTO update(AccommodationCommentDTO accommodationCommentDTO) {
        return accommodationCommentDTO;
    }
    @Transactional
    @Override
    public void delete(Long id) {
        AccommodationComment accommodationComment = accommodationCommentRepository.findById(id).orElseGet(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                User user = (User) principal;
                if (accommodationComment != null) {
                    accommodationComment.setDeleted(true);
                    accommodationCommentRepository.save(accommodationComment);
                }
            } else {
                // Handle the case where the principal is not an instance of User
                throw new RuntimeException("Unexpected principal type: " + principal.getClass());
            }
        } else {
            // Handle the case where there is no authentication
            throw new RuntimeException("User not authenticated");
        }
    }

    @Override
    public void report(Long id) {
        AccommodationComment comment = accommodationCommentRepository.findById(id).orElseGet(null);
        comment.setReported(true);
        accommodationCommentRepository.save(comment);
    }


    @Override
    public AccommodationComment save(AccommodationComment accommodationComment) {
        return accommodationCommentRepository.save(accommodationComment);
    }

    @Override
    public List<AccommodationComment> findAllReported() {
        return accommodationCommentRepository.findAllReported();
    }

    @Override
    public List<AccommodationCommentDTO> findAllNotDeletedForAccommodation(Long accommodationId) {
        List<AccommodationComment> accommodationComments = accommodationCommentRepository.findAllNotDeletedForAccommodation(accommodationId);
        List<AccommodationCommentDTO> notDeleted = new ArrayList<>();
        for (AccommodationComment accommodationComment : accommodationComments) {
            AccommodationCommentDTO accommodationCommentDTO = AccommodationCommentDTO.createFromAccommodationComment(accommodationComment);
            notDeleted.add(accommodationCommentDTO);
        }
        return notDeleted;
    }

    private boolean hasPassedFiveMinutesFromEnd(String endTimeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date endTime = new Date();
        try {
            endTime = sdf.parse(endTimeString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        Date currentTime = new Date();
        System.out.println("Current time: " + sdf.format(currentTime));


        long timeDifference = currentTime.getTime() - endTime.getTime();

        // Convert milliseconds to minutes
        long minutesDifference = timeDifference / (60 * 1000);

        // Check if 5 minutes have passed
        return minutesDifference >= 5;

    }

    @Override
    public void deleteForAdmin(Long id) {
        AccommodationComment comment = accommodationCommentRepository.findById(id).orElseGet(null);
        comment.setDeleted(true);
        accommodationCommentRepository.save(comment);
    }
}
