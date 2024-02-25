package com.vasily_sokolov.nucacola.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Tag(name = "ErrorDto", description = "The class contains information about the error if it occurs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorDto {

    @Schema(description = "Exception name")
    private String title;

    @Schema(description = "Error message")
    private String message;
}
