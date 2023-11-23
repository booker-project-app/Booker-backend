/*package booker.BookingApp.service.implementation;

import booker.BookingApp.model.accommodation.AccommodationComment;
import booker.BookingApp.repository.AccommodationCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationCommentService {
    @Autowired
    private AccommodationCommentRepository accommodationCommentRepository;

    public AccommodationComment findOne(Long id) {
        if (accommodationCommentRepository.findOne(id) == null) {
            return null;
        }
        return accommodationCommentRepository.findOne(id);
    }

    public AccommodationComment findOneByAccommodationId(Long accommodationId) {
        if (accommodationCommentRepository.findByAccommodationId(accommodationId) == null) {
            return null;
        }
        return accommodationCommentRepository.findByAccommodationId(accommodationId);
    }

    public List<AccommodationComment> findAll() {
        return accommodationCommentRepository.findAll();
    }

    public List<AccommodationComment> findAllForAccommodation(Long accommodationId) {
        return accommodationCommentRepository.findAllForAccommodation(accommodationId);
    }

    public AccommodationComment create(AccommodationComment accommodationComment) {
        return accommodationCommentRepository.create(accommodationComment);
    }

    public AccommodationComment update(AccommodationComment accommodationComment) {
        return accommodationCommentRepository.update(accommodationComment);
    }
    public void remove(Long id) {
        accommodationCommentRepository.deleteById(id);
    }

    public AccommodationComment save(AccommodationComment accommodationComment) {
        return accommodationCommentRepository.save(accommodationComment);
    }
}*/
