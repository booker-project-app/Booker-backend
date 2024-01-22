package booker.BookingApp.service.interfaces;

import booker.BookingApp.dto.accommodation.CreatePriceDTO;
import booker.BookingApp.dto.accommodation.PriceDTO;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.Price;

import java.util.Date;
import java.util.List;

public interface IPriceService {
    List<Price> findAll();
    Price findOne(Long id);
    void delete(Long id);
    //public Price save(Price price);
    Price create(CreatePriceDTO createPriceDTO);
    PriceDTO update(PriceDTO priceDTO);
    List<Price> findAllForAccommodation(Long id);
    List<Price> findAllForTypeAccommodation();
    List<Price> findAllForTypeGuest();
    double findUnitPriceForDateRange(Long accommodationId, Date fromDate, Date toDate);

    PriceType getAccommodationPriceType(Long accommodationId);
}
