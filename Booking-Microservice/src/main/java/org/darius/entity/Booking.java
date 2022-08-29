package org.darius.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="booking_id")
    private Long bookingId;

    @Column(name="date_of_booking")
    private Date dateOfBooking;

    @Column(name="seat_id")
    private Long seatId;

    @Column(name="flight_id")
    private Long flightId;

    @Column(name="user_id")
    private Long userId;

    public Booking(Date from) {
        this.dateOfBooking = from;
    }
}
