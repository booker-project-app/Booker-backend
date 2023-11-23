/*package booker.BookingApp.controller;

import booker.BookingApp.dto.OwnerRatingDTO;
import booker.BookingApp.model.OwnerRating;
import booker.BookingApp.service.implementation.OwnerRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/owner_ratings")
public class OwnerRatingController {
    @Autowired
    private OwnerRatingService ownerRatingService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<OwnerRatingDTO>> getAllOwnerRatings() {
        List<OwnerRating> ownerRatings = ownerRatingService.findAll();
        List<OwnerRatingDTO> ratingDTOS = new ArrayList<>();

        for (OwnerRating rating : ownerRatings) {
            ratingDTOS.add(new OwnerRatingDTO(rating));
        }
        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/all/{owner_id}")
    public ResponseEntity<List<OwnerRatingDTO>> getAllRatingsForOwner(@PathVariable Long owner_id) {
        List<OwnerRating> ownerRatings = ownerRatingService.findAll();
        List<OwnerRatingDTO> ratingDTOS = new ArrayList<>();

        for (OwnerRating ownerRating : ownerRatings) {
            if (ownerRating.getOwnerId() == owner_id) {
                ratingDTOS.add(new OwnerRatingDTO(ownerRating));
            }
        }
        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerRatingDTO> getRating(@PathVariable Long id) {
        OwnerRating ownerRating = ownerRatingService.findOne(id);

        if (ownerRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new OwnerRatingDTO(ownerRating), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{owner_id}")
    public ResponseEntity<OwnerRatingDTO> getOwnerRatingByOwnerId(@PathVariable Long owner_id) {
        OwnerRating ownerRating = ownerRatingService.findOneByOwnerId(owner_id);

        if (ownerRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new OwnerRatingDTO(ownerRating), HttpStatus.OK);
    }

    @GetMapping(value = "/{guest_id}")
    public ResponseEntity<OwnerRatingDTO> getOwnerRatingByGuestId(@PathVariable Long guest_id) {
        OwnerRating ownerRating = ownerRatingService.findOneByGuestRating(guest_id);

        if (ownerRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new OwnerRatingDTO(ownerRating), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<OwnerRatingDTO> saveOwnerRating(@RequestBody OwnerRatingDTO ownerRatingDTO) {
        OwnerRating ownerRating = new OwnerRating();

        ownerRating.setOwnerId(ownerRatingDTO.getOwnerId());
        ownerRating.setGuestId(ownerRatingDTO.getGuestId());
        ownerRating.setRate(ownerRatingDTO.getRate());
        ownerRating.setWhen(ownerRatingDTO.getWhen());
        ownerRating.setReported(ownerRatingDTO.isReported());

        ownerRating = ownerRatingService.save(ownerRating);
        return new ResponseEntity<>(new OwnerRatingDTO(ownerRating), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<OwnerRatingDTO> updateOwnerRating(@RequestBody OwnerRatingDTO ownerRatingDTO) {
        OwnerRating ownerRating = ownerRatingService.findOne(ownerRatingDTO.getId());

        if (ownerRating == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ownerRating.setOwnerId(ownerRatingDTO.getOwnerId());
        ownerRating.setGuestId(ownerRatingDTO.getGuestId());
        ownerRating.setRate(ownerRatingDTO.getRate());
        ownerRating.setWhen(ownerRatingDTO.getWhen());
        ownerRating.setReported(ownerRatingDTO.isReported());

        ownerRating = ownerRatingService.save(ownerRating);
        return new ResponseEntity<>(new OwnerRatingDTO(ownerRating), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwnerRating(@PathVariable Long id) {
        OwnerRating ownerRating = ownerRatingService.findOne(id);

        if (ownerRating != null) {
            ownerRatingService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}*/
