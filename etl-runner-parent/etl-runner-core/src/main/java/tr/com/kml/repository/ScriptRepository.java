package tr.com.kml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.kml.domain.entity.Script;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
}