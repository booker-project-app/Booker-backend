package booker.BookingApp.controller.commentsAndRatings;

import booker.BookingApp.dto.accommodation.AccommodationRatingDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationCommentDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationRatingDTO;
import booker.BookingApp.model.accommodation.AccommodationRating;
import booker.BookingApp.service.implementation.AccommodationRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accommodation_ratings")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationRatingController {
    @Autowired
    private AccommodationRatingService accommodationRatingService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationRatingDTO>> findAll() {
        List<AccommodationRating> ratings = accommodationRatingService.findAll();
        List<AccommodationRatingDTO> ratingDTOS = new ArrayList<>();

        for(AccommodationRating accommodationRating : ratings) {
            ratingDTOS.add(new AccommodationRatingDTO(accommodationRating));
        }

        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/all/{accommodation_id}/ratings")
    public ResponseEntity<List<AccommodationRatingDTO>> findAllForAccommodation(@PathVariable Long accommodation_id) {
        List<AccommodationRating> ratings = accommodationRatingService.findAllForAccommodation(accommodation_id);
        List<AccommodationRatingDTO> ratingDTOS = new ArrayList<>();
        for (AccommodationRating accommodationRating : ratings) {
            ratingDTOS.add(new AccommodationRatingDTO(accommodationRating));
        }
        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/rating")
    public ResponseEntity<AccommodationRatingDTO> findOne(@PathVariable Long id) {
        AccommodationRating accommodationRating = accommodationRatingService.findOne(id);

        if (accommodationRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccommodationRatingDTO(accommodationRating), HttpStatus.OK);
    }

//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<AccommodationRatingDTO> saveAccommodationRating(@RequestBody AccommodationRatingDTO accommodationRatingDTO) {
//        AccommodationRating accommodationRating = new AccommodationRating();
//
//        //accommodationRating.setAccommodationId(accommodationRatingDTO.getAccommodationId());
//        //accommodationRating.setGuestId(accommodationRatingDTO.getGuestId());
//        accommodationRating.setRate(accommodationRatingDTO.getRate());
//        accommodationRating.setDate(accommodationRatingDTO.getDate());
//        accommodationRating.setReported(accommodationRatingDTO.isReported());
//
//        accommodationRating = accommodationRatingService.save(accommodationRating);
//        return new ResponseEntity<>(new AccommodationRatingDTO(accommodationRating), HttpStatus.CREATED);
//    }
//
//    @PutMapping(consumes = "application/json")
//    public ResponseEntity<AccommodationRatingDTO> updateAccommodationRating(@RequestBody AccommodationRatingDTO accommodationRatingDTO) {
//        AccommodationRating accommodationRating = accommodationRatingService.findOne(accommodationRatingDTO.getId());
//
//        if (accommodationRating == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        //accommodationRating.setAccommodationId(accommodationRatingDTO.getAccommodationId());
//        //accommodationRating.setGuestId(accommodationRatingDTO.getGuestId());
//        accommodationRating.setRate(accommodationRatingDTO.getRate());
//        accommodationRating.setDate(accommodationRatingDTO.getDate());
//        accommodationRating.setReported(accommodationRatingDTO.isReported());
//
//        accommodationRating = accommodationRatingService.save(accommodationRating);
//        return new ResponseEntity<>(new AccommodationRatingDTO(accommodationRating), HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> deleteAccommodationRating(@PathVariable Long id) {
//        AccommodationRating accommodationRating = accommodationRatingService.findOne(id);
//
//        if (accommodationRating != null) {
//            accommodationRatingService.remove(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping(value = "/add_rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAccommodationRatingDTO> create(@RequestBody CreateAccommodationRatingDTO accommodationRatingDTO) {
        CreateAccommodationRatingDTO ratingDTO = accommodationRatingService.create(accommodationRatingDTO);
        return new ResponseEntity<>(ratingDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody AccommodationRatingDTO accommodationRatingDTO) {
        accommodationRatingService.update(accommodationRatingDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/remove/{accommodation_rating_id}")
    public ResponseEntity<Void> delete(@PathVariable Long accommodation_rating_id) {
        accommodationRatingService.delete(accommodation_rating_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all/reported")
    public ResponseEntity<List<AccommodationRatingDTO>> findAllReported() {
        List<AccommodationRating> ratings = accommodationRatingService.findAllReported();
        List<AccommodationRatingDTO> ratingDTOS = new ArrayList<>();
        for(AccommodationRating accommodationRating : ratings) {
            ratingDTOS.add(new AccommodationRatingDTO(accommodationRating));
        }

        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/report/{rating_id}")
    public ResponseEntity<AccommodationRatingDTO> report(@PathVariable Long rating_id) {
        accommodationRatingService.report(rating_id);
        AccommodationRatingDTO ratingDTO = new AccommodationRatingDTO(accommodationRatingService.findOne(rating_id));
        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/delete/{rating_id}")
    public ResponseEntity<AccommodationRatingDTO> deleteForAdmin(@PathVariable Long rating_id) {
        accommodationRatingService.deleteForAdmin(rating_id);
        AccommodationRatingDTO ratingDTO = new AccommodationRatingDTO(accommodationRatingService.findOne(rating_id));
        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }

}
