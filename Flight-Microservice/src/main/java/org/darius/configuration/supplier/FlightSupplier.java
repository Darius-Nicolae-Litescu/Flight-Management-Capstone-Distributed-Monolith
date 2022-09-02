package org.darius.configuration.supplier;

import org.darius.configuration.utils.GenerateUtils;
import org.darius.entity.flight.Flight;
import org.darius.entity.location.City;
import org.darius.model.User;
import org.darius.function.QuadFunction;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Supplier;

public class FlightSupplier {
    public static FlightSupplier supplier = new FlightSupplier();
    private final Random random = new Random();

    public Supplier<Flight> flightSupplier = () -> {
        Flight flight = new Flight();
        String city1 = GenerateUtils.generateRandomString(Math.abs(random.nextInt()%20) + 2);
        String city2 = GenerateUtils.generateRandomString(Math.abs(random.nextInt()%20) + 2);
        flight.setFlightName(city1 + " to " + city2);
        flight.setFlightType(Math.abs(random.nextInt()%3) == 1 ? "Domestic" : "International");
        flight.setFlightSeatCapacity(Math.abs(random.nextInt() % 200));
        return flight;
    };

    public QuadFunction<Flight, City, City, User, Flight> flightQuadFunction =
            (flight, departureCity, arrivalCity, user) -> {
        LocalDate randomDate = LocalDate.now().plus(Period.ofDays((new Random().nextInt(365 * 70))));
        departureCity.setDepartureFlights(new HashSet<>());
        departureCity.getDepartureFlights().add(flight);
        arrivalCity.setArrivalFlights(new HashSet<>());
        arrivalCity.getArrivalFlights().add(flight);
        return flight;
    };
}
