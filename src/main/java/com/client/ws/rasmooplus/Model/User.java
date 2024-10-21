package com.client.ws.rasmooplus.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="users_id")
    private Long id;
    private String nome;
    private String email;
    private String phone;
    private  String cpf;
    @Column(name="dt_subscription")
    private LocalDate dtSubscription = LocalDate.now();
    @Column(name="dt_expiration")
    private LocalDate dtExpiration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_type_id")
    private UserType userType;
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name="subscriptions_type_id")
    private SubscriptionType subscriptionType;
}
