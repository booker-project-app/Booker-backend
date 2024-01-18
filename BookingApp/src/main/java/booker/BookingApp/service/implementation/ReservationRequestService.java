package booker.BookingApp.service.implementation;

import booker.BookingApp.dto.accommodation.AccommodationViewDTO;
import booker.BookingApp.dto.requestsAndReservations.ReservationRequestDTO;
import booker.BookingApp.enums.ReservationRequestStatus;
import booker.BookingApp.model.accommodation.Accommodation;
import booker.BookingApp.model.requestsAndReservations.ReservationRequest;
import booker.BookingApp.repository.AccommodationRepository;
import booker.BookingApp.repository.ReservationRequestRepository;
import booker.BookingApp.service.interfaces.IReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationRequestService implements IReservationRequestService {
    @Autowired
    ReservationRequestRepository repository;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    AvailabilityService availabilityService;

    @Override
    public ReservationRequestDTO create(ReservationRequestDTO requestDto) {
        ReservationRequest request = ReservationRequestDTO.makeRequestFromDTO(requestDto);
        request.setAccommodationId(request.getAccommodationId());
        // if accommodation has automatically accepting reservation requests option, then
        // create reservation and change reservation request status
        System.out.println("MILA PRVI PUT " + requestDto.getAccommodationId());
        if (!checkReservationAcceptingType(request.getAccommodationId()) &&
                !checkAvailability(request.getAccommodationId(), request.getFromDate(), request.getToDate())){
            request.setStatus(ReservationRequestStatus.ACCEPTED);
            requestDto.setStatus(ReservationRequestStatus.ACCEPTED);
            reservationService.create(requestDto);
        }
        //if accommodation is available for time slot
        if(!checkAvailability(request.getAccommodationId(), request.getFromDate(), request.getToDate())) {
            System.out.println("NIJE PROSAO VALIDACIJE ZA AVAILABILITY");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date from = sdf.parse(request.getFromDate());
            Date to = sdf.parse(request.getToDate());
            //dates have to be valid (not in the past)
            if(!from.after(new Date()) || !to.after(from)) {
                System.out.println("NIJE PROSAO VALIDACIJE ZA DATUME");
                return null;
            }
            //price has to be adequate
            if (request.getPrice() != accommodationService.findPriceForDateRange(request.getAccommodationId(), sdf.parse(request.getFromDate()), sdf.parse(request.getToDate()), requestDto.getNumberOfGuests())) {
                System.out.println("NIJE PROSAO VALIDACIJE ZA CENE");
                System.out.println(request.getPrice());
                System.out.println(accommodationService.findPriceForDateRange(request.getAccommodationId(), sdf.parse(request.getFromDate()), sdf.parse(request.getToDate()), requestDto.getNumberOfGuests()));
                return null;
            }

            AccommodationViewDTO accommodation = accommodationService.findOne(requestDto.getAccommodationId());
            //and number of guests has to be between min and max capacity
            if(request.getNumberOfGuests() < accommodation.getMin_capacity() || request.getNumberOfGuests() > accommodation.getMax_capacity()) {
                System.out.println("NIJE PROSAO VALIDACIJE ZA BROJ GOSTIJU");
                return null;
            }

            //validation passed
            System.out.println("Prosao validacije");
            this.repository.save(request);
            return ReservationRequestDTO.makeFromRequest(request);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkAvailability(Long accommodationId, String fromDate, String toDate){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date from = sdf.parse(fromDate);
            Date to = sdf.parse(toDate);
            return availabilityService.checkIfAvailable(accommodationId, from, to);
        } catch (ParseException e){
            System.out.println("Can not parse date");
        }
        return false;
    }

    @Override
    public boolean checkReservationAcceptingType(Long accommodationId) {
        try{
            System.out.println("MILA " + accommodationId);
            AccommodationViewDTO accommodation = accommodationService.findOne(accommodationId);
            if (accommodation.isManual_accepting()){
                return true;
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
        return false;
    }

    @Override
    public ReservationRequestDTO findOne(Long id) {
        ReservationRequest request = repository.findById(id).get();
        return ReservationRequestDTO.makeFromRequest(request);
    }

    @Override
    public ArrayList<ReservationRequestDTO> findOwnersRequests(Long ownerId) {
        ArrayList<ReservationRequestDTO> requestDTOS = new ArrayList<>();
        /*requestDTOS.add(findOne(1L));
        requestDTOS.add(findOne(2L));
        requestDTOS.add(findOne(3L));*/
        List<Long> accommodationIds = new ArrayList<>();
        List<Accommodation> accommodations = accommodationRepository.findSpecifiedForOwner(ownerId, true);
        for (Accommodation a : accommodations){
            accommodationIds.add(a.getId());
        }
        List<ReservationRequest> requests = repository.findAllForOwner(accommodationIds);
        for (ReservationRequest r : requests) {
            Accommodation acc = accommodationRepository.findById(r.getAccommodationId()).get();
            if(!r.isDeleted() && acc.isManual_accepting()) {        //when guest deletes request before owner sees it
                requestDTOS.add(ReservationRequestDTO.makeFromRequest(r));
            }
        }
        return requestDTOS;
    }

    @Override
    public ArrayList<ReservationRequestDTO> findAccommodationRequests(Long accommodationId){
        ArrayList<ReservationRequestDTO> requestDTOS = new ArrayList<>();
        List<ReservationRequest> requests = repository.findAllForAccommodation(accommodationId);
        for (ReservationRequest r : requests) {
            requestDTOS.add(ReservationRequestDTO.makeFromRequest(r));
        }
        return requestDTOS;
    }

    @Override
    public ArrayList<ReservationRequestDTO> search(Long guestId, String dateString, String name) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ReservationRequestDTO> requests = this.findGuestsRequests(guestId);
        ArrayList<ReservationRequestDTO> adequateRequests = new ArrayList<>();
        for(ReservationRequestDTO r : requests) {
            AccommodationViewDTO accommodation = accommodationService.findOne(r.getAccommodationId());
            Date startDate, endDate;
            try {
                startDate = dateFormat.parse(r.getFromDate());
                endDate = dateFormat.parse(r.getToDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(!name.equals("noNameSearching")) {
                if(!dateString.equals("1111-01-01")) {  ///by name and date
                    System.out.println("searching by name and date");
                    if (accommodation.getTitle().contains(name) && date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                        adequateRequests.add(r);
                    }
                } else {    //just by name
                    System.out.println("searching by name");
                    if(accommodation.getTitle().contains(name)) {
                        adequateRequests.add(r);
                    }
                }
            } else {    //just by date
                System.out.println("searching by date");
                if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                    adequateRequests.add(r);
                }
            }
        }
        return adequateRequests;
    }

    @Override
    public ArrayList<ReservationRequestDTO> searchForOwner(Long ownerId, String dateString, String name) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ReservationRequestDTO> requests = this.findOwnersRequests(ownerId);
        ArrayList<ReservationRequestDTO> adequateRequests = new ArrayList<>();
        for(ReservationRequestDTO r : requests) {
            AccommodationViewDTO accommodation = accommodationService.findOne(r.getAccommodationId());
            Date startDate, endDate;
            try {
                startDate = dateFormat.parse(r.getFromDate());
                endDate = dateFormat.parse(r.getToDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(!name.equals("noNameSearching")) {
                if(!dateString.equals("1111-01-01")) {  ///by name and date
                    System.out.println("searching by name and date");
                    if (accommodation.getTitle().contains(name) && date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                        adequateRequests.add(r);
                    }
                } else {    //just by name
                    System.out.println("searching by name");
                    if(accommodation.getTitle().contains(name)) {
                        adequateRequests.add(r);
                    }
                }
            } else {    //just by date
                System.out.println("searching by date");
                if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0) {
                    adequateRequests.add(r);
                }
            }
        }
        return adequateRequests;
    }

    @Override
    public ArrayList<ReservationRequestDTO> applyFilters(ArrayList<ReservationRequestDTO> requests, ArrayList<ReservationRequestStatus> adequateTypes) {
        Iterator<ReservationRequestDTO> iterator = requests.iterator();
        while (iterator.hasNext()) {
            ReservationRequestDTO currentElement = iterator.next();
                //if status of request is not one of checked statuses, we remove it
                if (!adequateTypes.contains(currentElement.getStatus())) {
                    iterator.remove();
                }
        }
        return requests;
    }

    @Override
    public ArrayList<ReservationRequestDTO> findGuestsRequests(Long guestId) {
        ArrayList<ReservationRequest> requests = (ArrayList<ReservationRequest>) repository.findAllForGuest(guestId);
        ArrayList<ReservationRequestDTO> dtos = new ArrayList<>();
        for(ReservationRequest request : requests) {
            if(!request.isDeleted()) {          //when guest deletes request before owner sees it
                dtos.add(ReservationRequestDTO.makeFromRequest(request));
            }
        }
        return dtos;
    }

    @Override
    public void cancelRequest(Long userId, Long requestId) {
        ReservationRequest request = repository.findById(requestId).get();
        request.setDeleted(true);
        repository.save(request);
    }

    @Override
    public boolean acceptOrDecline(boolean accept, ReservationRequestDTO reservationRequestDTO) {
        if (accept) {
            if (checkAvailability(reservationRequestDTO.getAccommodationId(), reservationRequestDTO.getFromDate(),
                    reservationRequestDTO.getToDate())){
                reservationRequestDTO.setStatus(ReservationRequestStatus.ACCEPTED);
                reservationService.create(reservationRequestDTO);
                declineOthers(reservationRequestDTO);
                ReservationRequest request = new ReservationRequest(reservationRequestDTO.getId(),
                        reservationRequestDTO.getGuestId(), reservationRequestDTO.getAccommodationId(), reservationRequestDTO.getFromDate(),
                        reservationRequestDTO.getToDate(), reservationRequestDTO.getNumberOfGuests(), reservationRequestDTO.getStatus(),
                        reservationRequestDTO.isDeleted(), reservationRequestDTO.getPrice());
                repository.save(request);
                return true;
            }
        }
        else{
            reservationRequestDTO.setStatus(ReservationRequestStatus.DENIED);
            ReservationRequest request = new ReservationRequest(reservationRequestDTO.getId(),
                    reservationRequestDTO.getGuestId(), reservationRequestDTO.getAccommodationId(), reservationRequestDTO.getFromDate(),
                    reservationRequestDTO.getToDate(), reservationRequestDTO.getNumberOfGuests(), reservationRequestDTO.getStatus(),
                    reservationRequestDTO.isDeleted(), reservationRequestDTO.getPrice());
            repository.save(request);
            return true;

        }
        return false;
    }

    @Override
    public void declineOthers(ReservationRequestDTO acceptedRequest) {
        try {
            ArrayList<ReservationRequestDTO> all = findAccommodationRequests(acceptedRequest.getAccommodationId());
            for (ReservationRequestDTO requestDTO : all){
                // if request is not accepted and request and accepted have overlap
                if (!requestDTO.equals(acceptedRequest) && availabilityService.checkForOverlaps(requestDTO, acceptedRequest)){
                    requestDTO.setStatus(ReservationRequestStatus.DENIED);
                    ReservationRequest request = new ReservationRequest(requestDTO.getId(),
                            requestDTO.getGuestId(), requestDTO.getAccommodationId(), requestDTO.getFromDate(),
                            requestDTO.getToDate(), requestDTO.getNumberOfGuests(), requestDTO.getStatus(),
                            requestDTO.isDeleted(), requestDTO.getPrice());
                    repository.save(request);
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
