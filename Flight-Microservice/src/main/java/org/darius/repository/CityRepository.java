package org.darius.repository;

import org.darius.entity.flight.Flight;
import org.darius.entity.location.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {


    @Query("select flights from City c inner join c.departureCities flights where c.name like %?1%")
    List<Flight> findByDepartureCityLike(String flightName);

    @Query("select flights from City c inner join c.arrivalCities flights where c.name like %?1%")
    List<Flight> findByArrivalCityLike(String flightName);

}
