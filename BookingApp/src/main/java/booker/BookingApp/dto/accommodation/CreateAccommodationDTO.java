package booker.BookingApp.dto.accommodation;

import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Address;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public @Data class CreateAccommodationDTO {
    private Long id;
    private String title;
    private String description;
    private AddressDTO address;
    private String[] amenities;
//    private List<ImageDTO> images;
    private MultipartFile[] images;
    private AccommodationType type;
    private Date startDate;
    private Date endDate;
    private CreatePriceDTO price;
    private int min_capacity;
    private int max_capacity;


    public CreateAccommodationDTO() {
    }


}
