package net.ellections.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.ellections.entities.Role;
import net.ellections.entities.User;
import net.ellections.exceptions.ForbiddenException;
import net.ellections.requests.RegisterRequest;
import net.ellections.responses.UserResponse;
import net.ellections.services.RoleService;
import net.ellections.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private final RoleService roleService;


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAll(@RequestHeader(name = "Authorization") String token) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(path = "/{uuid}", produces = "application/json")
    public ResponseEntity<UserResponse> getByUUID(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("uuid") String uuid) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(userService.getUserByUUID(uuid));
    }

    @DeleteMapping(path = "/{uuid}", produces = "application/json")
    public ResponseEntity<UserResponse> deleteUser(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("uuid") String uuid) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(userService.removeUser(uuid));
    }

    @PostMapping(path = "/{uuid}/reset", produces = "application/json")
    public ResponseEntity resetPassword(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("uuid") String uuid) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(userService.resetPassword(uuid));
    }
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid RegisterRequest user){
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping(path = "/{uuid}/restore", produces = "application/json")
    public ResponseEntity restoreUser(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("uuid") String uuid) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(userService.restoreUser(uuid));
    }

    @PostMapping(path = "/{uuid}/roles/{roleId}", produces = "application/json")
    public ResponseEntity<UserResponse> addRoleToUser(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("uuid") String uuid,
        @PathVariable("roleId") Long roleId) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        Role role = roleService.getById(roleId);

        return ResponseEntity.ok(userService.addRoleToUser(uuid, role));
    }

    @DeleteMapping(path = "/{uuid}/roles/{roleId}", produces = "application/json")
    public ResponseEntity<UserResponse> deleteRoleFromUser(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("uuid") String uuid,
        @PathVariable("roleId") Long roleId) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }
        Role role = roleService.getById(roleId);

        return ResponseEntity.ok(userService.removeRoleFromUser(uuid, role));
    }

    @PatchMapping(path = "/{uuid}")
    public ResponseEntity<UserResponse> partialUpdate(
        @RequestHeader(name = "Authorization") String token,
        @RequestBody Map<String,Object> difference,
        @PathVariable("uuid") UUID uuid){

        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(userService.updateUser(uuid, difference));
    }

    @PatchMapping("user")
    public ResponseEntity<UserResponse> userUpdate(@RequestHeader(name="Authorization") String token,
                                                   @RequestBody Map<String, Object> difference){
        return ResponseEntity.ok(userService.updateSessionUser(token,difference));
    }

}
