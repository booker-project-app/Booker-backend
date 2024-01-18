package booker.BookingApp.dto.accommodation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

public @Data
@AllArgsConstructor class AccommodationNameDTO {
    @NotEmpty
    private String title;
    @NotNull
    private Long id;
}
