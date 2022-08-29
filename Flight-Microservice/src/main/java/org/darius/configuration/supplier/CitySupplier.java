package org.darius.configuration.supplier;

import org.darius.entity.location.City;

import java.util.Random;
import java.util.function.Supplier;

public class CitySupplier {
    public static CitySupplier supplier = new CitySupplier();
    private final Random random = new Random();

    public Supplier<City> citySupplier = () -> {
        City city = new City();
        city.setName("Some city: " + random.nextInt());
        return city;
    };
}
