package org.darius.service;

import org.darius.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService{
    private final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    @Autowired
    private RestClient restClient;

    @Override
    public Boolean checkIfFlightExists(Long flightNumber) {
        logger.info("checkIfFlightExists: {}", flightNumber);
        ResponseEntity<Boolean> response = restClient.restExchangeBearer(
                "http://FLIGHT-MICROSERVICE/api/flight/user/check/" + flightNumber, HttpMethod.GET, Boolean.class);
        return response.getBody();
    }

}
