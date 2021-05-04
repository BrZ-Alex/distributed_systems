package nsu.akatov.third_task;

import lombok.RequiredArgsConstructor;
import nsu.akatov.third_task.entities.NodeEntity;
import nsu.akatov.third_task.repository.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NodeService {
    private final NodeRepository nodeRepository;

    public NodeEntity saveNodeEntity(NodeEntity node) {
        return nodeRepository.save(node);
    }

    public NodeEntity getNodeEntityById(int id) {
        return nodeRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);
    }

    public NodeEntity updateNodeEntity(int id, NodeEntity node) {
        NodeEntity nodeFromDb = nodeRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);

        node.setId(nodeFromDb.getId());
        return nodeRepository.save(node);
    }

    public void deleteNodeEntity(int id) {
        NodeEntity node = nodeRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);
        nodeRepository.delete(node);
    }

    public List<NodeEntity> searchNearNodes(Double lat, Double lon, Double radius) {
        return nodeRepository.getNearNodes(lat, lon, radius);
    }
}