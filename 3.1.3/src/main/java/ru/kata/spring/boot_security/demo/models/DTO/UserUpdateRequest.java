package ru.kata.spring.boot_security.demo.models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String username;

    @NotEmpty
    private String surname;

    @NotEmpty
    private int age;

    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty
    private String password;

    @NotNull(message = "Role cannot be blank")
    private String[] roles;
}
