package com.client.ws.rasmooplus.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="subscriptions_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="subscriptions_type_id")
    private Long id;
    private String name;
    @Column(name="access_month")
    private long accessMonth;
    private BigDecimal price;
    @Column(name="product_key")
    private String productKey;
}
