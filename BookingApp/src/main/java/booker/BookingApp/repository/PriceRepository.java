package booker.BookingApp.repository;

import booker.BookingApp.enums.PriceType;
import booker.BookingApp.model.accommodation.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("select p from Price p where p.accommodation.id = ?1")
    public List<Price> findAllForAccommodation(Long accommodationId);
    @Query("select p from Price p where p.type = 0")
    public List<Price> findAllForTypeAccommodation();

    @Query("select p from Price p where p.type = 1")
    public List<Price> findAllForTypeGuest();

    @Query("select distinct p.type from Price p where p.accommodation.id = :accId")
    public PriceType getAccommodationPriceType(@Param("accId") Long accId);

    @Query(value="select p.cost from Accommodation a, Price p WHERE a.id=:accId AND p.accommodation.id=:accId AND :date BETWEEN p.fromDate AND p.toDate")
    public double findPriceForDate(@Param("accId") Long accId, @Param("date") Date date);
}
