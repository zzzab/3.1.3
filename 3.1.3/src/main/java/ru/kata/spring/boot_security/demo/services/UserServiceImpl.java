package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("User " + user.getUsername() + " " + user.getSurname() + " saved");
    }

    @Override
    public void updateUser(User user) {
        User existingUser = findById(user.getId());

        Optional.ofNullable(user.getPassword())
                .filter(password -> !password.isEmpty())
                .filter(password -> !existingUser.getPassword().equals(password))
                .ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));

        userRepository.save(user);
        System.out.println("User " + user.getUsername() + " " + user.getSurname() + " updated");
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
        User user = findById(id);
        System.out.println("User " + user.getUsername() + " " + user.getSurname() + " deleted");
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found " + email);
        }
        return user;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}