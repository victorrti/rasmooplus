package com.client.ws.rasmooplus.Model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="user_type")
public class UserType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_type_id")
    private Long id;
}
