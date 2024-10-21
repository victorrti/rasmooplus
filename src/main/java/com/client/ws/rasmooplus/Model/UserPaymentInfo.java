package com.client.ws.rasmooplus.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="user_payment_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_payment_info_id")
    private Long id;
    private String cardNumber;
    @Column(name="card_expiration_month")
    private Long cardExpirationMonth;
    @Column(name="card_expiration_year")
    private Long cardExpirationYear;
    @Column(name="card_security_code")
    private String cardSecurityCode;
    private BigDecimal price;
    @Column(name="dt_payment")
    private LocalDate dtPayment;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="users_id")
    private User user;
}
