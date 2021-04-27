package com.example.wally.controller;

import com.example.wally.domain.SimpleUser;
import com.example.wally.jwt.TokenProvider;
import com.example.wally.service.SimpleUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@CrossOrigin
public class AuthController {

    private final SimpleUserService userService;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final String userNotFoundEncodedPassword;

    public AuthController(PasswordEncoder passwordEncoder, SimpleUserService userService,
                          TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;

        this.userNotFoundEncodedPassword = this.passwordEncoder
                .encode("userNotFoundPassword");
    }

    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void authenticate() {
        // we don't have to do anything here
        // this is just a secure endpoint and the JWTFilter
        // validates the token
        // this service is called at startup of the app to check
        // if the jwt token is still valid
    }

    @PostMapping("/login")
    public ResponseEntity<String> authorize(@Valid @RequestBody SimpleUser loginUser) {

        SimpleUser user = this.userService.lookUpUser(loginUser.getEmail());
        if (user != null) {
            boolean pwMatches = this.passwordEncoder.matches(loginUser.getPassword(),
                    user.getPassword());
            if (pwMatches) {
                String token = this.tokenProvider.createToken(loginUser.getEmail());
                return ResponseEntity.ok(token);
            }
        } else {
            this.passwordEncoder.matches(loginUser.getPassword(),
                    this.userNotFoundEncodedPassword);
            System.out.println("N-am gasit domnu");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SimpleUser signupUser) {
        if (this.userService.chechUserByEmail(signupUser.getEmail())) {
            return "EXISTS";
        }

        signupUser.encodePassword(this.passwordEncoder);
        this.userService.addSimpleUser(signupUser);
        return this.tokenProvider.createToken(signupUser.getEmail());
    }
}