package org.darius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Schedule {

	private Long scheduleId;
	private LocalTime departureTime;
	private LocalTime landingTime;
	private String stop;
}
