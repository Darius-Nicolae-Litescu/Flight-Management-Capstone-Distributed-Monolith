package org.darius.entity.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.darius.entity.flight.Flight;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "city_name")
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "ArrivalCity_Flight",
			joinColumns = @JoinColumn(name = "arrival_city_id"),
			inverseJoinColumns = @JoinColumn(name = "flight_id"))
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Flight> arrivalFlights = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "DepartureCity_Flight",
			joinColumns = @JoinColumn(name = "departure_city_id"),
			inverseJoinColumns = @JoinColumn(name = "flight_id"))
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Flight> departureFlights = new HashSet<>();
}
