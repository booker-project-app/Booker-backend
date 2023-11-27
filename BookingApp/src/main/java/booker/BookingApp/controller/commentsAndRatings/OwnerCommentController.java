package booker.BookingApp.controller.commentsAndRatings;

import booker.BookingApp.dto.commentsAndRatings.OwnerCommentDTO;
import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import booker.BookingApp.service.implementation.OwnerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/owner_comments")
public class OwnerCommentController {

    @Autowired
    private OwnerCommentService ownerCommentService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<OwnerCommentDTO>> getAllOwnerComments() {
        List<OwnerComment> ownerComments = ownerCommentService.findAll();

        List<OwnerCommentDTO> ownerCommentsDTO = new ArrayList<>();
        for (OwnerComment ownerComment : ownerComments) {
            ownerCommentsDTO.add(new OwnerCommentDTO(ownerComment));
        }
        return new ResponseEntity<>(ownerCommentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerCommentDTO> getOwnerComment(@PathVariable Long id) {
        OwnerComment ownerComment = ownerCommentService.findOne(id);

        if (ownerComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new OwnerCommentDTO(ownerComment), HttpStatus.OK);
    }




    @PostMapping(consumes = "application/json")
    public ResponseEntity<OwnerCommentDTO> saveOwnerComment(@RequestBody OwnerCommentDTO ownerCommentDTO) {
        OwnerComment ownerComment = new OwnerComment();

        //ownerComment.setOwnerId(ownerCommentDTO.getOwnerId());
        //ownerComment.setGuestId(ownerCommentDTO.getGuestId());
        ownerComment.setContent(ownerCommentDTO.getContent());
        ownerComment.setDate(ownerCommentDTO.getDate());
        ownerComment.setReported(ownerCommentDTO.isReported());

        ownerComment = ownerCommentService.save(ownerComment);
        return new ResponseEntity<>(new OwnerCommentDTO(ownerComment), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<OwnerCommentDTO> updateOwnerComment(@RequestBody OwnerCommentDTO ownerCommentDTO) {
        OwnerComment ownerComment = ownerCommentService.findOne(ownerCommentDTO.getId());

        if (ownerComment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //ownerComment.setOwnerId(ownerCommentDTO.getOwnerId());
        //ownerComment.setGuestId(ownerCommentDTO.getGuestId());
        ownerComment.setContent(ownerCommentDTO.getContent());
        ownerComment.setDate(ownerCommentDTO.getDate());
        ownerComment.setReported(ownerComment.isReported());

        ownerComment = ownerCommentService.save(ownerComment);
        return new ResponseEntity<>(new OwnerCommentDTO(ownerComment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwnerComment(@PathVariable Long id) {
        OwnerComment ownerComment = ownerCommentService.findOne(id);

        if (ownerComment != null) {
            ownerCommentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all/{owner_id}/comments")
    public ResponseEntity<List<OwnerCommentDTO>> getAllForOwner(@PathVariable Long owner_id) {
        List<OwnerComment> comments = ownerCommentService.findAllForOwner(owner_id);
        List<OwnerCommentDTO> commentDTOS = new ArrayList<>();

        for(OwnerComment ownerComment : comments) {
            commentDTOS.add(new OwnerCommentDTO(ownerComment));
        }

        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }
}
