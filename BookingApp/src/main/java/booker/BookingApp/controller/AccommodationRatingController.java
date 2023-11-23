/*package booker.BookingApp.controller;

import booker.BookingApp.dto.accommodation.AccommodationRatingDTO;
import booker.BookingApp.model.accommodation.AccommodationRating;
import booker.BookingApp.service.implementation.AccommodationRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accommodation_ratings")
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

    @GetMapping(value = "/all/{accommodation_id}")
    public ResponseEntity<List<AccommodationRatingDTO>> findAllForAccommodation(@PathVariable Long accommodation_id) {
        List<AccommodationRating> ratings = accommodationRatingService.findAll();
        List<AccommodationRatingDTO> ratingDTOS = new ArrayList<>();

        for (AccommodationRating accommodationRating : ratings) {
            if (accommodationRating.getAccommodationId()  == accommodation_id) {
                ratingDTOS.add(new AccommodationRatingDTO(accommodationRating));
            }
        }
        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationRatingDTO> findOne(@PathVariable Long id) {
        AccommodationRating accommodationRating = accommodationRatingService.findOne(id);

        if (accommodationRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccommodationRatingDTO(accommodationRating), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationRatingDTO> saveAccommodationRating(@RequestBody AccommodationRatingDTO accommodationRatingDTO) {
        AccommodationRating accommodationRating = new AccommodationRating();

        accommodationRating.setAccommodationId(accommodationRatingDTO.getAccommodationId());
        accommodationRating.setGuestId(accommodationRatingDTO.getGuestId());
        accommodationRating.setRate(accommodationRatingDTO.getRate());
        accommodationRating.setWhen(accommodationRatingDTO.getWhen());
        accommodationRating.setReported(accommodationRatingDTO.isReported());

        accommodationRating = accommodationRatingService.save(accommodationRating);
        return new ResponseEntity<>(new AccommodationRatingDTO(accommodationRating), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationRatingDTO> updateAccommodationRating(@RequestBody AccommodationRatingDTO accommodationRatingDTO) {
        AccommodationRating accommodationRating = accommodationRatingService.findOne(accommodationRatingDTO.getId());

        if (accommodationRating == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accommodationRating.setAccommodationId(accommodationRatingDTO.getAccommodationId());
        accommodationRating.setGuestId(accommodationRatingDTO.getGuestId());
        accommodationRating.setRate(accommodationRatingDTO.getRate());
        accommodationRating.setWhen(accommodationRatingDTO.getWhen());
        accommodationRating.setReported(accommodationRatingDTO.isReported());

        accommodationRating = accommodationRatingService.save(accommodationRating);
        return new ResponseEntity<>(new AccommodationRatingDTO(accommodationRating), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationRating(@PathVariable Long id) {
        AccommodationRating accommodationRating = accommodationRatingService.findOne(id);

        if (accommodationRating != null) {
            accommodationRatingService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}*/
