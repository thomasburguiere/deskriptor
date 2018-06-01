package ch.burg.deskriptor.io.sdd;

import ch.burg.deskriptor.model.descriptor.Descriptor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class SddParser {

    final Set<Descriptor> descriptors = new HashSet<>();


    public Set<Descriptor> parse(final InputStream fileToParseStream) throws IOException, SAXException, ParserConfigurationException {
        final SAXParser parser = init();

        final SddHandler handler = new SddHandler();
        parser.parse(fileToParseStream, handler);

        return handler.getDescriptors();
    }

    private SAXParser init() throws ParserConfigurationException, SAXException {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        return factory.newSAXParser();
    }
}
