package nsu.akatov.third_task.repository;

import nsu.akatov.third_task.entities.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, Integer> {
    @Query(value = "SELECT * FROM nodes  " +
            "WHERE earth_distance(" +
            "ll_to_earth(?1, ?2), " +
            "ll_to_earth(nodes.lat, nodes.lon)" +
            ") < ?3",
            nativeQuery = true)
    List<NodeEntity> getNearNodes(double lat, double lon, double radius);
}
