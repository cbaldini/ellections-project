package net.ellections.controllers;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.ellections.exceptions.ForbiddenException;
import net.ellections.requests.LoginRequest;
import net.ellections.requests.PasswordUpdateRequest;
import net.ellections.requests.RegisterRequest;
import net.ellections.responses.AuthUserResponse;
import net.ellections.responses.UserResponse;
import net.ellections.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping
public class AuthenticationController {

    private final UserService userService;

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthUserResponse> authenticate(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserResponse> register(
        @RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody RegisterRequest request) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping(path = "/me", produces = "application/json")
    public ResponseEntity<AuthUserResponse> me(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(userService.me(token));
    }

    @PostMapping(path = "/change-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity recover(@RequestHeader(name = "Authorization") String token,
                                  @Valid @RequestBody PasswordUpdateRequest request) {
        return ResponseEntity.ok(userService.changePassword(token, request));
    }
}

