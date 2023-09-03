package org.example.controllers;

import org.example.model.AuthRequest;

import org.example.model.User;
import org.example.services.AuthService;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    User user = new User();

    @GetMapping("/getAllUsers")
    @Cacheable(cacheNames = "dataCache")
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }

    @PostMapping("/addUser")
    @CacheEvict
    public String getAllUsers(@RequestBody User user) {
        return authService.addUser(user);
    }

    @GetMapping("/getUserById")
    @CacheEvict
    public User getUserById(@RequestParam String userName) {
        return authService.getUserByName(userName);
    }

    @DeleteMapping("/deleteUserById")
    @CacheEvict
    public String deleteCustomerById(@RequestParam String userName) {
        return authService.deleteUserByName(userName);
    }

    @DeleteMapping("/deleteAllUsers")
    public String deleteAllUsers() {
       return authService.deleteUsers();
    }


    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            System.out.println(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
}
