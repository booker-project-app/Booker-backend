package booker.BookingApp.model.business;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

public @Data @AllArgsConstructor class Report {
    @NotNull
    String name;
    @Min(0)
    double profit;
    @Min(0)
    int reservations;
}
