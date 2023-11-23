/*package booker.BookingApp.controller;

import booker.BookingApp.dto.accommodation.AccommodationCommentDTO;
import booker.BookingApp.model.accommodation.AccommodationComment;
import booker.BookingApp.service.implementation.AccommodationCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accommodation_comments")
public class AccommodationCommentController {
    @Autowired
    private AccommodationCommentService accommodationCommentService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationCommentDTO>> getAllComments() {
        List<AccommodationComment> comments = accommodationCommentService.findAll();

        List<AccommodationCommentDTO> commentDTOS = new ArrayList<>();
        for (AccommodationComment comment : comments) {
            commentDTOS.add(new AccommodationCommentDTO(comment));
        }
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/all/{accommodation_id}")
    public ResponseEntity<List<AccommodationCommentDTO>> getAllAccommodationComments(@PathVariable Long accommodation_id) {
        List<AccommodationComment> comments = accommodationCommentService.findAll();

        List<AccommodationCommentDTO> commentDTOS = new ArrayList<>();
        for (AccommodationComment comment : comments) {
            if (comment.getAccommodationId() == accommodation_id) {
                commentDTOS.add(new AccommodationCommentDTO(comment));
            }
        }
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationCommentDTO> getOne(@PathVariable Long id) {
        AccommodationComment accommodationComment = accommodationCommentService.findOne(id);

        if (accommodationComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new AccommodationCommentDTO(accommodationComment), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationCommentDTO> saveAccommodationComment(@RequestBody AccommodationCommentDTO accommodationCommentDTO) {
        AccommodationComment accommodationComment = new AccommodationComment();

        accommodationComment.setAccommodationId(accommodationCommentDTO.getAccommodationId());
        accommodationComment.setGuestId(accommodationCommentDTO.getGuestId());
        accommodationComment.setContent(accommodationCommentDTO.getContent());
        accommodationComment.setWhen(accommodationCommentDTO.getWhen());
        accommodationComment.setReported(accommodationCommentDTO.isReported());

        accommodationComment = accommodationCommentService.save(accommodationComment);
        return new ResponseEntity<>(new AccommodationCommentDTO(accommodationComment), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<AccommodationCommentDTO> updateAccommodationComment(@RequestBody AccommodationCommentDTO accommodationCommentDTO) {
        AccommodationComment accommodationComment = accommodationCommentService.findOne(accommodationCommentDTO.getId());

        if (accommodationComment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accommodationComment.setAccommodationId(accommodationCommentDTO.getAccommodationId());
        accommodationComment.setGuestId(accommodationCommentDTO.getGuestId());
        accommodationComment.setContent(accommodationCommentDTO.getContent());
        accommodationComment.setWhen(accommodationCommentDTO.getWhen());
        accommodationComment.setReported(accommodationCommentDTO.isReported());

        accommodationComment = accommodationCommentService.save(accommodationComment);
        return new ResponseEntity<>(new AccommodationCommentDTO(accommodationComment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationComment(@PathVariable Long id) {
        AccommodationComment accommodationComment = accommodationCommentService.findOne(id);

        if (accommodationComment != null) {
            accommodationCommentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}*/
