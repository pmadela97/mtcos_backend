package com.user.interfaces.controller;


import com.security.jwt.JwtTokenComponent;
import com.user.application.UserQueries;
import com.user.application.UserApplicationService;
import com.user.interfaces.form.JwtRequest;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
class JwtAuthUserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenComponent jwtTokenComponent;
    private final UserApplicationService userApplicationService;
    private final UserQueries userQueries;


    @Autowired
    private JwtAuthUserController(AuthenticationManager authenticationManager, JwtTokenComponent jwtTokenComponent, UserApplicationService userApplicationService, UserQueries userQueries) {

        Assert.notNull(authenticationManager, "authenticationManager must be not null");
        Assert.notNull(jwtTokenComponent, "jwtTokenComponent must be not null");
        Assert.notNull(userApplicationService, "userService must be not null");
        Assert.notNull(userQueries, "userQueries must be not null");

        this.authenticationManager = authenticationManager;
        this.jwtTokenComponent = jwtTokenComponent;
        this.userApplicationService = userApplicationService;
        this.userQueries = userQueries;
    }


    @PostMapping
    private ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        final UserDetails userDetails = userApplicationService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenComponent.generateToken(userDetails);

        System.out.println("SEND AUTH TOKEN: " + token);

        return ResponseEntity.ok("\""+token+"\"");
    }

    private void authenticate(String username, String password) throws Exception {

        Assert.hasText(username, "username must be has text");
        Assert.hasText(password, "password must be has text");

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e) {

            System.out.println("USER_DISABLED");

            throw new Exception("USER_DISABLED", e);

        }
        catch (BadCredentialsException e) {

            System.out.println("INVALID_CREDENTIALS");

            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

