package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.*;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.service.interfaces.IAccommodationService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    AccommodationRepository repository;

    @Override @Transactional
    public ArrayList<AccommodationListingDTO> findAll() throws IOException {
        List<Accommodation> accommodations = repository.findAll();
        ArrayList<AccommodationListingDTO> dtos = new ArrayList<>();
        for(Accommodation a : accommodations) {
            dtos.add(AccommodationListingDTO.makeFromAccommodation(a));
        }
        return dtos;
    }

    @Override
    public AccommodationViewDTO findOne(Long id) throws IOException {
        ArrayList<Image> images = new ArrayList<>();
        images.add(new Image(1L, "../../assets/images/kitchen-2165756_640.jpg", new Accommodation()));
        images.add(new Image(1L, "../../assets/images/living-room.jpg", new Accommodation()));
        images.add(new Image(1L, "../../assets/images/kitchen-2165756_640.jpg", new Accommodation()));
        images.add(new Image(1L, "../../assets/images/living-room.jpg", new Accommodation()));
        images.add(new Image(1L, "../../assets/images/kitchen-2165756_640.jpg", new Accommodation()));
        images.add(new Image(1L, "../../assets/images/kitchen-2165756_640.jpg", new Accommodation()));
        images.add(new Image(1L, "../../assets/images/living-room.jpg", new Accommodation()));
        //TODO: FIX!!!
        char[] name = new char[4];
        name[1] = 'w';
        name[1] = 'i';
        name[1] = 'f';
        name[1] = 'i';
        ArrayList<Amenity> amenities = new ArrayList<>();
        amenities.add(new Amenity(1L, "wifi",null, "../../../assets/images/icons8-wifi-30.png"));
        amenities.add(new Amenity(2L, "good place", null, "../../../assets/images/icons8-location-32.png"));
        amenities.add(new Amenity(3L, "AC", null,"../../../assets/images/icons8-ac-30.png"));
        amenities.add(new Amenity(4L, "free cancellation", null, "../../../assets/images/icons8-calendar-32.png"));
//        amenities.add(new Amenity(2L, "good place", ImageIO.read(new File("src/main/resources/london_image.jpg"))));
//        amenities.add(new Amenity(3L, "AC", ImageIO.read(new File("src/main/resources/madrid_image.jpg"))));
//        amenities.add(new Amenity(4L, "parking spot", ImageIO.read(new File("src/main/resources/paris_image.jpg"))));
        ArrayList<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1L, new Date(), new Date(), new Accommodation()));
        availabilities.add(new Availability(2L, new Date(), new Date(), new Accommodation()));
        availabilities.add(new Availability(3L, new Date(), new Date(), new Accommodation()));
        availabilities.add(new Availability(4L, new Date(), new Date(), new Accommodation()));
        ArrayList<Price> prices = new ArrayList<>();
        prices.add(new Price());
        prices.add(new Price());
        prices.add(new Price());
        prices.add(new Price());
        ArrayList<AccommodationRating> ratings = new ArrayList<>();
        ratings.add(new AccommodationRating());
        ratings.add(new AccommodationRating());
        ratings.add(new AccommodationRating());
        ratings.add(new AccommodationRating());
        ArrayList<AccommodationComment> comments = new ArrayList<>();
        comments.add(new AccommodationComment());
        comments.add(new AccommodationComment());
        comments.add(new AccommodationComment());
        comments.add(new AccommodationComment());
        String description = "The units come with parquet floors and feature a fully equipped\n" +
                "        kitchen with a microwave, a dining area, a flat-screen TV with\n" +
                "        streaming services, and a private bathroom with walk-in shower and a\n" +
                "        hair dryer. A toaster, a fridge and stovetop are also available, as\n" +
                "        well as a coffee machine and a kettle.\n" +
                "        The units come with parquet floors and feature a fully equipped\n" +
                "        kitchen with a microwave, a dining area, a flat-screen TV with\n" +
                "        streaming services, and a private bathroom with walk-in shower and a\n" +
                "        hair dryer. A toaster, a fridge and stovetop are also available, as\n" +
                "        well as a coffee machine and a kettle.\n" +
                "        \n" +
                "        Eventim Apollo is 2.4 km from the apartment, while South Kensington\n" +
                "        Underground Station is 3 km from the property. The nearest airport\n" +
                "        is London Heathrow Airport, 21 km from Central London Luxury Studios\n" +
                "        Fulham Close to Underground Newly Refurbished.\n" +
                "        The units come with parquet floors and feature a fully equipped\n" +
                "        kitchen with a microwave, a dining area, a flat-screen TV with\n" +
                "        streaming services, and a private bathroom with walk-in shower and a\n" +
                "        hair dryer. A toaster, a fridge and stovetop are also available, as\n" +
                "        well as a coffee machine and a kettle.The units come with parquet floors and feature a fully equipped\n" +
                "        kitchen with a microwave, a dining area, a flat-screen TV with\n" +
                "        streaming services, and a private bathroom with walk-in shower and a\n" +
                "        hair dryer. A toaster, a fridge and stovetop are also available, as\n" +
                "        well as a coffee machine and a kettle.";
        //TODO: images
        AccommodationViewDTO accommodation = new AccommodationViewDTO(id, "Example accommodation", description, "Example address 12bb", amenities, images, availabilities, prices, ratings, comments);
        return accommodation;
    }

    @Override
    public AccommodationViewDTO create(CreateAccommodationDTO accommodationDto) throws Exception {
        Accommodation accommodation = new Accommodation();
        accommodation.setTitle(accommodationDto.getTitle());
        accommodation.setDescription(accommodationDto.getDescription());
        accommodation.setAddress(accommodationDto.getAddress());

        ArrayList<Amenity> amenities = new ArrayList<Amenity>();
        for(AmenityDTO amenityDTO : accommodationDto.getAmenities()){
            Amenity amenity = amenityDTO.toAmenity(accommodation);
            amenities.add(amenity);
        }
        accommodation.setAmenities(amenities);

        ArrayList<Image> images = new ArrayList<Image>();
        for(ImageDTO imageDTO : accommodationDto.getImages()) {
            Image image = imageDTO.toImage(accommodation);
            images.add(image);
        }
        accommodation.setImages(images);


        Availability availability = new Availability();
        availability.setStartDate(accommodationDto.getStartDate());
        availability.setEndDate(accommodationDto.getEndDate());
        availability.setAccommodation(accommodation);
        ArrayList<Availability> availabilities = new ArrayList<Availability>();
        availabilities.add(availability);
        accommodation.setAvailabilities(availabilities);

        Price price = new Price();
        price.setCost(accommodationDto.getPrice().getCost());
        price.setFromDate(accommodationDto.getPrice().getFromDate());
        price.setToDate(accommodationDto.getPrice().getToDate());
        price.setType(accommodationDto.getPrice().getType());
        price.setAccommodation(accommodation);
        ArrayList<Price> prices = new ArrayList<Price>();
        prices.add(price);
        accommodation.setPrices(prices);


        repository.save(accommodation);

        AccommodationViewDTO accommodationViewDTO = AccommodationViewDTO.makeFromAccommodation(accommodation);
        return accommodationViewDTO;
    }

    @Override
    public AccommodationViewDTO update(AccommodationViewDTO accommodation) throws Exception {
        return accommodation;
    }

    @Override
    public void delete(Long id) {
        //TODO: delete accommodation
    }

    @Override
    public ArrayList<AccommodationListingDTO> findOwnersActiveAccommodations(Long ownerId) {
        try {
            //TODO: this should return accommodation listing dto
            return findAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeFavoriteAccommodation(Long userId, Long accommodationId) {
        //TODO: remove favourite
    }

    @Override
    public ArrayList<AccommodationListingDTO> findUnapprovedAccommodations() {
        return findOwnersActiveAccommodations(2L);
    }

    @Override
    public ArrayList<FavouriteAccommodationDTO> findGuestsFavouriteAccommodations(Long guestId) throws IOException {
        AccommodationViewDTO accommodation = findOne(1L);

        Image image1 = new Image(1L, "src/main/resources/lisbon_image.jpg", new Accommodation());
        Image image2 = new Image(2L, "src/main/resources/london_image.jpg", new Accommodation());
        Image image3 = new Image(3L, "src/main/resources/madrid_image.jpg", new Accommodation());
        FavouriteAccommodationDTO accommodation1 = new FavouriteAccommodationDTO(1L, "Example accommodation 1", "Description 1", image1, 80.00, 4,"Example address 1");
        FavouriteAccommodationDTO accommodation2 = new FavouriteAccommodationDTO(2L, "Example accommodation 2", "Description 2", image2, 100.00, 5,"Example address 2");
        FavouriteAccommodationDTO accommodation3 = new FavouriteAccommodationDTO(3L, "Example accommodation 3", "Description 3", image3, 85.50, 3.2F,"Example address 3");
        ArrayList<FavouriteAccommodationDTO> accommodations = new ArrayList<>();
        accommodations.add(accommodation1);
        accommodations.add(accommodation2);
        accommodations.add(accommodation3);
        return accommodations;

    }

    @Override
    public ArrayList<AccommodationListingDTO> search(String startDate, String endDate, String location, int people) {
        return findOwnersActiveAccommodations(2L);
    }

    @Override
    public ArrayList<AccommodationListingDTO> findAllOwnersAccommodations(Long ownerId) {
        return findOwnersActiveAccommodations(2L);
    }

    @Override
    public ArrayList<AccommodationListingDTO> applyFilters(ArrayList<AccommodationListingDTO> accommodations, Filter filter) {
        return findOwnersActiveAccommodations(2L);
    }
}