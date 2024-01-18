package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Image;
import booker.BookingApp.service.implementation.AccommodationService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

public @Data @AllArgsConstructor class AccommodationListingDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    private Image image;        //path to image in front
    @Min(0) @Max(5)
    private float rating;
    @Min(0)
    private double totalPrice;
    @Min(0)
    private double pricePerDay;

    public static AccommodationListingDTO makeFromAccommodation(Accommodation accommodation) {

        //TODO make getAverageRating

        return new AccommodationListingDTO(accommodation.getId(),
                accommodation.getTitle(), accommodation.getShortDescription(),
                accommodation.getImages().get(0), 0f,
                0d, 0d);
    }

}
