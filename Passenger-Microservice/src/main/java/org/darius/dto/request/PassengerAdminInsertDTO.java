package org.darius.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerAdminInsertDTO implements Serializable {
    @NotNull(message = "user id is required")
    @Min(value = 1, message = "user id must be greater than 0")
    private Long userId;
    @NotNull(message = "first name is required")
    private String firstName;
    @NotNull(message = "last name is required")
    private String lastName;
    @NotNull(message = "age is required")
    private Integer age;
    @NotNull(message = "gender is required")
    private String gender;
    @NotNull(message = "phone number is required")
    private String phoneNumber;
}
