package com.client.ws.rasmooplus.dto;

import com.client.ws.rasmooplus.Model.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPaymentInfoDto {
    private Long id;
    @Size(min=16,max=16,message="Deve container 16 carcteres")
    private String cardNumber;
    @Min(value=1)
    @Max(value=12)
    private Long cardExpirationMonth;

    private Long cardExpirationYear;

    private String cardSecurityCode;
    private BigDecimal price;
    @Size(min=3,max=3,message="deve conter 3 catcteres")
    private LocalDate dtPayment = LocalDate.now();
    @NotNull(message="deve ser informado")
    private Long userId;

}
