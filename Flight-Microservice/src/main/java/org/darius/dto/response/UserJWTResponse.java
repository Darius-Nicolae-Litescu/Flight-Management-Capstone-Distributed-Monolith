package org.darius.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJWTResponse implements Serializable {
    String username;
    String jwtToken;
    List<String> userRoles;
}
