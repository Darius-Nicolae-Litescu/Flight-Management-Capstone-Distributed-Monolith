package org.darius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerInsertResponseDTO {
    private String username;
    private String firstName;
    private String lastName;
}
