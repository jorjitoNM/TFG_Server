package org.server.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationUser {
    private String username;
    private String password;
    private String email;
}
