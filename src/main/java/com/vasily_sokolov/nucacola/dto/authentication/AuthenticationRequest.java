package com.vasily_sokolov.nucacola.dto.authentication;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull(message = "Username shouldn't be null")
    @Size(min = 1, max = 44, message = "Username should be not null and from 1 to 44 symbols")
    private String userName;

    @NotNull(message = "Password shouldn't be null")
    @Size(min = 1, max = 44, message = "Password should be not null and from 1 to 44 symbols")
    private String password;
}