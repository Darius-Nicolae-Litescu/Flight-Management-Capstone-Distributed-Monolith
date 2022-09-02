package org.darius.mapper;

import org.darius.dto.request.insert.ScheduleInsertDTO;
import org.darius.dto.request.insert.ScheduleUpdateDTO;
import org.darius.dto.request.update.FlightUpdateDTO;
import org.darius.dto.request.insert.FlightInsertDTO;
import org.darius.dto.response.FlightResponseDTO;
import org.darius.dto.response.ScheduleResponseDTO;
import org.darius.entity.flight.Flight;
import org.darius.entity.flight.Schedule;
import org.darius.entity.location.City;

public class FlightMapper {

	public static Flight flightUpdateDTOToFlight(FlightUpdateDTO flightUpdateDTO) {
		Flight flight = new Flight();
		flight.setFlightId(flightUpdateDTO.getId());
		flight.setFlightName(flightUpdateDTO.getFlightName());
		flight.setFlightType(flightUpdateDTO.getFlightType());
		return flight;
	}

	public static Flight flightInsertDTOToFlight(FlightInsertDTO flightInsertDTO) {
		Flight flight = new Flight();
		flight.setFlightName(flightInsertDTO.getFlightName());
		flight.setFlightType(flightInsertDTO.getFlightType());
		flight.setFlightSeatCapacity(flightInsertDTO.getSeatCapacity());
		return flight;
	}

	public static FlightResponseDTO flightToFlightResponseDTO(Flight flight) {
		if(flight == null){
			return null;
		}
		FlightResponseDTO flightResponseDTO = new FlightResponseDTO();
		flightResponseDTO.setFlightName(flight.getFlightName());
		flightResponseDTO.setFlightNumber(flight.getFlightId());
		ScheduleResponseDTO scheduleResponseDTO = scheduleToScheduleResponseDTO(flight.getFlightSchedule(), flight.getFlightId());
		flightResponseDTO.setScheduleResponseDTO(scheduleResponseDTO);
		return flightResponseDTO;
	}

    public static Schedule scheduleInsertDTOToSchedule(ScheduleInsertDTO scheduleInsertDTO) {
		Schedule schedule = new Schedule();
		schedule.setDepartureTime(scheduleInsertDTO.getDepartureTime());
		schedule.setLandingTime(scheduleInsertDTO.getLandingTime());
		schedule.setStop(scheduleInsertDTO.getStop());
		return schedule;
    }

	public static Schedule scheduleUpdateDtoToSchedule(Schedule schedule, ScheduleUpdateDTO scheduleUpdateDTO) {
		if(schedule == null){
			return null;
		}
		schedule.setDepartureTime(scheduleUpdateDTO.getDepartureTime());
		schedule.setLandingTime(scheduleUpdateDTO.getLandingTime());
		schedule.setStop(scheduleUpdateDTO.getStop());
		return schedule;
	}

	public static ScheduleResponseDTO scheduleToScheduleResponseDTO(Schedule schedule, Long flightId) {
		if(schedule == null){
			return null;
		}
		ScheduleResponseDTO scheduleResponseDTO = new ScheduleResponseDTO();
		scheduleResponseDTO.setFlightId(flightId);
		scheduleResponseDTO.setScheduleId(schedule.getScheduleId());
		scheduleResponseDTO.setDepartureTime(schedule.getDepartureTime());
		scheduleResponseDTO.setLandingTime(schedule.getLandingTime());
		scheduleResponseDTO.setStop(schedule.getStop());
		return scheduleResponseDTO;
	}
}
