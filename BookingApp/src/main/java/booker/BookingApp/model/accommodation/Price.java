package booker.BookingApp.model.accommodation;

import booker.BookingApp.enums.PriceType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
public @Data class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @Column(name = "type", nullable = false)
    private PriceType type;
}
