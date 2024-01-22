package booker.BookingApp.springtesting.controller;

import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.dto.users.LoginUserDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.http.*;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationRequestControllerTest {

    private final String GUEST_EMAIL = "email1@gmail.com";
    private final String OWNER_EMAIL = "email2@gmail.com";
    private final String PASSWORD = "12345678";

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
    public void invalidDate_ShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "lalala", "aaa", 2,
                ReservationRequestStatus.WAITING, false, 250);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity =
                restTemplate.exchange("/api/requests", HttpMethod.POST,
                        httpEntity, ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("should return bad request, accommodation does not exist")
    public void nonExistingAccommodation_ShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 100L,
                1L, "2024-03-05", "2024-03-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity =
                restTemplate.exchange("/api/requests", HttpMethod.POST,
                        httpEntity, ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("should return bad request, accommodation not available for dates")
    public void accommodationNotAvailable_ShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-05", "2024-03-15", 2,
                ReservationRequestStatus.WAITING, false, 250);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity =
                restTemplate.exchange("/api/requests", HttpMethod.POST,
                        httpEntity, ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("should return bad request, dates are in the past")
    public void pastDates_ShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2023-02-20", "2023-02-25", 2,
                ReservationRequestStatus.WAITING, false, 250);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity =
                restTemplate.exchange("/api/requests", HttpMethod.POST,
                        httpEntity, ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("should return bad request, price not rigth")
    public void priceNotGood_ShouldReturnBadRequest() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-25", "2024-03-28", 2,
                ReservationRequestStatus.WAITING, false, 250);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity =
                restTemplate.exchange("/api/requests", HttpMethod.POST,
                        httpEntity, ReservationRequestDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("should return request, everything is right")
    public void success() {
        ReservationRequestDTO requestDTO = new ReservationRequestDTO(1L, 1L,
                1L, "2024-03-25", "2024-03-28", 2,
                ReservationRequestStatus.WAITING, false, 270);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenGuest);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(requestDTO, headers);

        ResponseEntity<ReservationRequestDTO> responseEntity =
                restTemplate.exchange("/api/requests", HttpMethod.POST,
                        httpEntity, ReservationRequestDTO.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ReservationRequestDTO request = responseEntity.getBody();
        assertEquals(requestDTO, request);
    }

}
