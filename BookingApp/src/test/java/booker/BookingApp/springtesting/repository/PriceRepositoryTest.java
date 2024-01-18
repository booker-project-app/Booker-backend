package booker.BookingApp.springtesting.repository;

import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.PriceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class PriceRepositoryTest {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Test
    @DisplayName("should save price")
    public void shouldSavePrice() {
        Accommodation accommodation = new Accommodation(null, "accommodation 1", "long description",
                "short description", 2L, new ArrayList<Amenity>(), new ArrayList<Image>(),
                new ArrayList<Availability>(), null, new ArrayList<Price>(),
                new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                3, 1, 10, AccommodationType.ROOM, true, false);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        Price price = new Price();
        price.setAccommodation(savedAccommodation);
        price.setCost(150.0);
        price.setFromDate(new Date());
        price.setToDate(new Date());
        price.setType(PriceType.PER_GUEST);

        Price savedPrice = priceRepository.save(price);

        assertThat(savedAccommodation).usingRecursiveComparison().ignoringFields("id").isEqualTo(accommodation);
        assertThat(savedPrice).usingRecursiveComparison().ignoringFields("id").isEqualTo(price);
        assertThat(savedPrice.getId()).isNotNull();
        assertEquals(savedPrice.getFromDate(), price.getFromDate());
        assertEquals(savedPrice.getToDate(), price.getToDate());
        assertEquals(savedPrice.getAccommodation(), price.getAccommodation());
        assertEquals(savedPrice.getType(), price.getType());
        assertEquals(savedPrice.getAccommodation().getId(), savedAccommodation.getId());
    }
}
