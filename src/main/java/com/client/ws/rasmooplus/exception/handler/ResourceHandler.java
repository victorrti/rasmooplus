package com.client.ws.rasmooplus.exception.handler;

import com.client.ws.rasmooplus.dto.error.ErroResponseDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroResponseDto> notFoundException(NotFoundException notFoundException){
        String errorMessage = notFoundException.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(montaErroResponseDto(errorMessage,HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()));
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErroResponseDto> badRequestException(BadRequestException badRequestException){
        String errorMessage = badRequestException.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(montaErroResponseDto(errorMessage,HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDto>  methodArgumentNotValidException(MethodArgumentNotValidException m){
        Map<String,String> messages = new HashMap<>();
        m.getBindingResult().getAllErrors().forEach(error->{
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            messages.put(field,defaultMessage);
        });
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErroResponseDto.builder()
                .message(Arrays.toString(messages.entrySet().toArray()))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build());
    }
    public ErroResponseDto montaErroResponseDto(String message,HttpStatus httpStatus,Integer code){
        return ErroResponseDto.builder().message(message).httpStatus(httpStatus).statusCode(code).build();
    }


}
