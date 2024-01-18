package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.Address;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String street;
    @NotEmpty
    private String city;
    @Min(-90) @Max(90)
    private double latitude;
    @Min(-180) @Max(180)
    private double longitude;

    public AddressDTO() {
    }

    public AddressDTO(Address address) {
        this(address.getStreet(), address.getCity(), address.getLatitude(), address.getLongitude());
    }

    public AddressDTO(String street, String city, double latitude, double longitude) {
        this.street = street;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
