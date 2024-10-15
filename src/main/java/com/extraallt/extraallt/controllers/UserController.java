package com.extraallt.extraallt.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.extraallt.extraallt.models.User;
import com.extraallt.extraallt.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpriationMs;

    @GetMapping
    public String getRoot() {
        return "Hello World!";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        System.out.println("Hämtar alla users...");
        ;
        return userService.getUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User addedUser = userService.addUser(user);
            System.out.println("User lades till!");
            return ResponseEntity.ok(addedUser);
        } catch (Exception e) {
            System.out.println("Användarnamnet var upptaget!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Användarnamnet är upptaget.");
        }
    }

    @PostMapping("login-user")
    public ResponseEntity<?> login(@RequestBody User user) {
        User dbUser = userService.getUserByUsername(user.getUsername());

        if (dbUser != null) {
            String encodedPassword = dbUser.getPassword();
            String incomingPassword = user.getPassword();

            if (passwordEncoder.matches(incomingPassword, encodedPassword)) {
                System.out.println("Loggade in!");
                @SuppressWarnings("deprecation")
                String token = Jwts.builder()
                        .setSubject(dbUser.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + jwtExpriationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
                return ResponseEntity.ok(token);
            }
        }
        System.out.println("Fel användarnamn eller lösenord vid inloggning!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fel användarnamn eller lösenord.");
    }
}
