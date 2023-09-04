package org.example.services;

import org.example.model.User;
import org.example.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    public List<User> dataStore;

    @Autowired
    public AuthRepository authrepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authrepository.findByUserName(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }

    public List<User> getAllUsers() {
        System.out.println("All users from Postgres DB...!!!");
        return authrepository.findAll();
    }

    public String addUser(@NotNull @Valid User user) {
        if (user.getUserName().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()) {
            return ("inValid User Details");
        } else {
            return authrepository.save(user).toString();
        }
    }

    public User getUserByName(@NotNull @Valid String userName) {
        return authrepository.findByUserName(userName);
    }

    public String deleteUserByName(@NotNull @Valid String userName) {
        try {
            authrepository.deleteByUserName(userName);
            return ("User Deleted Successfully");
        } catch (Error error) {
            return ("Failed to delete the Users" + error);
        }
    }

    public String deleteUsers() {
        try {
            authrepository.deleteAll();
            return ("all Users Deleted Successfully");
        } catch (Error error) {
            return ("Failed to delete all Users" + error);
        }
    }


    @Cacheable(cacheNames = "dataCache")
    public List<User> getAllData() {
        simulateDataFetchingDelay();
        return dataStore;
    }

    private void simulateDataFetchingDelay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
