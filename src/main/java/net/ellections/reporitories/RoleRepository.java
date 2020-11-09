package net.ellections.reporitories;

import java.io.Serializable;
import java.util.List;
import net.ellections.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, Serializable, JpaSpecificationExecutor<Role> {
    List<Role> findAll();
    List<Role> findRoleByIdIn(List<Long> ids);

}
