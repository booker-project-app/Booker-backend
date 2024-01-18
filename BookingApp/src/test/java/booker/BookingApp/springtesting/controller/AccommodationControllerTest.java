package booker.BookingApp.springtesting.controller;

import booker.BookingApp.dto.accommodation.UpdateAvailabilityDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.dto.users.LoginUserDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.text.SimpleDateFormat;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationControllerTest {
    private String GUEST_EMAIL = "email1@gmail.com";
    private String OWNER_EMAIL = "email2@gmail.com";
    private String PASSWORD = "12345678";

    private String tokenGuest;
    private String tokenOwner;

    @Autowired
    private TestRestTemplate restTemplate;

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
    @DisplayName("should return bad request, dates invalid")
    public void updateAvailabilityInvalidDates_shouldReturnBadRequest() {
        UpdateAvailabilityDTO updateAvailabilityDTO = new UpdateAvailabilityDTO();
    }
}
