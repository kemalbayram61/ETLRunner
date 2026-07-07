package tr.com.kml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.kml.domain.entity.TargetDatabase;

import java.util.List;
import java.util.Optional;

@Repository
public interface TargetDatabaseRepository extends JpaRepository<TargetDatabase, Long> {
    List<TargetDatabase> findByActive(boolean active);
    Optional<TargetDatabase> findByName(String name);
    List<TargetDatabase> findByNameContainingIgnoreCase(String name);
}
