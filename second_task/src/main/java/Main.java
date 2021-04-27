import db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    static final Logger rootLogger = LogManager.getRootLogger();

    static final int MODE_STRING = 0;
    static final int MODE_PREPARED = 1;
    static final int MODE_BATCH = 2;

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        rootLogger.debug("START OF READING XML");

        DBUtils.init();

        try (StreamProcessor processor = new StreamProcessor(Main.class.getClassLoader().getResourceAsStream("RU-NVS.osm"))) {
            OsmReader.readNodes(processor, MODE_STRING);
            OsmReader.readNodes(processor, MODE_PREPARED);
            OsmReader.readNodes(processor, MODE_BATCH);
        } catch (XMLStreamException | JAXBException e) {
            rootLogger.error(e.getMessage());
        }

        rootLogger.debug("END OF READING XML");
        DBUtils.closeConnection();
        rootLogger.debug("END OF PROGRAM");
    }
}