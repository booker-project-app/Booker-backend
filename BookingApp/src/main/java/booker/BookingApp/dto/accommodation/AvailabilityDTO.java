package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.Availability;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public @Data class AvailabilityDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public AvailabilityDTO(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static AvailabilityDTO makeFromAvailability(Availability availability) {
        return new AvailabilityDTO(availability.getStartDate(), availability.getEndDate());
    }
}
