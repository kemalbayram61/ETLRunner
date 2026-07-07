package tr.com.kml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tr.com.kml.domain.entity.Script;
import tr.com.kml.enums.ScriptStatus;

import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByStatus(ScriptStatus status);
}