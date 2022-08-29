package org.darius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDTO extends RepresentationModel<FlightResponseDTO> {
	private Long flightNumber;
	private String flightName;
}
