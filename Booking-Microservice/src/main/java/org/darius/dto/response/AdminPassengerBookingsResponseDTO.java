package org.darius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminPassengerBookingsResponseDTO {
    private Long flightNumber;
    private Long userId;
    private String fullName;
    private Integer age;
    private String gender;
    private String phoneNumber;
}
