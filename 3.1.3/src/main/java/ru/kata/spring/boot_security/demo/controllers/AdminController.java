package ru.kata.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

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
                             @RequestParam(value = "rolesId") String[] roles) {
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
    public String updateUser(User user,
                             @RequestParam(value = "rolesId") String[] roles) {
        user.setRoles(roleService.getSetRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/user-delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
