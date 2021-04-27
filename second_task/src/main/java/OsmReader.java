import dao.NodeDAO;
import dao.TagDAO;
import entities.NodeEntity;
import entities.TagEntity;
import generated.Node;

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
    private static final int BATCH_SIZE = 5000;

    public static void readNodes(StreamProcessor streamProcessor, int mode) throws JAXBException, XMLStreamException, SQLException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        NodeDAO nodeDAO = new NodeDAO();
        TagDAO tagDAO = new TagDAO();
        List<NodeEntity> nodeList = new ArrayList<>();

        LocalDateTime start = LocalDateTime.now();
        int count = 0;


        XMLStreamReader streamReader = streamProcessor.getNodeAsReader();
        while (streamReader!=null){
            Node node = (Node) unmarshaller.unmarshal(streamReader);
            if(count>ROWS_SIZE) break;
            switch (mode){
                case Main.MODE_STRING:
                    nodeDAO.insert(new NodeEntity(node));
                    for(Node.Tag tag: node.getTag()){
                        tagDAO.insert(new TagEntity(node.getId(), tag));
                    }
                    break;
                case Main.MODE_PREPARED:
                    nodeDAO.insertPrepared(new NodeEntity(node));
                    for(Node.Tag tag: node.getTag()){
                        tagDAO.insertPrepared(new TagEntity(node.getId(), tag));
                    }
                    break;
                case Main.MODE_BATCH:
                    nodeList.add(new NodeEntity(node));
                    if(nodeList.size()==BATCH_SIZE || count==ROWS_SIZE){
                        nodeDAO.batchInsert(nodeList);
                        List<TagEntity> tags = nodeList.stream()
                                .flatMap(nodeValue -> nodeValue.getTag().stream())
                                .collect(Collectors.toList());
                        tagDAO.batchInsert(tags);
                        nodeList.clear();
                    }
                    break;
                default:
                    System.out.println("WRONG MODE");
            }
            streamReader = streamProcessor.getNodeAsReader();
            count++;
        }

        LocalDateTime end = LocalDateTime.now();
        System.out.println("All time (s): " + ((double)Duration.between(start, end).toMillis())/1000);
        System.out.println("Time per 1 item (ms): " + ((double)Duration.between(start, end).toMillis())/count);
    }
}
