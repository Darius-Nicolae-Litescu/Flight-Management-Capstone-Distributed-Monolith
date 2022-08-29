package org.darius.service.builder;

import org.darius.model.Flight;
import org.darius.model.Schedule;

import java.time.LocalTime;

public class FlightBuilder {
    private Long flightId;
    private String flightName;
    private String flightType;
    private Integer flightSeatCapacity;
    private Schedule flightSchedule;

    public static FlightBuilder defaultValues() {
        FlightBuilder flightBuilder = new FlightBuilder();
        return new FlightBuilder();
    }

    public static FlightBuilder random() {
        FlightBuilder flightBuilder = defaultValues();
        flightBuilder.flightId = (long) (Math.random() * 10);
        flightBuilder.flightName = "flightName";
        flightBuilder.flightType = "flightType";
        flightBuilder.flightSeatCapacity = Math.abs((int) (Math.random() * 10));
        flightBuilder.flightSchedule = new Schedule((long) (Math.random() * 10), LocalTime.now(), LocalTime.now(), "stop");
        return flightBuilder;
    }

    public FlightBuilder setFlightId(Long flightId) {
        this.flightId = flightId;
        return this;
    }

    public FlightBuilder setFlightName(String flightName) {
        this.flightName = flightName;
        return this;
    }

    public FlightBuilder setFlightType(String flightType) {
        this.flightType = flightType;
        return this;
    }

    public FlightBuilder setFlightSeatCapacity(Integer flightSeatCapacity) {
        this.flightSeatCapacity = flightSeatCapacity;
        return this;
    }

    public FlightBuilder setFlightSchedule(Schedule flightSchedule) {
        this.flightSchedule = flightSchedule;
        return this;
    }

    public Flight build() {
        return new Flight(flightId, flightName, flightType, flightSeatCapacity, flightSchedule);
    }

}
