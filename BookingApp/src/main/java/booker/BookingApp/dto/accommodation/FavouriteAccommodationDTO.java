package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

public @Data @AllArgsConstructor
class FavouriteAccommodationDTO {
    Long id;
    String title;
    String shortDescription;
    Image image;
    Double avgPrice;
    float avgRating;
    String address;

    public static FavouriteAccommodationDTO makeFromAccommodation(Accommodation accommodation) {

        return new FavouriteAccommodationDTO(accommodation.getId(),
                accommodation.getTitle(), accommodation.getShortDescription(),
                accommodation.getImages().get(0), accommodation.getPrices().get(0).getCost(),
                accommodation.getRatings().get(0).getRate(), accommodation.getAddress());
    }

}
