package org.darius.dto.request.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightUpdateDTO {
    @NotNull(message = "ID field is required")
    private Long id;
    @NotNull(message = "Passenger name field is required")
    @Size(min = 1, max = 150)
    private String flightName;
    @NotNull(message = "Departure city field is required")
    private Long departureCityId;
    @NotNull(message = "Arrival city field is required")
    private Long arrivalCityId;
    @NotNull(message = "Flight type field is required")
    @Size(min = 1, max = 150)
    private String flightType;
}