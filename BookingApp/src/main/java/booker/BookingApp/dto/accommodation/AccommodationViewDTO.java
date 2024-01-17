package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @AllArgsConstructor @NoArgsConstructor class AccommodationViewDTO {
    private Long id;
    private String title;
    private String description;
    private Address address;
    private List<Amenity> amenities;
    private List<Image> images;
    private List<AvailabilityDTO> availabilities;
    private List<Price> prices;
    private List<AccommodationRating> ratings;
    private List<AccommodationComment> comments;
    private Long owner_id;
    private int min_capacity;
    private int max_capacity;
    private boolean manual_accepting;
    private int deadline;

    public static AccommodationViewDTO makeFromAccommodation(Accommodation accommodation) {
        List<AvailabilityDTO> availabilityDTOS = new ArrayList<>();
        for(Availability a : accommodation.getAvailabilities()) {
            availabilityDTOS.add(new AvailabilityDTO(a.getStartDate(), a.getEndDate()));
        }
        return new AccommodationViewDTO(accommodation.getId(),
                accommodation.getTitle(), accommodation.getDescription(),
                accommodation.getAddress(), accommodation.getAmenities(),
                accommodation.getImages(), availabilityDTOS,
                accommodation.getPrices(), accommodation.getRatings(),
                accommodation.getComments(), accommodation.getOwner_id(),
                accommodation.getMin_capacity(), accommodation.getMax_capacity(),
                accommodation.isManual_accepting(), accommodation.getDeadline());
    }
}
