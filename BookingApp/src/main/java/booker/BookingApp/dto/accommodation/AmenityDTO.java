package booker.BookingApp.dto.accommodation;

import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Amenity;
import booker.BookingApp.model.accommodation.Image;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

public @Data class AmenityDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String image_path;

    // Parameterized constructor
    public AmenityDTO(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image_path = image;
    }

    public static AmenityDTO makeFromAmenity(Amenity amenity) {
        return new AmenityDTO(amenity.getId(), amenity.getName(), amenity.getImage_path());
    }

    public Amenity toAmenity(Accommodation accommodation){
        Amenity amenity = new Amenity();
        amenity.setName(name);
        amenity.setAccommodation(accommodation);
        return amenity;
    }
}
