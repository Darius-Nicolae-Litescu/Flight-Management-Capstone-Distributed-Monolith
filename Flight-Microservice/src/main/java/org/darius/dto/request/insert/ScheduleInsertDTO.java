package org.darius.dto.request.insert;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleInsertDTO implements Serializable {
    @NotNull(message = "Departure time must not be null")
    private LocalTime departureTime;
    @NotNull(message = "Landing time must not be null")
    private LocalTime landingTime;
    @NotNull(message = "Stop must not be null")
    private String stop;
}
