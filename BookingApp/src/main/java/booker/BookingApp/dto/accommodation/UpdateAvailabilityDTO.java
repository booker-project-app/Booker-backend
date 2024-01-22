package booker.BookingApp.dto.accommodation;

import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Availability;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UpdateAvailabilityDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    public Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    public Date endDate;
    @NotNull
    public CreatePriceDTO price;
    @Min(0)
    public int deadline;

    public UpdateAvailabilityDTO() {
    }
}
