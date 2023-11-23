/*package booker.BookingApp.controller;

import booker.BookingApp.dto.OwnerCommentDTO;
import booker.BookingApp.model.OwnerComment;
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

    @GetMapping(value = "/{ownerId}")
    public ResponseEntity<OwnerCommentDTO> getOwnerCommentByOwnerId(@PathVariable Long ownerId) {
        OwnerComment ownerComment = ownerCommentService.findOneByOwnerId(ownerId);

        if (ownerComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new OwnerCommentDTO(ownerComment), HttpStatus.OK);
    }

    @GetMapping(value = "/{guestId}")
    public ResponseEntity<OwnerCommentDTO> getOwnerCommentByGuestId(@PathVariable Long guestId) {
        OwnerComment ownerComment = ownerCommentService.findOneByGuestId(guestId);

        if (ownerComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new OwnerCommentDTO(ownerComment), HttpStatus.OK);
    }

    @GetMapping(value = "/all/{ownerId}")
    public ResponseEntity<List<OwnerCommentDTO>> getAllOwnersComments(@PathVariable Long ownerId) {
        List<OwnerComment> ownerComments = ownerCommentService.findAllForOwner(ownerId);

        List<OwnerCommentDTO> ownerCommentsDTO = new ArrayList<>();
        for (OwnerComment ownerComment : ownerComments) {
            ownerCommentsDTO.add(new OwnerCommentDTO(ownerComment));
        }

        return new ResponseEntity<>(ownerCommentsDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<OwnerCommentDTO> saveUser(@RequestBody OwnerCommentDTO ownerCommentDTO) {
        OwnerComment ownerComment = new OwnerComment();

        ownerComment.setOwnerId(ownerCommentDTO.getOwnerId());
        ownerComment.setGuestId(ownerCommentDTO.getGuestId());
        ownerComment.setContent(ownerCommentDTO.getContent());
        ownerComment.setWhen(ownerCommentDTO.getWhen());
        ownerComment.setReported(ownerCommentDTO.isReported());

        ownerComment = ownerCommentService.save(ownerComment);
        return new ResponseEntity<>(new OwnerCommentDTO(ownerComment), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<OwnerCommentDTO> updateUser(@RequestBody OwnerCommentDTO ownerCommentDTO) {
        OwnerComment ownerComment = ownerCommentService.findOne(ownerCommentDTO.getId());

        if (ownerComment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ownerComment.setOwnerId(ownerCommentDTO.getOwnerId());
        ownerComment.setGuestId(ownerCommentDTO.getGuestId());
        ownerComment.setContent(ownerCommentDTO.getContent());
        ownerComment.setWhen(ownerCommentDTO.getWhen());
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
}*/
