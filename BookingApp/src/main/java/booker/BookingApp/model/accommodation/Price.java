package booker.BookingApp.model.accommodation;

import booker.BookingApp.enums.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public @Data class Price {
    private Long id;
    private Long accommodationId;
    private double cost;
    private Date fromDate;
    private Date toDate;
    private PriceType type;

}
