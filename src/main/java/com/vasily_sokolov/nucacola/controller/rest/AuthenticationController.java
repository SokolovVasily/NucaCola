package com.vasily_sokolov.nucacola.controller.rest;

import com.vasily_sokolov.nucacola.dto.ErrorDto;
import com.vasily_sokolov.nucacola.dto.authentication.AuthenticationRequest;
import com.vasily_sokolov.nucacola.dto.authentication.AuthenticationResponse;
import com.vasily_sokolov.nucacola.service.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "Authenticate User",
            description = "Authenticate User and returns Token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication Request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "All its great,return Token",
                            content = {@Content(schema = @Schema(implementation = AuthenticationResponse.class),
                                    mediaType = "application/json")}),
                    @ApiResponse(responseCode = "500",
                            description = "return Error Message because Login or Password is not correct",
                            content = {@Content(schema = @Schema(implementation = ErrorDto.class),
                                    mediaType = "application/json")})
            })
    @PostMapping ("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}