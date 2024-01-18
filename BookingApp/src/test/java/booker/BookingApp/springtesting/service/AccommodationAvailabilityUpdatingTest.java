package booker.BookingApp.springtesting.service;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.accommodation.AvailabilityDTO;
import booker.BookingApp.dto.accommodation.UpdateAvailabilityDTO;
import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.AvailabilityRepository;
import booker.BookingApp.repository.PriceRepository;
import booker.BookingApp.repository.ReservationRepository;
import booker.BookingApp.service.implementation.AccommodationService;
import booker.BookingApp.service.implementation.AvailabilityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AccommodationAvailabilityUpdatingTest {

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private AvailabilityRepository availabilityRepository;

    @MockBean
    private PriceRepository priceRepository;
    @MockBean
    private AccommodationService accommodationService;


    @Captor
    private ArgumentCaptor<Availability> availabilityArgumentCaptor;
    @Captor
    private ArgumentCaptor<Price> priceArgumentCaptor;

    @InjectMocks
    private AvailabilityService availabilityService;

    @Test
    @DisplayName("should update availability")
    public void shouldUpdateAvailability() throws IOException {
        Accommodation accommodation = new Accommodation(123L, "accommodation", "long description",
                "short description", 2L, new ArrayList<Amenity>(), new ArrayList<Image>(),
                new ArrayList<Availability>(), null, new ArrayList<Price>(), new ArrayList<AccommodationRating>(),
                new ArrayList<AccommodationComment>(), 3, 1, 10, AccommodationType.ROOM, true, false);
        Accommodation expected = accommodationRepository.save(accommodation);
        assertThat(expected.getId()).isEqualTo(accommodation.getId());
    }


}
