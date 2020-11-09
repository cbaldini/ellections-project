package net.ellections.reporitories;

import java.io.Serializable;
import java.util.List;
import net.ellections.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository <Area, Long>, Serializable, JpaSpecificationExecutor<Area> {
    List<Area> findAll();
}
