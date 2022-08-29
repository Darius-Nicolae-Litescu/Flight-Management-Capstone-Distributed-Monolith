package org.darius.dto.request.insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class FlightInsertDTO {
    @NotNull(message = "Passenger name field is required")
    @Size(min = 1, max = 150)
    private String flightName;
    @NotNull(message = "Departure city field is required")
    private Long departureCityId;
    @NotNull(message = "Arrival city field is required")
    private Long arrivalCityId;
    @NotNull(message = "Flight type field is required")
    private String flightType;
    @NotNull(message = "Seat capacity field is required")
    private Integer seatCapacity;
}