package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.AccommodationRating;
import lombok.Data;

import java.awt.*;

public @Data class AccommodationListingDTO {
    private Long id;
    private String title;
    private String description;
    private Image image;
    private AccommodationRating rating;
    private double totalPrice;
    private double pricePerDay;

    public AccommodationListingDTO makeFromAccommodation(Accommodation accommodation) {
        AccommodationListingDTO listingDTO = new AccommodationListingDTO();

        listingDTO.id = accommodation.getId();
        listingDTO.title = accommodation.getTitle();
        listingDTO.description = accommodation.getShortDescription();
        listingDTO.image =accommodation.getImages().get(0);
        //TODO make getAverageRating, getTotalPrice and getPricePerDay methods in service
        listingDTO.rating = accommodation.getRatings().get(0);
        listingDTO.pricePerDay = accommodation.getPrices().get(0).getCost();
        listingDTO.totalPrice = accommodation.getPrices().get(0).getCost();

        return listingDTO;
    }
}
