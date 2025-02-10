package ru.kata.spring.boot_security.demo.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> user;

    public Role(String roleName) {
        this.name = roleName;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}