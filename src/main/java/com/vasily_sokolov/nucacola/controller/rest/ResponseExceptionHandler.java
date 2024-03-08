package com.vasily_sokolov.nucacola.controller.rest;


import com.vasily_sokolov.nucacola.dto.ErrorDto;
import com.vasily_sokolov.nucacola.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
public class ResponseExceptionHandler {


    @ExceptionHandler(value = CapacityNotFoundException.class)
    public ResponseEntity<ErrorDto> capacityNotFoundException(CapacityNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "Capacity not in the application",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = CharacteristicNotFoundException.class)
    public ResponseEntity<ErrorDto> characteristicNotFoundException(CharacteristicNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "Characteristic not in the application",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = ListException.class)
    public ResponseEntity<ErrorDto> listException(ListException exception) {
        ErrorDto errorDto = new ErrorDto(
                "GOODS not in the application",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> productNotFoundException(ProductNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "Product not in the application",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = RawMaterialNotFoundException.class)
    public ResponseEntity<ErrorDto> rawMaterialNotFoundException(RawMaterialNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "RawMaterial not in the application",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = SaleNotFoundException.class)
    public ResponseEntity<ErrorDto> saleNotFoundException(SaleNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "Sale not in the application",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = StringNotCorrectException.class)
    public ResponseEntity<ErrorDto> stringNotCorrectException(StringNotCorrectException exception) {
        ErrorDto errorDto = new ErrorDto(
                "String is wrong",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = WarehouseNotFoundException.class)
    public ResponseEntity<ErrorDto> warehouseNotFoundException(WarehouseNotFoundException exception) {
        ErrorDto errorDto = new ErrorDto(
                "Warehouse not in the application",
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorDto> userNotAutowired(Exception exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorDto(
                                "User not Autowired",
                                exception.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Something Wrong", exception.getMessage()));
    }

}
