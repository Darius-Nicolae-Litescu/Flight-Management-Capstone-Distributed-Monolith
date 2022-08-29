package org.darius.service.builder;

import org.darius.entity.Passenger;
import org.darius.entity.PassengerDetails;

public class PassengerBuilder {
    private Long id;
    private Long userId;
    private PassengerDetails passengerDetails;

    public static PassengerBuilder random() {
        PassengerBuilder passengerBuilder = defaultValues();
        passengerBuilder.id = (long) (Math.random() * 100);
        passengerBuilder.userId = (long) (Math.random() * 100);
        passengerBuilder.passengerDetails = new PassengerDetailsBuilder().random().build();
        return passengerBuilder;
    }

    public static PassengerBuilder defaultValues() {
        PassengerBuilder passengerBuilder = new PassengerBuilder();
        return passengerBuilder;
    }

    public PassengerBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PassengerBuilder userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public PassengerBuilder passengerDetails(PassengerDetails passengerDetails) {
        this.passengerDetails = passengerDetails;
        return this;
    }

    public Passenger build() {
        return new Passenger(id, userId, passengerDetails);
    }
}
