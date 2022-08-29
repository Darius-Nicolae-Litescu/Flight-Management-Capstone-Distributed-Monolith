package org.darius.dto.request.insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBookingInsertDTO {
    @NotNull(message = "User ID field is required")
    private Long userId;
    @NotNull(message = "Number of seats field is required")
    private Long numberOfSeats;
}
