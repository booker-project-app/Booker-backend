//package booker.BookingApp.dto.accommodation;
//
//import booker.BookingApp.model.accommodation.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import java.awt.*;
//import java.util.ArrayList;
//
//public @Data class WholeAccommodationDTO {
//    private Long id;
//    private String title;
//    private String description;
//    private String shortDescription;
//    private String address;
//    private ArrayList<Amenity> amenities;
//    private ArrayList<Image> images;
//    private ArrayList<Availability> availabilities;
//    private ArrayList<Price> prices;
//    private ArrayList<AccommodationRating> ratings;
//    private ArrayList<AccommodationComment> comments;
//    private int deadline;
//
//    public static WholeAccommodationDTO makeFromAccommodation(Accommodation accommodation) {
//        WholeAccommodationDTO accommodationDTO = new WholeAccommodationDTO();
//
//        accommodationDTO.id = accommodation.getId();
//        accommodationDTO.title = accommodation.getTitle();
//        accommodationDTO.description = accommodation.getDescription();
//        accommodationDTO.shortDescription = accommodation.getShortDescription();
//        accommodationDTO.address = accommodation.getAddress();
//        accommodationDTO.amenities = accommodation.getAmenities();
//        accommodationDTO.images =accommodation.getImages();
//        accommodationDTO.ratings = accommodation.getRatings();
//        accommodationDTO.prices = accommodation.getPrices();
//        accommodationDTO.comments = accommodation.getComments();
//        accommodationDTO.deadline = accommodation.getDeadline();
//
//        return accommodationDTO;
//    }
//}
