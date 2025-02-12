package ru.kata.spring.boot_security.demo.models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private Long id;

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Surname cannot be null")
    private String surname;

    private int age;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Roles cannot be null")
    private String[] roles;
}