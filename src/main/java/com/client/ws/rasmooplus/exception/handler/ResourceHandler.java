package com.client.ws.rasmooplus.exception.handler;

import com.client.ws.rasmooplus.dto.error.ErroResponseDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponseDto> notFoundException(NotFoundException notFoundException){
        String errorMessage = notFoundException.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(montaErroResponseDto(errorMessage,HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()));
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponseDto> badRequestException(BadRequestException badRequestException){
        String errorMessage = badRequestException.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montaErroResponseDto(errorMessage,HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()));
    }
    public ErroResponseDto montaErroResponseDto(String message,HttpStatus httpStatus,Integer code){
        return ErroResponseDto.builder().message(message).httpStatus(httpStatus).statusCode(code).build();
    }

}
