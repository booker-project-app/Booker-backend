package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.*;
import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.*;
import booker.BookingApp.model.requestsAndReservations.Reservation;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.repository.*;
import booker.BookingApp.service.interfaces.IAccommodationService;
import booker.BookingApp.util.ImageUploadUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AccommodationService implements IAccommodationService {

    @Value("src/main/resources/images/accommodations")
    private String imagesDirPath;

    @Value("../../Booker-frontend/booker/src/assets/images/accommodation")
    private String imagesDirPathFront;

    @Value("../../../../../res/drawable")
    private String imagesDirPathMobile;

    @Autowired
    AccommodationRepository repository;

    @Autowired

    AvailabilityRepository availabilityRepository;

    @Autowired
    AvailabilityService availabilityService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AmenityRepository amenityRepository;
    @Autowired
    AmenityService amenityService;

    @Autowired
    PriceService priceService;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    ReservationRepository reservationRepository;



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
        Optional<Accommodation> accommodation = repository.findById(id);
        if(accommodation.isEmpty()) {return null;}
        return AccommodationViewDTO.makeFromAccommodation(accommodation.get());
    }

    @Override
    public AccommodationViewDTO create(CreateAccommodationDTO accommodationDto) throws Exception {
        Accommodation accommodation = new Accommodation();
        accommodation.setTitle(accommodationDto.getTitle());
        accommodation.setDescription(accommodationDto.getDescription());
        accommodation.setShortDescription(accommodationDto.getShortDescription());
        accommodation.setMin_capacity(accommodationDto.getMin_capacity());
        accommodation.setMax_capacity(accommodationDto.getMax_capacity());
        accommodation.setType(accommodationDto.getType());
        accommodation.setManual_accepting(true);

        //addressRepository.save(address);
        //accommodation.setAddress(address);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Owner) {
                Owner user = (Owner) principal;
                accommodation.setOwner_id(user.getId());
            } else {
                // Handle the case where the principal is not an instance of User
                throw new RuntimeException("Unexpected principal type: " + principal.getClass());
            }
        } else {
            // Handle the case where there is no authentication
            throw new RuntimeException("User not authenticated");
        }

        repository.save(accommodation);

        Address address = new Address();
        address.setStreet(accommodationDto.getAddress().getStreet());
        address.setCity(accommodationDto.getAddress().getCity());
        address.setLatitude(accommodationDto.getAddress().getLatitude());
        address.setLongitude(accommodationDto.getAddress().getLongitude());
        address.setAccommodation(accommodation);
        addressRepository.save(address);

        ArrayList<Amenity> amenities = new ArrayList<Amenity>();
        for(String amenityName : accommodationDto.getAmenities()){
            Amenity amenity = new Amenity();
            amenity.setName(amenityName);
            amenity.setImage_path("");
            amenity.setAccommodation(accommodation);
            amenities.add(amenity);
        }
        amenityRepository.saveAll(amenities);
        accommodation.setAmenities(amenities);


//        ArrayList<Image> images = new ArrayList<Image>();
//        for(ImageDTO imageDTO : accommodationDto.getImages()) {
//            Image image = imageDTO.toImage(accommodation);
//            images.add(image);
//        }
//        accommodation.setImages(images);

        Availability availability = new Availability();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate = simpleDateFormat.parse(accommodationDto.getStartDate());
