package org.darius.configuration.supplier;

import org.darius.entity.Booking;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BookingSupplier {
    public static BookingSupplier supplier = new BookingSupplier();
    private final Random random = new Random();

    public Supplier<Booking> bookingSupplier = () -> {
      Booking booking = new Booking();
        LocalDate randomDate = LocalDate.now().plus(Period.ofDays((new Random().nextInt(365 * 70))));
        booking.setDateOfBooking(Date.from(randomDate.atStartOfDay().toInstant(ZoneOffset.UTC)));
        return booking;
    };
}
