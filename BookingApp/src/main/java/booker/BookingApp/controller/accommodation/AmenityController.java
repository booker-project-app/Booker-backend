package booker.BookingApp.controller.accommodation;

import booker.BookingApp.dto.accommodation.AmenityDTO;
import booker.BookingApp.model.accommodation.Filter;
import booker.BookingApp.service.implementation.AccommodationService;
import booker.BookingApp.service.interfaces.IAccommodationService;
import booker.BookingApp.service.interfaces.IAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/amenities")
@CrossOrigin(origins = "http://localhost:4200")
public class AmenityController {
    @Autowired
    IAmenityService service;

    //create an amenity
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> insert(@RequestBody AmenityDTO amenity) throws Exception {
        AmenityDTO dto = service.create(amenity);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //get amenities of accommodation
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<AmenityDTO>> findAmenitiesForAccommodation(@PathVariable Long id) throws IOException {
        ArrayList<AmenityDTO> accommodation = service.findAllAmenitiesForAccommodation(id);
        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    }

    //get all amenities
    @GetMapping(value ="/all", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<ArrayList<AmenityDTO>> getAll() throws IOException {
        ArrayList<AmenityDTO> accommodations = service.findAll();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //delete amenity
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        service.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //update amenity
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAmenity(@RequestBody AmenityDTO updatedAmenity) throws Exception {
        service.update(updatedAmenity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //get names of all amenities
    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getNames() throws IOException {
        List<String> names = service.getAllNames();
        return new ResponseEntity<List<String>>(names, HttpStatus.OK);
    }
}
