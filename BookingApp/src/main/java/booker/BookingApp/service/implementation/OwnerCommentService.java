/*package booker.BookingApp.service.implementation;

import booker.BookingApp.model.OwnerComment;
import booker.BookingApp.repository.OwnerCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerCommentService {
    @Autowired
    private OwnerCommentRepository ownerCommentRepository;

    public OwnerComment findOne(Long id) {
        if(ownerCommentRepository.findOne(id) == null) {
            return null;
        }
        return ownerCommentRepository.findOne(id);
    }

    public OwnerComment findOneByOwnerId(Long ownerId) {
        if(ownerCommentRepository.findOneByOwnerId(ownerId) == null) {
            return null;
        }
        return ownerCommentRepository.findOneByOwnerId(ownerId);
    }

    public OwnerComment findOneByGuestId(Long guestId) {
        if (ownerCommentRepository.findOneByGuestId(guestId) == null) {
            return null;
        }
        return ownerCommentRepository.findOneByGuestId(guestId);
    }

    public List<OwnerComment> findAll() {
        return ownerCommentRepository.getAll();
    }

    public List<OwnerComment> findAllForOwner(Long ownerId) {
        List<OwnerComment> ownerComments = new ArrayList<>();

        for (OwnerComment comment : ownerCommentRepository.getAll()) {
            if (comment.getOwnerId() == ownerId) {
                ownerComments.add(comment);
            }
        }
        return ownerComments;
    }

    public OwnerComment create(OwnerComment ownerComment) {
        return ownerCommentRepository.create(ownerComment);
    }

    public OwnerComment update(OwnerComment ownerComment) {
        return ownerCommentRepository.update(ownerComment);
    }

    public void remove(Long id) {
        ownerCommentRepository.deleteById(id);
    }

    public OwnerComment save(OwnerComment ownerComment) {
        return ownerCommentRepository.save(ownerComment);
    }
}*/
