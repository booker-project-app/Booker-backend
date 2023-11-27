package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.AccommodationDTO;
import booker.BookingApp.dto.accommodation.AmenityDTO;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Filter;

import java.io.IOException;
import java.util.ArrayList;

public interface IAmenityService {
    ArrayList<AmenityDTO> findAllAmenitiesForAccommodation(Long accommodationId) throws IOException;

    AmenityDTO create(AmenityDTO amenityDTO);

    AmenityDTO update(AmenityDTO amenityDTO);

    void delete(Long id);

    ArrayList<AmenityDTO> findAll() throws IOException;

    ArrayList<String> getAllNames();
}