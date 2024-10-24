package com.client.ws.rasmooplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentProcessDTO {
    @NotBlank(message="não pode ser vazio ou nulo")
    private String productKey;
    private BigDecimal discount;
    @NotNull(message="dados do pagamento devem ser informados")
    @JsonProperty("userPaymentInfo")
    private  UserPaymentInfoDto userPaymentInfoDto;
}
