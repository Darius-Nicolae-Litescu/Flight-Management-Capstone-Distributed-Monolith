package org.darius.controller.admin;

import org.darius.controller.user.FlightUserOperationsController;
import org.darius.dto.request.insert.ScheduleInsertDTO;
import org.darius.dto.request.update.FlightUpdateDTO;
import org.darius.dto.request.insert.FlightInsertDTO;
import org.darius.dto.response.FlightResponseDTO;
import org.darius.dto.response.ScheduleResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.FlightService;
import org.darius.wrapper.FlightOperationWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/flight/admin")
public class FlightAdminOperationsController {
    private final  Logger logger = LoggerFactory.getLogger(FlightAdminOperationsController.class);
    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightOperationWrapper<FlightResponseDTO>> insertFlight(@RequestBody @Valid FlightInsertDTO flightInsertDTO) throws Exception {
        logger.info("Trying to add flight: {}", flightInsertDTO.getFlightName());
        FlightOperationWrapper<FlightResponseDTO> flightResponseDTOFlightOperationWrapper =
                flightService.addFlight(flightInsertDTO);
        Link link = linkTo(methodOn(FlightUserOperationsController.class)
                .getFlightBasedOnId(flightResponseDTOFlightOperationWrapper.getResult().getFlightNumber()))
                .withSelfRel();
        flightResponseDTOFlightOperationWrapper.getResult().add(link);
        return new ResponseEntity<>(flightResponseDTOFlightOperationWrapper, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FlightOperationWrapper<FlightResponseDTO>> updateFlight(@RequestBody @Valid  FlightUpdateDTO flightUpdateDTO) throws EntityNotFoundException {
        logger.info("Trying to update flight: {}", flightUpdateDTO.getFlightName());
        FlightOperationWrapper<FlightResponseDTO> flightResponseDTOFlightOperationWrapper =
                flightService.updateFlight(flightUpdateDTO);

        Link link = linkTo(methodOn(FlightUserOperationsController.class)
                .getFlightBasedOnId(flightResponseDTOFlightOperationWrapper.getResult().getFlightNumber()))
                .withSelfRel();
        flightResponseDTOFlightOperationWrapper.getResult().add(link);

        return new ResponseEntity<>(flightResponseDTOFlightOperationWrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FlightOperationWrapper<FlightResponseDTO>> deleteFlight(@PathVariable Long id) throws EntityNotFoundException {
        logger.info("Trying to delete flight with id: {}", id);
        FlightOperationWrapper<FlightResponseDTO> flightResponseDTOFlightOperationWrapper =
                flightService.deleteFlight(id);
        Link link = linkTo(methodOn(FlightUserOperationsController.class)
                .getFlightBasedOnId(flightResponseDTOFlightOperationWrapper.getResult().getFlightNumber()))
                .withSelfRel();
        flightResponseDTOFlightOperationWrapper.getResult().add(link);
        return new ResponseEntity<>(flightResponseDTOFlightOperationWrapper, HttpStatus.OK);
    }

    @PostMapping("/{id}/schedule")
    public ResponseEntity<ScheduleResponseDTO> addSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleInsertDTO scheduleInsertDTO) throws EntityNotFoundException {
        logger.info("Trying to add schedule {}" , scheduleInsertDTO);
        ScheduleResponseDTO scheduleResponseDTO =
                flightService.addSchedule(id, scheduleInsertDTO);
        return new ResponseEntity<>(scheduleResponseDTO, HttpStatus.OK);
    }

}