package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public @Data @AllArgsConstructor @NoArgsConstructor class AccommodationViewDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    private Address address;
    @NotNull
    private List<Amenity> amenities;
    @NotNull
    private List<Image> images;
    @NotNull
    private List<AvailabilityDTO> availabilities;
    @NotNull
    private List<Price> prices;
    @NotNull
    private List<AccommodationRating> ratings;
    @NotNull
    private List<AccommodationComment> comments;
    @NotNull
    private Long owner_id;
    @Min(1)
    private int min_capacity;
    @Min(1)
    private int max_capacity;
    private boolean manual_accepting;
    @Min(0)
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
