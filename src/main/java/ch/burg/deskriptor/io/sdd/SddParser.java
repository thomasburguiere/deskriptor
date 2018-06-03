package ch.burg.deskriptor.io.sdd;

import ch.burg.deskriptor.model.Dataset;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class SddParser {



    public Dataset parse(final InputStream fileToParseStream) throws IOException, SAXException, ParserConfigurationException {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        final SAXParser parser = factory.newSAXParser();

        final SddHandler handler = new SddHandler();
        parser.parse(fileToParseStream, handler);

        return Dataset.builder()
                .items(handler.getItems())
                .descriptors(handler.getDescriptors())
                .build();
    }

}
