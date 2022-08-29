package org.darius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerResponseDTO implements Serializable {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String phoneNumber;
}
