package org.darius.configuration;

import org.darius.configuration.supplier.CitySupplier;
import org.darius.configuration.supplier.CountrySupplier;
import org.darius.configuration.supplier.FlightSupplier;
import org.darius.entity.flight.Flight;
import org.darius.entity.location.City;
import org.darius.entity.location.Country;
import org.darius.model.User;
import org.darius.repository.CityRepository;
import org.darius.repository.CountryRepository;
import org.darius.repository.FlightRepository;
import org.darius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MockGenerateFlightData{
    FlightSupplier flightSupplier = FlightSupplier.supplier;
    CitySupplier citySupplier = CitySupplier.supplier;
    CountrySupplier countrySupplier = CountrySupplier.supplier;

    private final FlightRepository flightRepository;

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    private final UserService userService;


    private List<City> cities = new ArrayList<>();

    @Autowired
    public MockGenerateFlightData(FlightRepository flightRepository, UserService userService, CountryRepository countryRepository, CityRepository cityRepository) {
        this.flightRepository = flightRepository;
        this.userService = userService;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        //insertCountryAndCityData();
    }

    @Transactional
    private void insertCountryAndCityData() {
        if(countryRepository.count() < 10) {
            Arrays.stream(new int[200]).forEach(number -> {
                Country country = countrySupplier.countrySupplier.get();
                List<City> newCities = Arrays.stream(new int[12]).mapToObj(n -> citySupplier.citySupplier.get()).collect(Collectors.toList());
                country.setCities(newCities);
                countryRepository.save(country);
                this.cities.addAll(newCities);
            });
            insertFlightData();
        }
    }
    @Transactional
    private void insertFlightData() {
        if(flightRepository.count() < 10) {
            Arrays.stream(new int[4]).forEach(user -> {
                Arrays.stream(new int[3]).forEach(no -> {
                    Flight flight = flightSupplier.flightSupplier.get();
                    City departureCity = getRandomCity();
                    City arrivalCity = getRandomCity();
                    cityRepository.save(departureCity);
                    cityRepository.save(arrivalCity);
                    flightSupplier.flightQuadFunction.apply(flight, departureCity, arrivalCity, null);
                    flightRepository.save(flight);
                });
            });
        }
    }

    private City getRandomCity() {
        if (cities.size() > 0) {
            int randomInt = new Random().nextInt(cities.size() - 1);
            return cities.get(randomInt);
        }
        return null;
    }
}
