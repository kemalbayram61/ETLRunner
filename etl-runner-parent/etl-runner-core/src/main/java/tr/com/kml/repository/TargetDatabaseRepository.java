package tr.com.kml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.kml.domain.entity.TargetDatabase;

@Repository
public interface TargetDatabaseRepository extends JpaRepository<TargetDatabase, Long> {
}
