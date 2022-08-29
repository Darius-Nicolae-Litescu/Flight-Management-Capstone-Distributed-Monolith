package org.darius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Flight {

	private Long flightId;
	private String flightName;
	private String flightType;
	private Integer flightSeatCapacity;
	private Schedule flightSchedule;
}