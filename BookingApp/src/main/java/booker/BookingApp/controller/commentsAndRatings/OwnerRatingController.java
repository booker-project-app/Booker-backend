package booker.BookingApp.controller.commentsAndRatings;

import booker.BookingApp.dto.commentsAndRatings.CreateOwnerRatingDTO;
import booker.BookingApp.dto.commentsAndRatings.OwnerCommentDTO;
import booker.BookingApp.dto.commentsAndRatings.OwnerRatingDTO;
import booker.BookingApp.model.commentsAndRatings.OwnerRating;
import booker.BookingApp.service.implementation.OwnerRatingService;
import booker.BookingApp.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/owner_ratings")
@CrossOrigin(origins = "http://localhost:4200")
public class OwnerRatingController {
    @Autowired
    private OwnerRatingService ownerRatingService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<OwnerRatingDTO>> getAllOwnerRatings() {
        List<OwnerRating> ownerRatings = ownerRatingService.findAll();
        List<OwnerRatingDTO> ratingDTOS = new ArrayList<>();

        for (OwnerRating rating : ownerRatings) {
            ratingDTOS.add(new OwnerRatingDTO(rating));
        }
        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }



    @GetMapping(value = "/{id}/rating")
    public ResponseEntity<OwnerRatingDTO> getRating(@PathVariable Long id) {
        OwnerRating ownerRating = ownerRatingService.findOne(id);

        if (ownerRating == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new OwnerRatingDTO(ownerRating), HttpStatus.OK);
        }
    }




//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<CreateOwnerRatingDTO> saveOwnerRating(@RequestBody CreateOwnerRatingDTO ratingDTO) {
//        OwnerRating ownerRating = new OwnerRating();
//
//        ownerRating.setOwner(userService.findOne(ratingDTO.getOwnerId()));
//        ownerRating.setGuest(userService.findOne(ratingDTO.getGuestId()));
//        ownerRating.setRate(ratingDTO.getRate());
//        ownerRating.setDate(ratingDTO.getDate());
//
//        ownerRating = ownerRatingService.save(ownerRating);
//        return new ResponseEntity<>(new CreateOwnerRatingDTO(ownerRating), HttpStatus.CREATED);
//    }
//
//    @PutMapping(consumes = "application/json")
//    public ResponseEntity<OwnerRatingDTO> updateOwnerRating(@RequestBody OwnerRatingDTO ownerRatingDTO) {
//        OwnerRating ownerRating = ownerRatingService.findOne(ownerRatingDTO.getId());
//
//        if (ownerRating == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        //ownerRating.setOwnerId(ownerRatingDTO.getOwnerId());
//        //ownerRating.setGuestId(ownerRatingDTO.getGuestId());
//        ownerRating.setRate(ownerRatingDTO.getRate());
//        ownerRating.setDate(ownerRatingDTO.getDate());
//        ownerRating.setReported(ownerRatingDTO.isReported());
//
//        ownerRating = ownerRatingService.save(ownerRating);
//        return new ResponseEntity<>(new OwnerRatingDTO(ownerRating), HttpStatus.OK);
//    }

    @PutMapping(value = "/remove/{id}")
    public ResponseEntity<Void> deleteOwnerRating(@PathVariable Long id) {
        OwnerRating ownerRating = ownerRatingService.findOne(id);

        if (ownerRating != null) {
            ownerRatingService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/add_rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerRatingDTO> create(@RequestBody CreateOwnerRatingDTO createOwnerRatingDTO) {
         OwnerRatingDTO ownerRatingDTO =  ownerRatingService.create(createOwnerRatingDTO);
        return new ResponseEntity<>(ownerRatingDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody OwnerRatingDTO ownerRatingDTO) {
        ownerRatingService.update(ownerRatingDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{owner_id}/ratings")
    public ResponseEntity<List<OwnerRatingDTO>> findAllForOwner(@PathVariable Long owner_id) {
        List<OwnerRating> ratings = ownerRatingService.getAllForOwner(owner_id);
        List<OwnerRatingDTO> ratingDTOS = new ArrayList<>();
        for(OwnerRating rating : ratings) {
            ratingDTOS.add(new OwnerRatingDTO(rating));
        }
        return new ResponseEntity<>(ratingDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/all/reported")
    public ResponseEntity<List<OwnerRatingDTO>> findAllReported() {
        List<OwnerRating> reported = ownerRatingService.findAllReported();
        List<OwnerRatingDTO> reportedDTOS = new ArrayList<>();

        for (OwnerRating rating : reported) {
            reportedDTOS.add(new OwnerRatingDTO(rating));
        }
        return new ResponseEntity<>(reportedDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/report/{rating_id}")
    public ResponseEntity<OwnerRatingDTO> report(@PathVariable Long rating_id) {
        ownerRatingService.report(rating_id);
        OwnerRatingDTO ratingDTO = new OwnerRatingDTO(ownerRatingService.findOne(rating_id));
        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/delete/{rating_id}")
    public ResponseEntity<OwnerRatingDTO> delete(@PathVariable Long rating_id) {
        ownerRatingService.deleteForAdmin(rating_id);
        OwnerRatingDTO ratingDTO = new OwnerRatingDTO(ownerRatingService.findOne(rating_id));
        return new ResponseEntity<>(ratingDTO, HttpStatus.OK);
    }
}
