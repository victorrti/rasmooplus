package com.client.ws.rasmooplus.Model.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Table(name="user_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserType  implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_type_id")
    private Long id;
    private String name;
    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}