//        Date endDate = simpleDateFormat.parse(accommodationDto.getEndDate());
        availability.setStartDate(accommodationDto.getStartDate());
        availability.setEndDate(accommodationDto.getEndDate());
        availability.setAccommodation(accommodation);
        ArrayList<Availability> availabilities = new ArrayList<Availability>();
        availabilities.add(availability);
        availabilityRepository.saveAll(availabilities);
        accommodation.setAvailabilities(availabilities);


        Price price = new Price();
        price.setCost(accommodationDto.getPrice().getCost());
        price.setFromDate(accommodationDto.getPrice().getFromDate());
        price.setToDate(accommodationDto.getPrice().getToDate());
        price.setType(accommodationDto.getPrice().getType());
        price.setAccommodation(accommodation);
        ArrayList<Price> prices = new ArrayList<Price>();
        prices.add(price);
        priceRepository.saveAll(prices);
        accommodation.setPrices(prices);


        //repository.save(accommodation);
        //availabilityService.create(accommodation.getId(), availabilityDTO);

        ArrayList<Image> images = new ArrayList<Image>();
        for(String fileName : accommodationDto.getImages()) {
            Image image = new Image(null,"../../assets/images/accommodation" + fileName,
                    "../../../../../res/drawable" + fileName, accommodation);
            images.add(image);
        }

        accommodation.setImages(images);
        imageRepository.saveAll(images);
        //repository.save(accommodation);

        AccommodationViewDTO accommodationViewDTO = AccommodationViewDTO.makeFromAccommodation(accommodation);
        return accommodationViewDTO;
    }

    @Override
    public void update(AccommodationViewDTO accommodation, UpdateAccommodationDTO updateAccommodation) throws Exception {
        Accommodation a = repository.findById(accommodation.getId()).get();
        if (a!=null){
            a.setTitle(updateAccommodation.getTitle());
            a.setDescription(updateAccommodation.getDescription());
            a.setImages(updateAccommodation.getImages());
            a.setManual_accepting(updateAccommodation.isManual_accepting());
            repository.save(a);
        }
    }

    @Override
    public void updateAddress(Address existingAddr, AddressDTO addressDTO){
        Address a = addressRepository.findById(existingAddr.getId()).get();
        System.out.println(a);
        if (a!=null){
            a.setCity(addressDTO.getCity());
            a.setStreet(addressDTO.getStreet());
            a.setLongitude(addressDTO.getLongitude());
            a.setLatitude(addressDTO.getLatitude());
            addressRepository.save(a);
        }
    }

    @Override
    public void delete(Long id) {
        //TODO: delete accommodation
    }

    @Override
    public ArrayList<AccommodationListingDTO> findOwnersActiveAccommodations(Long ownerId) {
        List<Accommodation> accommodations = repository.findSpecifiedForOwner(ownerId, true);
        ArrayList<AccommodationListingDTO> dtos = new ArrayList<>();
        for(Accommodation a : accommodations) {
            dtos.add(AccommodationListingDTO.makeFromAccommodation(a));
        }
        return dtos;
    }

    @Override
    public ArrayList<AccommodationListingDTO> findAllUnapprovedAccommodations() {
        List<Accommodation> accommodations = repository.findUnapproved(false);
        ArrayList<AccommodationListingDTO> dtos = new ArrayList<>();
        for(Accommodation a : accommodations) {
            dtos.add(AccommodationListingDTO.makeFromAccommodation(a));
        }
        return dtos;
    }

    @Override
    public void approve(Long accommodationId) {
        System.out.println("dunja1");
        Accommodation accommodation = repository.findById(accommodationId).orElse(null);

        if (accommodation != null) {
            accommodation.setAccepted(true);
            repository.save(accommodation);
        }
        System.out.println("dunja2");
    }

    @Override
    public ArrayList<FavouriteAccommodationDTO> findGuestsFavouriteAccommodations(Long guestId) throws IOException {
        AccommodationViewDTO accommodation = findOne(1L);

        Image image1 = new Image(1L, "src/main/resources/lisbon_image.jpg", "", new Accommodation());
        Image image2 = new Image(2L, "src/main/resources/london_image.jpg", "", new Accommodation());
        Image image3 = new Image(3L, "src/main/resources/madrid_image.jpg", "", new Accommodation());
        FavouriteAccommodationDTO accommodation1 = new FavouriteAccommodationDTO(1L, "Example accommodation 1", "Description 1", image1, 80.00, 4,new Address());
        FavouriteAccommodationDTO accommodation2 = new FavouriteAccommodationDTO(2L, "Example accommodation 2", "Description 2", image2, 100.00, 5,new Address());
        FavouriteAccommodationDTO accommodation3 = new FavouriteAccommodationDTO(3L, "Example accommodation 3", "Description 3", image3, 85.50, 3.2F,new Address());
        ArrayList<FavouriteAccommodationDTO> accommodations = new ArrayList<>();
        accommodations.add(accommodation1);
        accommodations.add(accommodation2);
        accommodations.add(accommodation3);
        return accommodations;

    }

    @Override
    public ArrayList<AccommodationListingDTO> search(String startDate, String endDate, String location, int people) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start;
        Date end;
        try {
            start = dateFormat.parse(startDate);
            end = dateFormat.parse(endDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<Accommodation> accommodations = repository.searchAccommodations(location, people);
        ArrayList<AccommodationListingDTO> dtos = new ArrayList<>();
        for(Accommodation a : accommodations) {
            System.out.println("accommodation with id " + a.getId() + "is good by location and people");
            if(availabilityService.checkForDateRange(a.getId(), start, end)) {
                System.out.println("accommodation with id " + a.getId() + "is free in date range");
                dtos.add(AccommodationListingDTO.makeFromAccommodation(a));
            }
        }
        return dtos;
    }

    @Override
    public ArrayList<AccommodationListingDTO> findAllOwnersAccommodations(Long ownerId) {
        List<Accommodation> accommodations = repository.findAllForOwner(ownerId);
        ArrayList<AccommodationListingDTO> dtos = new ArrayList<>();
        for(Accommodation a : accommodations) {
            dtos.add(AccommodationListingDTO.makeFromAccommodation(a));
        }
        return dtos;
    }

    @Override
    public ArrayList<AccommodationListingDTO> applyFilters(ArrayList<AccommodationListingDTO> accommodations, Filter filter) throws IOException {
        //filtering amenities
        System.out.println(filter.getName());
        ArrayList<String> amenityNames = amenityService.getAllNames();
        System.out.println(accommodations.size() + " accommodations");
        if(amenityNames.contains(filter.getName())) {
            Iterator<AccommodationListingDTO> iterator = accommodations.iterator();
            while (iterator.hasNext()) {
                AccommodationListingDTO currentElement = iterator.next();
                ArrayList<String> amenities = amenityService.getAllAmenityNamesForAccommodation(currentElement.getId());
                if (!amenities.contains(filter.getName())) {
                    System.out.println("Amenity with id " + currentElement.getId() + " does not have " + filter.getName());
                    iterator.remove();
                }
            }
        }
        System.out.println("after filtering amenities: " + accommodations.size());

        //filtering accommodation types is not done here

        //filtering prices
        if(filter.getName().equals("minPrice")) {
            System.out.println("Price filtering - min");
            ObjectMapper mapper = new ObjectMapper();
            Iterator<AccommodationListingDTO> iterator1 = accommodations.iterator();
            while (iterator1.hasNext()) {
                AccommodationListingDTO currentElement = iterator1.next();
                PriceFilter price = (PriceFilter) mapper.convertValue(filter.getValue(), new TypeReference<PriceFilter>() { });
                if (currentElement.getTotalPrice() < price.price) {
                    iterator1.remove();
                }
            }
        }
        if(filter.getName().equals("maxPrice")) {
            System.out.println("Price filtering - max");
            ObjectMapper mapper = new ObjectMapper();
            Iterator<AccommodationListingDTO> iterator = accommodations.iterator();
            while (iterator.hasNext()) {
                AccommodationListingDTO currentElement = iterator.next();
                PriceFilter price =(PriceFilter) mapper.convertValue(filter.getValue(), new TypeReference<PriceFilter>() { });
                if (currentElement.getTotalPrice() > price.price) {
                    iterator.remove();
                }
            }
        }
        System.out.println("AFter filters: " + accommodations.size());
        return accommodations;
    }

    @Override
    public ArrayList<AccommodationListingDTO> filterTypes(ArrayList<AccommodationListingDTO> accommodations, ArrayList<AccommodationType> adequateTypes) {
        Iterator<AccommodationListingDTO> iterator = accommodations.iterator();
        while (iterator.hasNext()) {
            AccommodationListingDTO currentElement = iterator.next();
            Optional<Accommodation> accommodation = repository.findById(currentElement.getId());
            if(!accommodation.isEmpty()) {
                //if type of accommodation is not one of checked types, we remove it
                if (!adequateTypes.contains(accommodation.get().getType())) {
                    iterator.remove();
                }
            }
        }
        return accommodations;
    }

    @Override
    public double findPriceForDateRange(Long id, Date startDate, Date endDate, int numOfGuests) {
        double cost = priceService.findUnitPriceForDateRange(id, startDate, endDate);
        if(priceService.getAccommodationPriceType(id) == PriceType.PER_GUEST) {
            //if the price is per gust we multiply the price by number of guests
            cost *= numOfGuests;
        }
        return cost;
    }

    @Override
    public double findUnitPrice(Long id, Date startDate, Date endDate, int numOfGuests) {
        double cost = priceService.findUnitPriceForDateRange(id, startDate, endDate);
        return cost;
    }

    @Override
    public PriceType getAccommodationPriceType(Long accommodationId) {
        return priceService.getAccommodationPriceType(accommodationId);
    }

    @Override
    public Accommodation updateAvailability(Long accommodationId, UpdateAvailabilityDTO updateAvailabilityDTO) throws Exception{
        Accommodation accommodation = repository.findById(accommodationId).orElseGet(null);
        if (accommodation == null) {
            return null;
        }
        repository.save(accommodation);

        Availability availability = new Availability();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate = simpleDateFormat.parse(updateAvailabilityDTO.getStartDate());
//        Date endDate = simpleDateFormat.parse(updateAvailabilityDTO.getEndDate());
        List<Reservation> reservations = reservationRepository.findAllForAccommodation(accommodationId);
        List<Reservation> currentlyActive = reservationRepository.findCurrentlyActiveReservationsForAccommodation(accommodationId, updateAvailabilityDTO.getStartDate(), updateAvailabilityDTO.getEndDate());
        if (!currentlyActive.isEmpty()) {
            throw new RuntimeException("This accommodation has active reservations in this period!");
        }
        availability.setStartDate(updateAvailabilityDTO.getStartDate());
        availability.setEndDate(updateAvailabilityDTO.getEndDate());
        availability.setAccommodation(accommodation);
        ArrayList<Availability> availabilities = new ArrayList<Availability>();
        availabilities.add(availability);
        availabilityRepository.saveAll(availabilities);
        accommodation.setAvailabilities(availabilities);
        Price price = new Price();
        price.setCost(updateAvailabilityDTO.getPrice().getCost());
        price.setFromDate(updateAvailabilityDTO.getPrice().getFromDate());
        price.setToDate(updateAvailabilityDTO.getPrice().getToDate());
        price.setType(updateAvailabilityDTO.getPrice().getType());
        price.setAccommodation(accommodation);
        ArrayList<Price> prices = new ArrayList<Price>();
        prices.add(price);
        priceRepository.saveAll(prices);
        accommodation.setPrices(prices);
        accommodation.setDeadline(updateAvailabilityDTO.getDeadline());
        repository.save(accommodation);
        return accommodation;
    }

    @Override
    public void uploadAccommodationPictures(Long accommodationId, MultipartFile image) throws IOException {
        Accommodation accommodation = repository.findById(accommodationId).orElse(null);

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = StringUtils.cleanPath(imagesDirPathFront + accommodation.getId());
        System.out.println(uploadDir);

        // save to frontend folder
        ImageUploadUtil.saveImage(uploadDir, fileName, image);
        // save to mobile app folder
        ImageUploadUtil.saveImage(imagesDirPathMobile, fileName, image);

        repository.save(accommodation);
    }


    public Image convertMultipartFileToImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setPath_front(saveFile(file));

        return image;
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        String uploadDir = "images";

        // Create the directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Use File.separator to ensure correct file path on different operating systems
        String filePath = uploadDir + File.separator;

        file.transferTo(new File(filePath));

        return filePath;
    }

    @Override
    public void deleteImage(Long accommodationId, Long imageId) {
        imageRepository.deleteById(imageId);
    }


    @Override
    public void uploadImage(Long accommodationId, MultipartFile image) throws IOException {
        AccommodationViewDTO accommodation = findOne(accommodationId);
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = StringUtils.cleanPath(imagesDirPathFront + accommodation.getId());
        // save to frontend folder
        ImageUploadUtil.saveImage(uploadDir, fileName, image);
        // save to mobile app folder
        ImageUploadUtil.saveImage(imagesDirPathMobile, fileName, image);
        Image newImage = new Image();
        newImage.setPath_front("../../assets/images/accommodation" + accommodationId + "/" + fileName);
        newImage.setPath_mobile("../../../../../res/drawable/" + fileName);
        Optional<Accommodation> a = repository.findById(accommodationId);
        newImage.setAccommodation(a.get());
        imageRepository.save(newImage);
    }

    @Override
    public void updateAvailabilitiesForAccommodation(Long accommodationId, String startDate, String endDate) {
        try {
            Accommodation accommodation = repository.findById(accommodationId).get();
            List<Availability> availabilities = accommodation.getAvailabilities();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date from = sdf.parse(startDate);
            Date to = sdf.parse(endDate);
            for (Availability availability : availabilities){
                availabilityService.refactorAvailability(availability, from, to);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void returnAvailabilitiesForAccommodation(Long accommodationId, String startDate, String endDate) {
        try {
            Accommodation accommodation = repository.findById(accommodationId).get();
            List<Availability> availabilities = accommodation.getAvailabilities();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date from = sdf.parse(startDate);
            Date to = sdf.parse(endDate);
            availabilityService.returnAvailabilities(accommodationId, from, to);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public ArrayList<AccommodationNameDTO> getAccommodationNames(Long ownerId) {
        List<AccommodationListingDTO> accommodations = findOwnersActiveAccommodations(ownerId);
        ArrayList<AccommodationNameDTO> names = new ArrayList<>();
        for(AccommodationListingDTO accommodation:accommodations) {
            names.add(new AccommodationNameDTO(accommodation.getTitle(), accommodation.getId()));
        }
        return names;
    }

    @Override
    public Long getAccommodationId(String accName) {
        return repository.findIdByName(accName);
    };


}