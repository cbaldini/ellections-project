package net.ellections.controllers;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import net.ellections.entities.Area;
import net.ellections.exceptions.ForbiddenException;
import net.ellections.requests.AreaRequest;
import net.ellections.services.AreaService;
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
@RequestMapping("/areas")
public class AreaController {

    private AreaService areaService;
    private UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Area>> getAreas(
        @RequestHeader(name = "Authorization") String token) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }
        return ResponseEntity.ok(areaService.getAreas());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Area> createArea(
        @RequestHeader(name = "Authorization") String token,
        @Valid @RequestBody AreaRequest request) {
        if (!userService.verifyRole(token, "ADMIN")) {
            throw new ForbiddenException();
        }
        return ResponseEntity.ok(areaService.createArea(request));
    }

}
