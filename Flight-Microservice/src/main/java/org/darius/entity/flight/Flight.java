package org.darius.entity.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "flight_id")
	private Long flightId;


	@Column(name = "flight_name")
	private String flightName;

	@Column(name = "flight_type")
	private String flightType;

	@Column(name = "flight_seat_capacity")
	private Integer flightSeatCapacity;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "flight_schedule")
	private Schedule flightSchedule;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "flight_id")
	private List<Seat> seats = new ArrayList<>();
}