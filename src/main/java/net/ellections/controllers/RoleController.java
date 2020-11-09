package net.ellections.controllers;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.ellections.entities.Role;
import net.ellections.exceptions.ForbiddenException;
import net.ellections.requests.RoleRequest;
import net.ellections.services.UserService;
import net.ellections.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Role>> getAll(@RequestHeader(name = "Authorization") String token) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(roleService.getRoles());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Role> create(
        @RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody RoleRequest request) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(roleService.createRole(request));
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Role> delete(
        @RequestHeader(name = "Authorization") String token,
        @PathVariable("id") Long id) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }

        return ResponseEntity.ok(roleService.deleteRole(id));
    }
}
