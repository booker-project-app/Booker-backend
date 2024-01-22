package booker.BookingApp.springtesting.repository;

import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.AvailabilityRepository;
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
public class AvailabilityRepositoryTest {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Test
    @DisplayName("should save availability")
    public void shouldSaveAvailability() {
        Accommodation accommodation = new Accommodation(null, "accommodation 1", "long description",
                "short description", 2L, new ArrayList<Amenity>(), new ArrayList<Image>(),
                new ArrayList<Availability>(), null, new ArrayList<Price>(),
                new ArrayList<AccommodationRating>(), new ArrayList<AccommodationComment>(),
                3, 1, 10, AccommodationType.ROOM, true, false);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        Availability availability = new Availability(null, new Date(), new Date(), savedAccommodation);
        Availability savedAvailability = availabilityRepository.save(availability);


        assertThat(savedAccommodation).usingRecursiveComparison().ignoringFields("id").isEqualTo(accommodation);
        assertThat(savedAvailability).usingRecursiveComparison().ignoringFields("id").isEqualTo(availability);
        assertThat(savedAvailability.getId()).isNotNull();
        assertEquals(availability.getStartDate(), savedAvailability.getStartDate());
        assertEquals(availability.getEndDate(), savedAvailability.getEndDate());
        assertEquals(availability.getAccommodation().getId(), savedAccommodation.getId());
        assertThat(savedAvailability.getAccommodation()).isNotNull();

    }
}