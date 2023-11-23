/*package booker.BookingApp.controller;

import booker.BookingApp.dto.PriceDTO;
import booker.BookingApp.model.accommodation.Price;
import booker.BookingApp.service.implementation.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<PriceDTO>> findAll() {
        List<Price> prices = priceService.findAll();
        List<PriceDTO> pricesDTO = new ArrayList<>();

        for (Price p : prices) {
            pricesDTO.add(new PriceDTO(p));
        }

        return new ResponseEntity<>(pricesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PriceDTO> findOne(@PathVariable Long id) {
        Price price = priceService.findOne(id);

        if (price == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(new PriceDTO(price), HttpStatus.OK);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<PriceDTO> savePrice(@RequestBody PriceDTO priceDTO) {
        Price price = new Price();

        price.setAccommodationId(priceDTO.getAccommodationId());
        price.setCost(priceDTO.getCost());
        price.setFromDate(priceDTO.getFromDate());
        price.setToDate(priceDTO.getToDate());
        price.setType(priceDTO.getPriceType());

        price = priceService.save(price);
        return new ResponseEntity<>(new PriceDTO(price), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<PriceDTO> updatePrice(@RequestBody PriceDTO priceDTO) {
        Price price = priceService.findOne(priceDTO.getId());

        if (price == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        price.setAccommodationId(priceDTO.getAccommodationId());
        price.setCost(priceDTO.getCost());
        price.setFromDate(priceDTO.getFromDate());
        price.setToDate(priceDTO.getToDate());
        price.setType(priceDTO.getPriceType());

        price = priceService.save(price);
        return new ResponseEntity<>(new PriceDTO(price), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {
        Price price = priceService.findOne(id);

        if (price != null) {
            priceService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}*/
