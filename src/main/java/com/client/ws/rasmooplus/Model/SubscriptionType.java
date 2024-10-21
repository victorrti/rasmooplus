package com.client.ws.rasmooplus.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="subscriptions_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private long accessMonth;
    private BigDecimal price;
    private String productKey;
}
