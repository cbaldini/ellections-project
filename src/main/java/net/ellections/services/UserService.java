package net.ellections.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonMergePatch;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ellections.dto.domain.JwtUser;
import net.ellections.entities.Role;
import net.ellections.entities.User;
import net.ellections.exceptions.BadRequestException;
import net.ellections.exceptions.NotFoundException;
import net.ellections.exceptions.UnauthorizedException;
import net.ellections.reporitories.RoleRepository;
import net.ellections.reporitories.UserRepository;
import net.ellections.requests.LoginRequest;
import net.ellections.requests.PasswordUpdateRequest;
import net.ellections.requests.RegisterRequest;
import net.ellections.responses.AuthUserResponse;
import net.ellections.responses.UserResponse;
import net.ellections.utils.PatchHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final HttpServletRequest request;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;
    private final RoleRepository roleRepository;
    private final PatchHelper patchHelper;


    public AuthUserResponse authenticate(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            loginAttemptService.loginFailed("");
            throw new UnauthorizedException();
        }

        user.setLastLogin(new Date());
        userRepository.save(user);
        loginAttemptService.loginSucceeded("");

        return user.toAuthResponse(jwtService.generateJwt(user));
    }

    public UserResponse register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            throw new BadRequestException("Username already registered");
        }

        String generatedPassword = RandomStringUtils.randomAlphanumeric(10);
        System.out.println("GENERATED PASSWORD: " + generatedPassword);
        User user = User.builder()
            .uuid(UUID.randomUUID())
            .name(request.getName())
            .email(request.getEmail())
            .timezone(request.getTimezone())
            .roles(new ArrayList<>())
            .password(passwordEncoder.encode(generatedPassword))
            .createdAt(new Date())
            .lastLogin(new Date())
            .build();

        userRepository.save(user);

        log.info("Registered new user '{}' [{}]", user.getName(), user.getUuid());

        return user.toResponse();
    }

    public AuthUserResponse me(String header) {
        JwtUser details = getJwtUser(header);
        Optional<User> userOptional = userRepository.findByEmail(details.getSubject());

        if (!userOptional.isPresent() || userOptional.get().getDeletedAt() != null) {
            throw new UnauthorizedException();
        }

        User user = userOptional.get();

        return user.toAuthResponse(header.replace("Bearer ", ""));
    }

    public UserResponse getUserByUUID(String uuid) {
        return findUserByUUID(uuid).toResponse();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponse removeUser(String uuid) {
        User user = findUserByUUID(uuid);

        user.setDeletedAt(new Date());
        userRepository.save(user);

        log.info("Removed user '{}' [{}]", user.getName(), user.getUuid());

        return user.toResponse();
    }

    public UserResponse restoreUser(String uuid) {
        User user = findUserByUUID(uuid, true);

        user.setDeletedAt(null);
        userRepository.save(user);

        log.info("Restored user '{}' [{}]", user.getName(), user.getUuid());

        return user.toResponse();
    }

    public UserResponse addRoleToUser(String uuid, Role role) {
        User user = findUserByUUID(uuid);
        List<Role> roles = user.getRoles();

        if (!user.getRoles().contains(role)) {
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }

        return user.toResponse();
    }

    public UserResponse removeRoleFromUser(String uuid, Role role) {
        User user = findUserByUUID(uuid);
        List<Role> roles = user.getRoles();

        if (user.getRoles().contains(role)) {
            roles.remove(role);
            user.setRoles(roles);
            userRepository.save(user);
        }

        return user.toResponse();
    }

    public Boolean verifyRole(String header, String roleName) {
        User user = getLoggedUser(header);

        return user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains(roleName);
    }

    public Long verifyId (String header){
        User user = getLoggedUser(header);
        return user.getId();
    }

    public UserResponse resetPassword(String uuid) {
        User user = findUserByUUID(uuid);
        String generatedPassword = RandomStringUtils.randomAlphanumeric(36);
        user.setPassword(passwordEncoder.encode(generatedPassword));
        userRepository.save(user);

        log.info("Password reset for user '{}' [{}]", user.getName(), user.getUuid());

        return user.toResponse();
    }

    public UserResponse changePassword(String header, PasswordUpdateRequest request) {
        JwtUser details = getJwtUser(header);
        Optional<User> userOptional = userRepository.findByUuid(UUID.fromString(details.getId()));

        if (!userOptional.isPresent()) {
            throw new NotFoundException("User");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return user.toResponse();
    }

    private User findUserByUUID(String uuid) {
        return findUserByUUID(uuid, false);
    }

    private User findUserByUUID(String uuid, Boolean withDeleted) {
        Optional<User> userOptional = userRepository.findByUuid(UUID.fromString(uuid));

        if (!userOptional.isPresent() || (!withDeleted && userOptional.get().getDeletedAt() != null)) {
            throw new NotFoundException("User");
        }

        return userOptional.get();
    }

    private User getLoggedUser(String header) {
        JwtUser details = getJwtUser(header);
        Optional<User> userOptional = userRepository.findByUuid(UUID.fromString(details.getId()));

        if (!userOptional.isPresent() || userOptional.get().getDeletedAt() != null) {
            throw new NotFoundException("User");
        }

        return userOptional.get();
    }

    private JwtUser getJwtUser(String authorization) {
        return jwtService.getUserDetails((authorization.replace("Bearer ", "")),
            jwtService.getSecret());
    }

    private String getClientIP() {
        String proxyHeader = request.getHeader("X-Forwarded-For");

        return (proxyHeader == null) ? request.getRemoteAddr() : proxyHeader.split(",")[0];
    }

    public UserResponse updateUser(UUID uuid, Map<String, Object> differences) {
        User user = userRepository.findByUuid(uuid)
            .orElseThrow(() -> new NotFoundException("User"));

        JsonMergePatch mergePatch = Json.createMergePatch(Json.createObjectBuilder(differences).build());
        User userPatched = patchHelper.mergePatch(mergePatch, user, User.class);
        userPatched.setRoles(obtainRoles(userPatched.getRoles()));
        userPatched.setUpdatedAt(Date.from(Instant.now()));


        return userRepository.save(userPatched).toResponse();

    }
    private List<Role> obtainRoles(List<Role> roles) {

        return roleRepository.findRoleByIdIn(roles.stream()
            .map(Role::getId)
            .collect(Collectors.toList()));

    }
    public UserResponse updateSessionUser(String token, Map<String, Object> differences) {
        JwtUser jwtUser = getJwtUser(token);
        User user = userRepository.findByUuid(UUID.fromString(jwtUser.getId()))
            .orElseThrow(() -> new NotFoundException("User"));

        JsonMergePatch mergePatch = Json.createMergePatch(Json.createObjectBuilder(differences).build());
        User userPatched = patchHelper.mergePatch(mergePatch, user, User.class);
        userPatched.setRoles(obtainRoles(userPatched.getRoles()));
        userPatched.setUpdatedAt(Date.from(Instant.now()));
        return userRepository.save(userPatched).toResponse();
    }
}

