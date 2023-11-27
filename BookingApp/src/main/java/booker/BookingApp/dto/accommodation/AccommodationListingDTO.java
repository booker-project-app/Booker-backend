//package booker.BookingApp.dto.accommodation;
//
//import booker.BookingApp.model.accommodation.Accommodation;
//import booker.BookingApp.model.accommodation.AccommodationRating;
//import lombok.Data;
//
//import java.awt.*;
//
//public @Data class AccommodationListingDTO {
//    private Long id;
//    private String title;
//    private String description;
//    private Image image;
//    private AccommodationRating rating;
//    private double totalPrice;
//    private double pricePerDay;
//
//    public static AccommodationListingDTO makeFromAccommodation(Accommodation accommodation) {
//        AccommodationListingDTO listingDTO = new AccommodationListingDTO();
//
//        listingDTO.id = accommodation.getId();
//        listingDTO.title = accommodation.getTitle();
//        listingDTO.description = accommodation.getShortDescription();
//        listingDTO.image =accommodation.getImages().get(0);
//        //TODO make getAverageRating, getTotalPrice and getPricePerDay methods in service
//        listingDTO.rating = accommodation.getRatings().get(0);
//        listingDTO.pricePerDay = accommodation.getPrices().get(0).getCost();
//        listingDTO.totalPrice = accommodation.getPrices().get(0).getCost();
//
//        return listingDTO;
//    }
//
//    public static AccommodationListingDTO makeFromWholeAccommodation(WholeAccommodationDTO a) {AccommodationListingDTO listingDTO = new AccommodationListingDTO();
//
//        listingDTO.id = a.getId();
//        listingDTO.title = a.getTitle();
//        listingDTO.description = a.getShortDescription();
//        listingDTO.image =a.getImages().get(0);
//        //TODO make getAverageRating, getTotalPrice and getPricePerDay methods in service
//        listingDTO.rating = a.getRatings().get(0);
//        listingDTO.pricePerDay = a.getPrices().get(0).getCost();
//        listingDTO.totalPrice = a.getPrices().get(0).getCost();
//
//        return listingDTO;
//    }
//}
