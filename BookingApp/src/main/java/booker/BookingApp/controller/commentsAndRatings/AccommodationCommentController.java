package booker.BookingApp.controller.commentsAndRatings;

import booker.BookingApp.dto.accommodation.AccommodationCommentDTO;
import booker.BookingApp.dto.accommodation.CreateAccommodationCommentDTO;
import booker.BookingApp.dto.accommodation.ReportAccommodationCommentDTO;
import booker.BookingApp.model.accommodation.AccommodationComment;
import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import booker.BookingApp.service.implementation.AccommodationCommentService;
import booker.BookingApp.service.implementation.AccommodationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accommodation_comments")
@CrossOrigin(origins = "http://localhost:4200")
public class AccommodationCommentController {
    @Autowired
    private AccommodationCommentService accommodationCommentService;
    @Autowired
    private AccommodationService accommodationService;


    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationCommentDTO>> getAllComments() {
        List<AccommodationComment> comments = accommodationCommentService.findAll();

        List<AccommodationCommentDTO> commentDTOS = new ArrayList<>();
        for (AccommodationComment comment : comments) {
            commentDTOS.add(new AccommodationCommentDTO(comment));
        }
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }


    @GetMapping(value = "/{accommodation_id}/comments")
    public ResponseEntity<List<AccommodationCommentDTO>> getAllAccommodationComments(@PathVariable Long accommodation_id) {
        List<AccommodationComment> comments = accommodationCommentService.findAllForAccommodation(accommodation_id);

        List<AccommodationCommentDTO> commentDTOS = new ArrayList<>();
        for (AccommodationComment comment : comments) {
            commentDTOS.add(new AccommodationCommentDTO(comment));
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


//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<AccommodationCommentDTO> saveAccommodationComment(@RequestBody AccommodationCommentDTO accommodationCommentDTO) {
//        AccommodationComment accommodationComment = new AccommodationComment();
//
//        accommodationComment.setAccommodationId(accommodationCommentDTO.getAccommodationId());
//        accommodationComment.setGuestId(accommodationCommentDTO.getGuestId());
//        accommodationComment.setContent(accommodationCommentDTO.getContent());
//        accommodationComment.setDate(accommodationCommentDTO.getDate());
//        accommodationComment.setReported(accommodationCommentDTO.isReported());
//
//        accommodationComment = accommodationCommentService.save(accommodationComment);
//        return new ResponseEntity<>(new AccommodationCommentDTO(accommodationComment), HttpStatus.CREATED);
//    }
//
//    @PutMapping(consumes = "application/json")
//    public ResponseEntity<AccommodationCommentDTO> updateAccommodationComment(@RequestBody AccommodationCommentDTO accommodationCommentDTO) {
//        AccommodationComment accommodationComment = accommodationCommentService.findOne(accommodationCommentDTO.getId());
//
//        if (accommodationComment == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        //accommodationComment.setAccommodationId(accommodationCommentDTO.getAccommodationId());
//        //accommodationComment.setGuestId(accommodationCommentDTO.getGuestId());
//        accommodationComment.setContent(accommodationCommentDTO.getContent());
//        accommodationComment.setDate(accommodationCommentDTO.getDate());
//        accommodationComment.setReported(accommodationCommentDTO.isReported());
//
//        accommodationComment = accommodationCommentService.save(accommodationComment);
//        return new ResponseEntity<>(new AccommodationCommentDTO(accommodationComment), HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> deleteAccommodationComment(@PathVariable Long id) {
//        AccommodationComment accommodationComment = accommodationCommentService.findOne(id);
//
//        if (accommodationComment != null) {
//            accommodationCommentService.remove(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping(value = "/add_comment",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationCommentDTO> create(@Valid @RequestBody CreateAccommodationCommentDTO accommodationCommentDTO) {
        AccommodationCommentDTO commentDTO = accommodationCommentService.create(accommodationCommentDTO);
        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody AccommodationCommentDTO accommodationCommentDTO) {
        accommodationCommentService.update(accommodationCommentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/remove/{accommodation_comment_id}")
    public ResponseEntity<Void> delete(@PathVariable Long accommodation_comment_id) {
        AccommodationComment accommodationComment = accommodationCommentService.findOne(accommodation_comment_id);

        if (accommodationComment != null) {
            accommodationCommentService.delete(accommodation_comment_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all/reported")
    public ResponseEntity<List<AccommodationCommentDTO>> findAllReported() {
        List<AccommodationComment> reportedComments = accommodationCommentService.findAllReported();
        List<AccommodationCommentDTO> reportedCommentsDTO = new ArrayList<>();

        for(AccommodationComment accommodationComment :reportedComments) {
            reportedCommentsDTO.add(new AccommodationCommentDTO(accommodationComment));
        }

        return new ResponseEntity<>(reportedCommentsDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/report/{comment_id}")
    public ResponseEntity<AccommodationCommentDTO> report(@PathVariable Long comment_id) {
        accommodationCommentService.report(comment_id);
        AccommodationCommentDTO accommodationCommentDTO = new AccommodationCommentDTO(accommodationCommentService.findOne(comment_id));
        return new ResponseEntity<>(accommodationCommentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/all/{accommodation_id}/not_deleted")
    public ResponseEntity<List<AccommodationCommentDTO>> getAllNotDeleted(@PathVariable Long accommodation_id) {
        List<AccommodationCommentDTO> commentDTOS = accommodationCommentService.findAllNotDeletedForAccommodation(accommodation_id);
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/delete/{comment_id}")
    public ResponseEntity<AccommodationCommentDTO> deleteForAdmin(@PathVariable Long comment_id) {
        accommodationCommentService.deleteForAdmin(comment_id);
        AccommodationCommentDTO accommodationCommentDTO = new AccommodationCommentDTO(accommodationCommentService.findOne(comment_id));
        return new ResponseEntity<>(accommodationCommentDTO, HttpStatus.OK);
    }
}
