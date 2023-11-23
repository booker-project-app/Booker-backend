/*package booker.BookingApp.service.implementation;

import booker.BookingApp.model.OwnerRating;
import booker.BookingApp.repository.OwnerRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerRatingService {
    @Autowired
    private OwnerRatingRepository ownerRatingRepository;

    public OwnerRating findOne(Long id) {
        if (ownerRatingRepository.findOne(id) == null) {
            return null;
        }
        return ownerRatingRepository.findOne(id);
    }

    public OwnerRating findOneByOwnerId(Long ownerId) {
        if (ownerRatingRepository.findOneByOwnerId(ownerId) == null) {
            return null;
        }
        return ownerRatingRepository.findOneByOwnerId(ownerId);
    }

    public OwnerRating findOneByGuestRating(Long guestId) {
        if (ownerRatingRepository.findOneByGuestId(guestId) == null) {
            return null;
        }
        return ownerRatingRepository.findOneByGuestId(guestId);
    }

    public List<OwnerRating> findAll() {
        return ownerRatingRepository.findAll();
    }

    public List<OwnerRating> findAllForOwner(Long ownerId) {
        List<OwnerRating> ratings = ownerRatingRepository.findAll();
        List<OwnerRating> ownerRatings = new ArrayList<>();

        for(OwnerRating ownerRating : ratings) {
            if (ownerRating.getOwnerId() == ownerId) {
                ownerRatings.add(ownerRating);
            }
        }
        return ownerRatings;
    }

    public List<OwnerRating> findAllReported() {
        List<OwnerRating> ratings = ownerRatingRepository.findAll();
        List<OwnerRating> reported = new ArrayList<>();

        for (OwnerRating ownerRating : ratings) {
            if (ownerRating.isReported()) {
                reported.add(ownerRating);
            }
        }
        return reported;
    }

    public OwnerRating create(OwnerRating ownerRating) {
        return ownerRatingRepository.create(ownerRating);
    }

    public OwnerRating update(OwnerRating ownerRating) {
        return ownerRatingRepository.update(ownerRating);
    }

    public void delete(Long id) {
        ownerRatingRepository.deleteById(id);
    }

    public OwnerRating save(OwnerRating ownerRating) {
        return ownerRatingRepository.save(ownerRating);
    }
}*/
