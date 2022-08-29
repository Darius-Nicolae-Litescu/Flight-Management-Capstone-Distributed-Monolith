package org.darius.service.builder;

import org.darius.entity.Booking;

import javax.persistence.Column;
import java.util.Date;

public class BookingBuilder {
    private Long bookingId;
    private Date dateOfBooking;
    private Long seatId;
    private Long flightId;
    private Long userId;

    public static BookingBuilder defaultValues() {
        BookingBuilder bookingBuilder = new BookingBuilder();
        return new BookingBuilder();
    }

    public static BookingBuilder random() {
        BookingBuilder bookingBuilder = defaultValues();
        bookingBuilder.bookingId = (long) (Math.random() * 10);
        bookingBuilder.dateOfBooking = new Date();
        bookingBuilder.seatId = (long) (Math.random() * 10);
        bookingBuilder.flightId = (long) (Math.random() * 10);
        bookingBuilder.userId = (long) (Math.random() * 10);
        return bookingBuilder;
    }

    public BookingBuilder setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public BookingBuilder setDateOfBooking(Date dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
        return this;
    }

    public BookingBuilder setSeatId(Long seatId) {
        this.seatId = seatId;
        return this;
    }

    public BookingBuilder setFlightId(Long flightId) {
        this.flightId = flightId;
        return this;
    }

    public BookingBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Booking build() {
        return new Booking(bookingId, dateOfBooking, seatId, flightId, userId);
    }
}
