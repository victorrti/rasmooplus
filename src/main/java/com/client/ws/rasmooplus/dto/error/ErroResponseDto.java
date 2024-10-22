package com.client.ws.rasmooplus.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErroResponseDto {
    private String message;
    private HttpStatus httpStatus;
    private Integer statusCode;
}
