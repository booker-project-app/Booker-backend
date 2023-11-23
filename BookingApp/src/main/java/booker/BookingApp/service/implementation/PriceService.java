/*package booker.BookingApp.service.implementation;


import booker.BookingApp.model.accommodation.Price;
import booker.BookingApp.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public List<Price> findAll() {
        return priceRepository.findAll();
    }

    public Price findOne(Long id) {
        if (priceRepository.findOne(id) == null) {
            return null;
        }
        return priceRepository.findOne(id);
    }

    public Price create(Price price) {
        return priceRepository.create(price);
    }

    public Price update(Price price) {
        return priceRepository.update(price);
    }

    public void delete(Long id) {
        priceRepository.deleteById(id);
    }

    public Price save(Price price) {
        return priceRepository.save(price);
    }
}*/
