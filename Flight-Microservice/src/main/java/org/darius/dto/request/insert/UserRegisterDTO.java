package org.darius.dto.request.insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO implements Serializable {
    @NotNull(message = "Username field is required")
    private String username;
    @NotNull(message = "Password field is required")
    private String password;
    @NotNull(message = "FirstName field is required")
    private String firstName;
    @NotNull(message = "LastName field is required")
    private String lastName;
    @NotNull(message = "Age field is required")
    private Integer age;
    @NotNull(message = "Gender field is required")
    private String gender;
    @NotNull(message = "Phone number field is required")
    private String phoneNumber;
}
