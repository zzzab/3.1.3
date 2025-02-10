package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.DTO.UserUpdateRequest;
import ru.kata.spring.boot_security.demo.models.entity.Role;
import ru.kata.spring.boot_security.demo.models.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/admin/users")
    public String findAll(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "admin";
    }

    @PostMapping("/user-create")
    public String createUser(User user,
                             @RequestParam(value = "roles") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        List<Role> listRoles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "update_user";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("userUpdateRequest") UserUpdateRequest userUpdateRequest) {
        User existingUser = userService.findById(userUpdateRequest.getId());

        //обновляем имя
        if (userUpdateRequest.getUsername() != null && !userUpdateRequest.getUsername().isEmpty()) {
            existingUser.setUsername(userUpdateRequest.getUsername());
        }

        //Обновляем фамилию
        if (userUpdateRequest.getSurname() != null && !userUpdateRequest.getSurname().isEmpty()) {
            existingUser.setSurname(userUpdateRequest.getSurname());
        }

        //Обновляем возраст
        if (userUpdateRequest.getAge() != 0) {
            existingUser.setAge(userUpdateRequest.getAge());
        }

        // Обновляем email, если он был изменен
        if (userUpdateRequest.getEmail() != null && !userUpdateRequest.getEmail().isEmpty()) {
            existingUser.setEmail(userUpdateRequest.getEmail());
        }

        // Обновляем пароль, только если он был изменен
        if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().isEmpty()) {
            existingUser.setPassword(userUpdateRequest.getPassword());
        }

        // Обновляем роли
        if (userUpdateRequest.getRoles() != null) {
            Set<Role> roles = roleService.getSetRoles(userUpdateRequest.getRoles());
            existingUser.setRoles(roles);
        }

        // Сохраняем обновленного пользователя
        userService.updateUser(existingUser);

        return "redirect:/admin/users";
    }

    @PostMapping("/user-delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
