import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLStreamException;
import java.util.HashMap;

public class Main {

    static final Logger rootLogger = LogManager.getRootLogger();

    private static final HashMap<String, Integer> userEdits = new HashMap<>();
    private static final HashMap<String, Integer> nodesTags = new HashMap<>();

    public static void main(String[] args) {
        rootLogger.debug("START OF READING XML");

        try (StaxStreamProcessor processor = new StaxStreamProcessor(Main.class.getClassLoader().getResourceAsStream("RU-NVS.osm"))) {
            while (processor.startElement("node", "osm")) {
                String user = processor.getAttribute("user");
                Integer col = userEdits.get(user);
                if(col==null){
                    col = 0;
                }
                userEdits.put(user, ++col);

                String nodeId = processor.getAttribute("id");
                while (processor.startElement("tag", "node")) {
                    col = nodesTags.getOrDefault(nodeId, 0);
                    nodesTags.put(nodeId, ++col);
                }
            }
        } catch (XMLStreamException e) {
            rootLogger.error(e.getMessage());
        }

        rootLogger.debug("END OF READING XML");

        rootLogger.debug("PRINT USERS STATS");
        System.out.println(userEdits);
        rootLogger.debug("PRINT NODES STATS");
        System.out.println(nodesTags);

        rootLogger.debug("END OF PROGRAM");
    }
}