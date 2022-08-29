package org.darius.service;

import org.darius.RestClient;
import org.darius.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.darius.castutils.CastUtils;

@Service
public class FlightServiceImpl implements FlightService{

    @Autowired
    private RestClient restClient;

    @Override
    public Optional<Flight> getFlightById(Long id) {
        ResponseEntity<Flight> flight = restClient.restExchangeBearer("http://FLIGHT-MICROSERVICE/api/flight/user/entity/" + id,
                HttpMethod.GET, Flight.class);
        return Optional.ofNullable(flight.getBody());

    }
    @Override
    public Long getNewSeatId(Long flightId){
        ResponseEntity<Long> seatId = restClient.restExchangeBearer("http://FLIGHT-MICROSERVICE/api/flight/user/seat/new/" + flightId, HttpMethod.POST, Long.class);
        return seatId.getBody();
    }

    @Override
    public List<Long> getAllFlightIds(){
        ResponseEntity<List> flightIdListResponseEntity =
                restClient.restExchangeBearer("http://FLIGHT-MICROSERVICE/api/flight/user/all/ids", HttpMethod.GET, List.class);

        return new CastUtils<Long>().castList(Objects.requireNonNull(flightIdListResponseEntity.getBody()), Long.class);
    }

    @Override
    public Long countSeatsByFlightId(Long flightId) {
        ResponseEntity<Long> seats = restClient.restExchangeBearer("http://FLIGHT-MICROSERVICE/api/flight/user/seat/count/" + flightId, HttpMethod.GET, Long.class);
        return seats.getBody();
    }

    @Override
    public List<Flight> findFlightsByIds(List<Long> flightIds) {
        ResponseEntity<List> flights = restClient.restExchangeBearer("http://FLIGHT-MICROSERVICE/api/flight/user/all/{flightIds}" + flightIds, HttpMethod.GET, List.class);
        List<Flight> flightList = flights.getBody();
        return flightList;
    }

}
