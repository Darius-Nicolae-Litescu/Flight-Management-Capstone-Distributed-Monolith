package org.darius.dto.request.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO implements Serializable {
    @NotNull(message = "Username field is required")
    @Size(min = 3, max = 25, message = "Username must be between 3 and 25 characters")
    private String username;
    @NotNull(message = "Password field is required")
    @Size(min = 3, max = 25, message = "Password must be between 3 and 25 characters")
    private String password;
}
