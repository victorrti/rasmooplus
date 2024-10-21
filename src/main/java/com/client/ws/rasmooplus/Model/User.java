package com.client.ws.rasmooplus.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="users_id")
    private Long id;
    private String nome;
    private String email;
    private String phone;
    private  String cpf;

    private LocalDate dtSubscription = LocalDate.now();
}
