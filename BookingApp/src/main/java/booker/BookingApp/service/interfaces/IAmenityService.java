package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.AmenityDTO;
import booker.BookingApp.model.accommodation.Amenity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IAmenityService {
    ArrayList<AmenityDTO> findAllAmenitiesForAccommodation(Long accommodationId) throws IOException;

    AmenityDTO create(AmenityDTO amenityDTO);

    AmenityDTO update(AmenityDTO amenityDTO);

    void delete(Long id);

    void removeAmenityFromAcc(String amenity_name, Long accId);

    ArrayList<AmenityDTO> findAll() throws IOException;

    ArrayList<String> getAllNames();

    ArrayList<String> getAllAmenityNamesForAccommodation(Long accommodationId);

    void updateAmenities(Amenity[] amenities, Long accommodationId);
}