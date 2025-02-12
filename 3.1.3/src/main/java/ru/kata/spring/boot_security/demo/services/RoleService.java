package ru.kata.spring.boot_security.demo.services;

import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.models.entity.Role;

import java.util.Set;

public interface RoleService {
    Role getRoleByName(String name);
    Set<Role> getSetRoles(@Param("names") String[] roleNames);
}