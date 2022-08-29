package org.darius.dto.request.insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookingInsertDTO {
    @NotNull(message = "Number of seats field is required")
    private Long numberOfSeats;
}
