package org.darius.configuration;

import org.darius.configuration.supplier.BookingSupplier;

import org.darius.entity.Booking;
import org.darius.repository.BookingRepository;
import org.darius.service.FlightService;
import org.darius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
public class MockGenerateSeatData {
    BookingSupplier bookingSupplier = BookingSupplier.supplier;

    private final BookingRepository bookingRepository;
    private final FlightService flightService;

    private final UserService userService;

    List<Long> userIds;

    @Autowired
    public MockGenerateSeatData(BookingRepository bookingRepository, FlightService flightService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.userService = userService;
        /*
        userIds = userService.getAllUserIds();
        addSeatDataForBookings();
         */
    }

    @Transactional
    private void addSeatDataForBookings() {
        if(bookingRepository.count() < 10) {
            for(Long id : flightService.getAllFlightIds()){
                Booking booking = bookingSupplier.bookingSupplier.get();
                booking.setSeatId(flightService.getNewSeatId(id));
                booking.setFlightId(id);
                booking.setUserId(getRandomUserIdFromList());
                bookingRepository.save(booking);

            }
        }
    }

    private Long getRandomUserIdFromList(){
        return userIds.get(new Random().nextInt(userIds.size()));
    }
}
