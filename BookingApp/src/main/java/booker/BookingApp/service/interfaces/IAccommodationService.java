package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.*;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.enums.AccommodationType;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.Address;
import booker.BookingApp.model.accommodation.Filter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface IAccommodationService {

    ArrayList<AccommodationListingDTO> findAll() throws IOException;

    AccommodationViewDTO findOne(Long id) throws IOException;

    AccommodationViewDTO create(CreateAccommodationDTO accommodation) throws Exception;

    void update(AccommodationViewDTO accommodation, UpdateAccommodationDTO updateAccommodation) throws Exception;

    void updateAddress(Address existingAddr, AddressDTO updatedAddressDTO);

    void delete(Long id);

    ArrayList<AccommodationListingDTO> findOwnersActiveAccommodations(Long ownerId);

    ArrayList<AccommodationListingDTO> findAllUnapprovedAccommodations();

    void approve(Long accommodationId);

    ArrayList<FavouriteAccommodationDTO> findGuestsFavouriteAccommodations(Long guestId) throws IOException;

    ArrayList<AccommodationListingDTO> search(String startDate, String endDate, String location, int people);

    ArrayList<AccommodationListingDTO> findAllOwnersAccommodations(Long ownerId);


    void uploadAccommodationPictures(Long accommodationId, MultipartFile image) throws IOException;

    ArrayList<AccommodationListingDTO> applyFilters(ArrayList<AccommodationListingDTO> accommodations, Filter filter) throws IOException;

    ArrayList<AccommodationListingDTO> filterTypes(ArrayList<AccommodationListingDTO> accommodations, ArrayList<AccommodationType> adequateTypes);

    double findPriceForDateRange(Long id, Date startDate, Date endDate, int numOfGuests);

    double findUnitPrice(Long id, Date startDate, Date endDate, int numOfGuests);

    PriceType getAccommodationPriceType(Long accommodationId);

    public Accommodation updateAvailability(Long accommodationId, UpdateAvailabilityDTO updateAvailabilityDTO) throws Exception;
    void deleteImage(Long accommodationId, Long imageId);

    void uploadImage(Long accommodationId, MultipartFile image) throws IOException;

    void updateAvailabilitiesForAccommodation(Long accommodationId, String startDate, String endDate);

    void returnAvailabilitiesForAccommodation(Long accommodationId, String startDate, String endDate);

    ArrayList<AccommodationNameDTO> getAccommodationNames(Long ownerId);

    Long getAccommodationId(String accName);
}
