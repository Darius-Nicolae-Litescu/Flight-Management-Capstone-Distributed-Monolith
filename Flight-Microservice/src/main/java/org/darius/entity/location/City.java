package org.darius.entity.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.darius.entity.flight.Flight;
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
	private List<Flight> arrivalCities = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "DepartureCity_Flight",
			joinColumns = @JoinColumn(name = "departure_city_id"),
			inverseJoinColumns = @JoinColumn(name = "flight_id"))
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Flight> departureCities = new ArrayList<>();
}
