package com.extraallt.extraallt.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.extraallt.extraallt.models.User;

@Service
public class UserService {

    private final MongoOperations mongoOperations;
    private PasswordEncoder passwordEncoder;

    public UserService(MongoOperations mongoOperations, PasswordEncoder passwordEncoder) {
        this.mongoOperations = mongoOperations;
        this.passwordEncoder = passwordEncoder;
    }

    // Hämtar alla users
    public List<User> getUsers() {
        return mongoOperations.findAll(User.class);
    }

    // Hämtar specifik user baserat på namn. Används för inloggningsmetod
    public User getUserByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, User.class);
    }

    // Lägg till user. Krypterar lösenordet innan tillägning till databasen
    public User addUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        User dbUser = mongoOperations.findOne(query, User.class);

        if (dbUser != null) {
            throw new RuntimeException("Användarnamnet är upptaget.");
        }
        String encyptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encyptedPassword);
        return mongoOperations.insert(user);
    }

}
