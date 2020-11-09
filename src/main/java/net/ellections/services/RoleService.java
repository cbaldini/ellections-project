package net.ellections.services;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ellections.entities.Role;
import net.ellections.exceptions.NotFoundException;
import net.ellections.reporitories.RoleRepository;
import net.ellections.requests.RoleRequest;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public Role createRole(RoleRequest request) {
        Role role = Role.builder().name(request.getName()).build();

        return roleRepository.save(role);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role deleteRole(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);

        if (!optionalRole.isPresent()) {
            throw new NotFoundException("Role");
        }

        Role role = optionalRole.get();
        roleRepository.delete(role);

        return role;
    }

    public Role getById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);

        if (!optionalRole.isPresent()) {
            throw new NotFoundException("Role");
        }

        return optionalRole.get();
    }
}

