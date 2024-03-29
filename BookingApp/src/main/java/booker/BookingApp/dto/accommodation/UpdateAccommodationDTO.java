package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Address;
import booker.BookingApp.model.accommodation.Amenity;
import booker.BookingApp.model.accommodation.Image;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public @Data @AllArgsConstructor @NoArgsConstructor class UpdateAccommodationDTO {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private List<Image> images;
    private boolean manual_accepting;

    public UpdateAccommodationDTO(Accommodation accommodation){

    }
}
