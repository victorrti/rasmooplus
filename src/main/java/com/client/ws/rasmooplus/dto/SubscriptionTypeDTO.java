package com.client.ws.rasmooplus.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionTypeDTO {

    private Long id;
    @NotBlank(message = "campo name não pode sr nulo ou vazio")
    @Size(min=4,max=30)
    private String name;
    @Max(value = 12,message = "campo acessMonths não pode ser maior que 12")
    private Long accessMonths;
    @NotNull(message="campo price não pode ser nulo")
    private BigDecimal price;
    @NotBlank(message = "campo name não pode sr nulo ou vazio")
    @Size(min=5,max=15,message="campo productkey deve conter um tamanho  entre 5 e 15")
    private String productKey;
}
