package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.accommodation.AvailabilityDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.accommodation.Availability;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.AvailabilityRepository;
import booker.BookingApp.service.interfaces.IAvailabilityService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityService implements IAvailabilityService {

    @Autowired
    AvailabilityRepository repository;
    @Autowired
    AccommodationRepository accommodationRepository;

    @Override
    public Availability create(Long accommodationId, AvailabilityDTO availabilityDTO) {
        Availability availability = new Availability();
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseGet(null);
        if (accommodation == null) {
            return null;
        }
        availability.setAccommodation(accommodation);
        availability.setStartDate(availabilityDTO.getStartDate());
        availability.setEndDate(availabilityDTO.getStartDate());
        repository.save(availability);
        return availability;
    }

    @Override
    public AvailabilityDTO update(Long accommodationId, AvailabilityDTO availabilityDTO) {

        return availabilityDTO;
    }

    @Override
    public ArrayList<AvailabilityDTO> findAllFreeInRange(Long accommodationId,
                                                         String startDate,
                                                         String endDate) throws IOException {
        ArrayList<AvailabilityDTO> availabilities = new ArrayList<>();
        availabilities.add(new AvailabilityDTO(new Date(), new Date()));
        availabilities.add(new AvailabilityDTO(new Date(), new Date()));
        availabilities.add(new AvailabilityDTO(new Date(), new Date()));
        availabilities.add(new AvailabilityDTO(new Date(), new Date()));
        return availabilities;
    }

    @Override
    public boolean checkForDate(Long accommodationId, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date1 = sdf.parse(date);
        boolean freeForRange = repository.checkForDate(accommodationId, date1) != null;
        System.out.println(date + " is " + freeForRange);
        //if it finds something than it's free for that day and should return true
        return freeForRange;
    }

    @Override
    public boolean checkForDateRange(Long accommodationId, Date startDate, Date endDate) {
        //if empty than it's not free in range and should return false
        return !repository.checkForDateRange(accommodationId, startDate, endDate).isEmpty();
    }

    @Override
    public boolean checkIfAvailable(Long accommodationId, Date startDate, Date endDate) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).get();
        List<Availability> availabilities = accommodation.getAvailabilities();
        for (Availability availability : availabilities){
            if ((availability.getStartDate().equals(startDate) || availability.getStartDate().before(startDate))
                    && (availability.getEndDate().equals(endDate) || availability.getEndDate().after(endDate))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void markAsNotAvailable(Long accommodationId, String startDate, String endDate) {

    }

    public Date daysCalculator(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    @Override
    public void refactorAvailability(Availability availability, Date fromDate, Date toDate) {
        Instant availabilityStart = availability.getStartDate().toInstant();
        Instant availabilityEnd = availability.getEndDate().toInstant();
        Instant startDate = fromDate.toInstant();
        Instant endDate = toDate.toInstant();
        if (availabilityStart.equals(startDate) && availabilityEnd.equals(endDate)) {
            // obrisi ceo availability
            repository.delete(availability);
        } else if (availabilityStart.equals(startDate) && availabilityEnd.isAfter(endDate)) {
            // izmeni availability tako da pocinje sa endDate a zavrsava se isto kao pre
            availability.setStartDate(toDate);
            repository.save(availability);
        } else if (availabilityStart.isBefore(startDate) && availabilityEnd.equals(endDate)) {
            // izmeni da pocinje sa istim a zavrsava se sa startDate-1
            availability.setEndDate(daysCalculator(fromDate, -1));
            repository.save(availability);
        } else if (availabilityStart.isBefore(startDate) && availabilityEnd.isAfter(endDate)) {
            // trenutni izmnei da pocne sa istim a zavrsava se sa startDate-1 i napravi novi koji
            // pocne sa endDate a zavrsi se sa istim
            Date oldEndDate = availability.getEndDate();
            availability.setEndDate(daysCalculator(fromDate, -1));
            repository.save(availability);
            Availability newAvailability = new Availability();
            newAvailability.setStartDate(toDate);
            newAvailability.setEndDate(oldEndDate);
            newAvailability.setAccommodation(availability.getAccommodation());
            repository.save(newAvailability);
        }

    }

    @Override
    public boolean checkForOverlaps(ReservationRequestDTO requestDTO, ReservationRequestDTO acceptedRequest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startA = sdf.parse(requestDTO.getFromDate());
            Date endA = sdf.parse(requestDTO.getToDate());
            Date startB = sdf.parse(acceptedRequest.getFromDate());
            Date endB = sdf.parse(acceptedRequest.getToDate());
            // return true if overlap
            return (endA.after(startB) && endA.before(endB)) || (startA.after(startB) && startA.before(endB));
        } catch (ParseException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void returnAvailabilities(Long accommodationId, Date startDate, Date endDate) {
        List<Availability> allAvailabilities = repository.findByAccommodationId(accommodationId);
        // sve dostupnosti koje su povezane sa datumima rezervacije koja se otkazuje
        List<Availability> modifiedAvailabilities = allAvailabilities.stream()
                .filter(availability -> (daysCalculator(startDate, -1).equals(availability.getEndDate()) ||
                        endDate.equals(availability.getStartDate())))
                .collect(Collectors.toList());
        System.out.println("modifikovano");
        System.out.println(modifiedAvailabilities);
        if (modifiedAvailabilities.isEmpty()){
            // dodavanje nezavisne dostupnosti
            Availability availability = new Availability();
            availability.setAccommodation(accommodationRepository.findById(accommodationId).get());
            availability.setStartDate(startDate);
            availability.setEndDate(endDate);
            repository.save(availability);
        } else if (modifiedAvailabilities.size() == 1) {
            // spajanje na pocetku ili na kraju
            if (daysCalculator(startDate, -1).equals(modifiedAvailabilities.get(0).getEndDate())){
                // ako se poklapa pocetni datum rezervacije sa krajnjim datumom dostupnosti
                // postavi se krajni datum rezervacije za krajnji datum dostupnosti
                Availability availability = modifiedAvailabilities.get(0);
                availability.setEndDate(endDate);
                repository.save(availability);
            } else if (endDate.equals(modifiedAvailabilities.get(0).getStartDate())) {
                // ako se poklapa krajnji datum rezervacije sa pocetnim datumom dostupnosti
                // postavi se pocetni datum rezervacije za pocetni datum dostupnosti
                Availability availability = modifiedAvailabilities.get(0);
                availability.setStartDate(startDate);
                repository.save(availability);
            }
        } else if (modifiedAvailabilities.size() == 2) {
            // spajanje dve dostupnosti u jednu
            Availability first;
            Availability second;
            if (modifiedAvailabilities.get(0).getStartDate().before(modifiedAvailabilities.get(1).getStartDate())){
                first = modifiedAvailabilities.get(0);
                second = modifiedAvailabilities.get(1);
            } else {
                first = modifiedAvailabilities.get(1);
                second = modifiedAvailabilities.get(0);
            }
            // krajnji datum kasnije dostupnosti postaje krajnji datum prve tj jedine dostupnosti a ta kasnija se brise
            first.setEndDate(second.getEndDate());
            repository.save(first);
            repository.delete(second);
        }
    }

}
