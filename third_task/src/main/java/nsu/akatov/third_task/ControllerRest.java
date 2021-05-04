package nsu.akatov.third_task;

import lombok.RequiredArgsConstructor;
import nsu.akatov.third_task.db.DBUtils;
import nsu.akatov.third_task.dto.NodeDto;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ControllerRest {
    private final NodeService nodeService;

    @GetMapping("/{id}")
    public NodeDto getNode(@PathVariable("id") int id) {
        return Converter.convertNodeEntityToNodeDto(nodeService.getNodeEntityById(id));
    }

    @PostMapping
    public NodeDto createNode(@RequestBody NodeDto node) {
        return Converter.convertNodeEntityToNodeDto(
                nodeService.saveNodeEntity(Converter.convertNodeDtoToNodeEntity(node)));
    }

    @PutMapping("/{id}")
    public NodeDto updateNode(@PathVariable("id") int id,
                              @RequestBody NodeDto node) {
        return Converter.convertNodeEntityToNodeDto(
                nodeService.updateNodeEntity(
                        id, Converter.convertNodeDtoToNodeEntity(node))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        nodeService.deleteNodeEntity(id);
    }

    @GetMapping("/search")
    public List<NodeDto> search(
            @RequestParam("lat") Double lat,
            @RequestParam("lon") Double lon,
            @RequestParam("radius") Double radius
    ) {
        return nodeService.searchNearNodes(lat, lon, radius)
                .stream()
                .map(Converter::convertNodeEntityToNodeDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/init")
    public void init() {
        try {
            DBUtils.init();
            try (StreamProcessor processor = new StreamProcessor(ControllerRest.class.getClassLoader().getResourceAsStream("RU-NVS.osm"))) {
                OsmReader.readNodes(processor, nodeService);
            } catch (XMLStreamException | JAXBException | SQLException e) {
                System.out.println("Error");
            }
        } catch (SQLException | IOException | ClassNotFoundException e){
            System.out.println("Error");
        }
    }
}
