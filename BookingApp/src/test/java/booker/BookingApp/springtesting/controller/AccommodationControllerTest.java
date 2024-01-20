package booker.BookingApp.springtesting.controller;

import booker.BookingApp.controller.accommodation.AccommodationController;
import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.accommodation.CreatePriceDTO;
import booker.BookingApp.dto.accommodation.UpdateAvailabilityDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.dto.users.LoginUserDTO;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.service.implementation.AccommodationService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AccommodationControllerTest {
    private String GUEST_EMAIL = "email1@gmail.com";
    private String OWNER_EMAIL = "email2@gmail.com";
    private String PASSWORD = "12345678";

    private String tokenGuest;
    private String tokenOwner;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AccommodationService accommodationService;

    @BeforeAll
    public void login() throws JSONException {
        ResponseEntity<String> responseEntity1 = restTemplate.postForEntity("/api/users/login",
                new LoginUserDTO(GUEST_EMAIL, PASSWORD), String.class);
        JSONObject json1 = new JSONObject(responseEntity1.getBody());
        tokenGuest = json1.getString("token");

        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/users/login",
                new LoginUserDTO(OWNER_EMAIL, PASSWORD), String.class);
        JSONObject json2 = new JSONObject(responseEntity2.getBody());
        tokenOwner = json2.getString("token");
    }

    @Test
    @DisplayName("should return not 401, owner not authorized")
    public void ownerRequest_ShouldReturnNotAuthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(null, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity =
                restTemplate.exchange("/api/requests", HttpMethod.POST,
                        httpEntity, ReservationRequestDTO.class);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("should return bad request, accommodation doesn't exist")
    public void updateAvailabilityAccommodationNonExisting_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO(150.0, new Date(), new Date(), PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        // Create an HTTP entity with the request body and headers
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                999L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, start date is in the past")
    public void updateAvailabilityStartDateInPast_ShouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO(150.0, Date.from(Instant.now().minus(5, ChronoUnit.DAYS)), Date.from(Instant.now().plus(5, ChronoUnit.DAYS)), PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setPrice(createPriceDTO);
        Date pastDate = (Date.from(Instant.now().minus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setStartDate(pastDate);
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, end date is in the past")
    public void updateAvailabilityEndDateInPast_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO(150.0, new Date(), new Date(), PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setPrice(createPriceDTO);
        Date pastDate = Date.from(Instant.now().minus(5, ChronoUnit.DAYS));
        updateAvailabilityDTO.setStartDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setEndDate(pastDate);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, both dates are in the past")
    public void updateAvailabilityBothDatesInPast_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO(150.0, new Date(), new Date(), PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setPrice(createPriceDTO);
        Date pastDate = Date.from(Instant.now().minus(15, ChronoUnit.DAYS));
        updateAvailabilityDTO.setStartDate(pastDate);
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().minus(10, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, end date is before start date")
    public void updateAvailability_EndDateBeforeStartDate() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO(150.0, new Date(), new Date(), PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setPrice(createPriceDTO);
        Date futureDate = Date.from(Instant.now().plus(10, ChronoUnit.DAYS));
        updateAvailabilityDTO.setStartDate(futureDate);
        updateAvailabilityDTO.setEndDate(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)));
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, deadline is negative")
    public void updateAvailabilityNegativeDeadline() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO(150.0, new Date(), new Date(), PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setPrice(createPriceDTO);
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(futureDate);
        updateAvailabilityDTO.setDeadline(-1);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, price is null")
    public void updateAvailabilityNullPrice_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO(150.0, new Date(), new Date(), PriceType.PER_GUEST);
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setPrice(null);
        Date futureDate = new Date(System.currentTimeMillis() + 86400000);
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(futureDate);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, price start date is in the past")
    public void updateAvailabilityPriceStartDateInPast_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setType(PriceType.PER_ACCOMMODATION);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(new Date());

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);


        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());

    }

    @Test
    @DisplayName("should return bad request, price end date is in the past")
    public void updateAvailabilityPriceEndDateInPast_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(new Date());
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, both price dates are in the past")
    public void updateAvailability_BothPriceDatesInPast_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().minus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().minus(15, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, price end date is before start date")
    public void updateAvailabilityPriceEndDateBeforeStartDate_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, cost is negative")
    public void updateAvailabilityNegativeCost_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(-150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, price type is null")
    public void updateAvailabilityPriceTypeNull_shouldReturnBadRequest() {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        createPriceDTO.setType(null);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        updateAvailabilityDTO.setStartDate(new Date());
        updateAvailabilityDTO.setEndDate(new Date());
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                1L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should return bad request, accommodation has currently active reservation")
    public void updateAvailabilityHasActiveReservations_shouldReturnBadRequest() throws ParseException {
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)));
        createPriceDTO.setToDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
        String startDateString = "2024-03-21";
        String endDateString = "2024-03-24";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);
        updateAvailabilityDTO.setStartDate(startDate);
        updateAvailabilityDTO.setEndDate(endDate);
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                Void.class,
                3L
        );

        // Check if the response status code is HttpStatus.BAD_REQUEST
        // Assert the response status code
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        System.out.println(responseEntity.getBody());
    }

    @Test
    @DisplayName("should update availability, everything is fine")
    public void updateAvailabilityEverythingFine_shouldReturnOK() throws ParseException {
        String startDateString = "2024-12-15";
        String endDateString = "2024-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(startDateString);
        Date endDate = sdf.parse(endDateString);
        CreatePriceDTO createPriceDTO = new CreatePriceDTO();
        createPriceDTO.setCost(150.0);
        createPriceDTO.setFromDate(startDate);
        createPriceDTO.setToDate(endDate);
        createPriceDTO.setType(PriceType.PER_GUEST);

        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();

        updateAvailabilityDTO.setStartDate(startDate);
        updateAvailabilityDTO.setEndDate(endDate);
        updateAvailabilityDTO.setPrice(createPriceDTO);
        updateAvailabilityDTO.setDeadline(3);

        // Set headers with owner token
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenOwner);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(updateAvailabilityDTO, headers);

        // Perform the request
        ResponseEntity<AccommodationViewDTO> responseEntity = restTemplate.exchange(
                "/api/accommodations/update_availability/{id}",
                HttpMethod.POST,
                httpEntity,
                AccommodationViewDTO.class,
                3L
        );

//        ResponseEntity<Void> responseEntity = restTemplate.exchange(
//                "/api/accommodations/update_availability/{id}",
//                HttpMethod.PUT,
//                httpEntity,
//                Void.class, 3L
//        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());




    }

}
