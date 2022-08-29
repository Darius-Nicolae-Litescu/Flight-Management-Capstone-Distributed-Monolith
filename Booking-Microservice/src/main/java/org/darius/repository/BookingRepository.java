package org.darius.repository;

import org.darius.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "select b.* from User u inner join Booking b on b.user_id = u.user_id where u.user_id = ?1", nativeQuery = true)
    Optional<Booking> findBookingByUserId(Long userId);

    @Query("Select b from Booking b where b.userId = ?1")
    List<Booking> bookingListByUserId(Long userId);

    @Query("Select b from Booking b where b.flightId = ?1")
    List<Booking> bookingListByFlightId(Long flightId);
    @Query("Select b.flightId from Booking b where b.bookingId = ?1")
    List<Long> flightIdsByBookingId(Long bookingId);

    @Query("Select b.userId from Booking b where b.bookingId = ?1")
    Long findUserIdByBookingId(Long bookingId);
}
