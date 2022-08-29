package org.darius.dto.request.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO implements Serializable {
    @NotNull(message = "Username field is required")
    private String username;
    @NotNull(message = "Password field is required")
    private String password;
}
