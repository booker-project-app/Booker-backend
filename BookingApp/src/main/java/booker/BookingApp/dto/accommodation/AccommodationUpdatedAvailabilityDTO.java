package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationUpdatedAvailabilityDTO {
    @NotNull
    private Long id;
    @NotNull
    private List<AvailabilityDTO> availabilities;
    @NotNull
    private List<Price> prices;
    @Min(0)
    private int deadline;


    public static AccommodationUpdatedAvailabilityDTO createFromAccommodation(Accommodation accommodation) {
        List<AvailabilityDTO> availabilityDTOS = new ArrayList<>();
        for(Availability a : accommodation.getAvailabilities()) {
            availabilityDTOS.add(new AvailabilityDTO(a.getStartDate(), a.getEndDate()));
        }
        return new AccommodationUpdatedAvailabilityDTO(accommodation.getId(),
                                        availabilityDTOS,
                                        accommodation.getPrices(),
                                        accommodation.getDeadline());
    }


}
