package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.commentsAndRatings.CreateOwnerCommentDTO;
import booker.BookingApp.dto.commentsAndRatings.OwnerCommentDTO;
import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.repository.OwnerCommentRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.repository.UserRepository;
import booker.BookingApp.service.interfaces.IOwnerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OwnerCommentService implements IOwnerCommentService {
    @Autowired
    private OwnerCommentRepository ownerCommentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public OwnerComment findOne(Long id) {
        return ownerCommentRepository.findById(id).orElse(null);
    }
    @Override
    public List<OwnerComment> findAll() {
        return ownerCommentRepository.findAll();
    }

    @Override
    public List<OwnerCommentDTO> findAllForOwner(Long ownerId) {
        List<OwnerComment> ownerComments = ownerCommentRepository.findAllForOwner(ownerId);
        List<OwnerCommentDTO> ownerCommentDTOS = new ArrayList<>();
        for (OwnerComment ownerComment : ownerComments) {
            OwnerCommentDTO ownerCommentDTO = OwnerCommentDTO.createFromOwnerComment(ownerComment);
            ownerCommentDTOS.add(ownerCommentDTO);
        }
        return ownerCommentDTOS;
    }

    @Transactional
    @Override
    public void remove(Long id) {
        OwnerComment ownerComment = ownerCommentRepository.findById(id).orElseGet(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Guest user) {
                if (ownerComment != null) {
                    ownerComment.setDeleted(true);
                    ownerCommentRepository.save(ownerComment);
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
    public OwnerCommentDTO create(CreateOwnerCommentDTO createOwnerCommentDTO) {
        OwnerComment ownerComment = new OwnerComment();
        ownerComment.setContent(createOwnerCommentDTO.getContent());
        ownerComment.setReported(false);
        ownerComment.setDate(new Date());
        ownerComment.setDeleted(false);
        ownerComment.setApproved(false);
        ownerComment.setRating(createOwnerCommentDTO.getRating());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Guest user) {
                if (reservationRepository.findAllForGuest(user.getId(), createOwnerCommentDTO.getOwnerId()).size() == 0) {
                    throw new RuntimeException("The guest has no uncancelled reservations. Commenting is not allowed.");
                }

                ownerComment.setGuest(user);
            } else {
                // Handle the case where the principal is not an instance of User
                throw new RuntimeException("Unexpected principal type: " + principal.getClass());
            }
        } else {
            // Handle the case where there is no authentication
            throw new RuntimeException("User not authenticated");
        }
        Owner owner = (Owner) userRepository.findById(createOwnerCommentDTO.getOwnerId()).orElseGet(null);
        ownerComment.setOwner(owner);

        ownerCommentRepository.save(ownerComment);
        OwnerCommentDTO ownerCommentDTO = OwnerCommentDTO.createFromOwnerComment(ownerComment);
        return ownerCommentDTO;
    }

    @Override
    public OwnerCommentDTO update(OwnerCommentDTO ownerCommentDTO) {
        return ownerCommentDTO;
    }

    @Override
    public void delete(Long id) {}

//     @Override
//     public OwnerComment save(OwnerComment ownerComment) {
//         return ownerCommentRepository.save(ownerComment);
//     }


    @Override
    public List<OwnerComment> findAllReported() {
        return ownerCommentRepository.findAllReported();
    }

    @Override
    public void report(Long id) {
        OwnerComment comment = ownerCommentRepository.findById(id).orElseGet(null);
        comment.setReported(true);
        ownerCommentRepository.save(comment);
    }

    @Override
    public List<OwnerCommentDTO> findAllNotDeletedForOwner(Long ownerId) {
        List<OwnerComment> comments = ownerCommentRepository.findAllNotDeletedForOwner(ownerId);
        List<OwnerCommentDTO> notDeleted = new ArrayList<>();
        for (OwnerComment comment : comments) {
            OwnerCommentDTO commentDTO = OwnerCommentDTO.createFromOwnerComment(comment);
            notDeleted.add(commentDTO);
        }
        return notDeleted;
    }

    @Override
    public void deleteForAdmin(Long id) {
        OwnerComment comment = ownerCommentRepository.findById(id).get();
        comment.setDeleted(true);
        ownerCommentRepository.save(comment);
    }

    @Override
    public void approveComment(Long id) {
        OwnerComment comment = ownerCommentRepository.findById(id).get();
        comment.setApproved(true);
        ownerCommentRepository.save(comment);
    }


}
