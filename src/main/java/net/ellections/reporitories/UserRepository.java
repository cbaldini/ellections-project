package net.ellections.reporitories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.ellections.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUuid(UUID uuid);
    List<User> findAllByDeletedAt(Date date);
    List<User> findAll();
    @Query(value = "SELECT * FROM auth_user WHERE uuid = ?1 AND deleted_at = null", nativeQuery = true)
    Optional<User> findActiveUserByUuid(UUID uuid);
}
