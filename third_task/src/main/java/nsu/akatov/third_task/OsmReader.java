package nsu.akatov.third_task;

import nsu.akatov.third_task.entities.NodeEntity;
import nsu.akatov.third_task.generated.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OsmReader {

    private static final int ROWS_SIZE = 10000;

    public static void readNodes(StreamProcessor streamProcessor, NodeService nodeService) throws JAXBException, XMLStreamException, SQLException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        int count = 0;

        XMLStreamReader streamReader = streamProcessor.getNodeAsReader();
        while (streamReader!=null){
            Node node = (Node) unmarshaller.unmarshal(streamReader);
            if(count>ROWS_SIZE) break;
            NodeEntity entity = Converter.convertNodeToNodeEntity(node);
            nodeService.saveNodeEntity(entity);
            streamReader = streamProcessor.getNodeAsReader();
            count++;
        }

    }
}
