package org.darius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponseDTO {
    private Long scheduleId;
    private Long flightId;
    private LocalTime departureTime;
    private LocalTime landingTime;
    private String stop;
}
