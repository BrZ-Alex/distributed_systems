package nsu.akatov.third_task;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

public class StreamProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    public StreamProcessor(InputStream io) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(io);
    }

    public XMLStreamReader getNodeAsReader() throws XMLStreamException {
        while(reader.hasNext()){
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && "node".equals(reader.getLocalName())) {
                return reader;
            }
        }
        return null;
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }
}
