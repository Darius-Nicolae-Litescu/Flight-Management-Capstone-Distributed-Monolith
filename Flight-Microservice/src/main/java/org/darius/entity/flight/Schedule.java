package org.darius.entity.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "schedule_id")
	private Long scheduleId;
	@Column(name = "departure_time")
	private LocalTime departureTime;
	@Column(name = "landing_time")
	private LocalTime landingTime;
	@Column(name = "stop")
	private String stop;
}
