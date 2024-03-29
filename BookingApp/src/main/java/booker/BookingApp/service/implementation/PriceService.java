package booker.BookingApp.service.implementation;


import booker.BookingApp.dto.accommodation.CreatePriceDTO;
import booker.BookingApp.dto.accommodation.PriceDTO;
import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.Price;
import booker.BookingApp.repository.PriceRepository;
import booker.BookingApp.service.interfaces.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PriceService implements IPriceService {
    @Autowired
    private PriceRepository priceRepository;
    @Override
    public List<Price> findAll() {
        return priceRepository.findAll();
    }
    @Override
    public Price findOne(Long id) {
        return priceRepository.findById(id).orElse(null);
    }
    @Override
    public void delete(Long id) {
        priceRepository.deleteById(id);
    }

    @Override
    public Price create(CreatePriceDTO createPriceDTO) {
        Price price = new Price();
        price.setFromDate(createPriceDTO.getFromDate());
        price.setToDate(createPriceDTO.getToDate());
        price.setCost(createPriceDTO.getCost());
        priceRepository.save(price);
        return price;
    }

    @Override
    public PriceDTO update(PriceDTO priceDTO) {
        return priceDTO;
    }

    @Override
    public List<Price> findAllForAccommodation(Long id) {
        return priceRepository.findAllForAccommodation(id);
    }

    @Override
    public List<Price> findAllForTypeAccommodation() {
        return priceRepository.findAllForTypeAccommodation();
    }

    @Override
    public List<Price> findAllForTypeGuest() {
        return priceRepository.findAllForTypeGuest();
    }


    @Override
    public double findUnitPriceForDateRange(Long accommodationId, Date fromDate, Date toDate) {
        double cost = 0;
        Date currentDate = fromDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        while(currentDate.before(toDate)) {
            cost += priceRepository.findPriceForDate(accommodationId, currentDate);
            //increment date by one -> get the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = calendar.getTime();
        }
        return cost;
    }

    @Override
    public PriceType getAccommodationPriceType(Long accommodationId) {
        return priceRepository.getAccommodationPriceType(accommodationId);
    }

    //@Override
    //public Price save(Price price) {
        //return priceRepository.save(price);
    //}
}
