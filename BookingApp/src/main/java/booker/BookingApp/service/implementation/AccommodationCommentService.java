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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                if (reservationRepository.findAllForGuestInAccommodation(user.getId(), createAccommodationCommentDTO.getAccommodationId()).size() == 0) {
                    throw new RuntimeException("The guest has no uncancelled reservations. Commenting is not allowed.");
                }

                List<Reservation> reservations = reservationRepository.findAllForGuestInAccommodation(user.getId(), createAccommodationCommentDTO.getAccommodationId());
                if(hasPassedFiveMinutesFromEnd(reservations.get(reservations.size() - 1).getToDate())) {
                    throw new RuntimeException("5 minutes passed");
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

    private boolean hasPassedFiveMinutesFromEnd(String endDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime fiveMinutesAfterEnd = endDate.plusMinutes(5);

        System.out.println("Current Date Time: " + currentDateTime);
        System.out.println("End Date: " + endDate);
        System.out.println("Five Minutes After End: " + fiveMinutesAfterEnd);

        boolean result = currentDateTime.isAfter(fiveMinutesAfterEnd);
        System.out.println("Result: " + result);

        return result;
    }

    @Override
    public void deleteForAdmin(Long id) {
        AccommodationComment comment = accommodationCommentRepository.findById(id).orElseGet(null);
        comment.setDeleted(true);
        accommodationCommentRepository.save(comment);
    }
}
