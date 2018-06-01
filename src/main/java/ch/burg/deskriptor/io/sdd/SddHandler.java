package ch.burg.deskriptor.io.sdd;

import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashSet;
import java.util.Set;

public class SddHandler extends DefaultHandler {

    private DiscreteDescriptor.Builder discreteDescriptorBuilder;

    @Getter
    private final Set<Descriptor> descriptors = new HashSet<>();

    private boolean inDiscrete = false;
    private boolean inRepresentation;
    private boolean inLabel = false;
    private boolean inStates = false;
    private boolean inStateDefinition = false;

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes atts) throws SAXException {
        switch (qName) {
            case "CategoricalCharacter":
                this.inDiscrete = true;
                discreteDescriptorBuilder = DiscreteDescriptor.builder();
                break;
            case "Representation":
                this.inRepresentation = true;
                break;

            case "Label":
                if (inRepresentation) {
                    this.inLabel = true;
                }
                break;

            case "States":
                this.inStates = true;
                break;

            case "StateDefinition":
                this.inStateDefinition = true;
                break;

            default:
                break;
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) {
        switch (qName) {
            case "CategoricalCharacter":
                this.inDiscrete = false;
                descriptors.add(discreteDescriptorBuilder.build());
                break;

            case "Representation":
                this.inRepresentation = false;
                break;

            case "Label":
                this.inLabel = false;
                break;

            case "States":
                this.inStates = false;
                break;

            case "StateDefinition":
                this.inStateDefinition = false;
                break;


            default:
                break;
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        final String stringContent = new String(ch, start, length);

        if (inLabel) {
            if (inDiscrete && !inStateDefinition) {
                discreteDescriptorBuilder.withName(stringContent);
            }
            if (inStateDefinition) {
                discreteDescriptorBuilder.withPossibleState(new State(stringContent));
            }
        }
    }
}
