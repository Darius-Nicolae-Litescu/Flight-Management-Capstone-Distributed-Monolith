package org.darius.repository;

import org.darius.entity.flight.Flight;
import org.darius.entity.flight.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "select count(*) from Seat s where s.flight_id = ?1", nativeQuery = true)
    Long countSeatsByFlightId(Long flightId);

    List<Flight> findByFlightName(String flightName);

    List<Flight> findByFlightType(String flightType);

    @Query("select flight.flightId from Flight flight")
    List<Long> findAllIds();

    @Query(value = "select f.* from Flight f join City c on c.id = f.arrival_city_id or c.id = f.departure_city_id " +
            "where c.name = ?2 or c.name = ?3 " +
            "and f.flight_name = ?1 and f.flight_type = ?4", nativeQuery = true)
    List<Flight> findByFlightNameAndDepartureCityAndArrivalCityAndFlightType(String flightName, String arrivalCity,
                                                                             String departureCity, String flightType);

    @Query(value = "select f from Flight f where f.flightSchedule.scheduleId = ?1")
    Optional<Flight> findScheduleById(Long scheduleId);
}
