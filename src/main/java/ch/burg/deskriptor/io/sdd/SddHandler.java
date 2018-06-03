package ch.burg.deskriptor.io.sdd;

import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashSet;
import java.util.Set;

public class SddHandler extends DefaultHandler {

    private State.StateBuilder stateBuilder;
    private DiscreteDescriptor.Builder discreteDescriptorBuilder;
    private Item.ItemBuilder itemBuilder;

    @Getter
    private final Set<Descriptor> descriptors = new HashSet<>();

    @Getter
    private final Set<Item> items = new HashSet<>();

    private boolean inCategoricalCharacter = false;
    private boolean inRepresentation;
    private boolean inLabel = false;
    private boolean inStateDefinition = false;
    private boolean inCodedDescription = false;
    private boolean inSummaryData = false;
    private boolean inCategorical = false;
    private boolean inState = false;
    private DiscreteDescriptor currentDiscreteDescriptor;

    @Override
    public void startDocument() {

    }

    @Override
    public void endDocument() {

    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes atts) {
        switch (qName) {
            case "CategoricalCharacter":
                this.inCategoricalCharacter = true;
                discreteDescriptorBuilder = DiscreteDescriptor.builder()
                        .withId(atts.getValue("id"));
                break;
            case "Representation":
                this.inRepresentation = true;
                break;

            case "Label":
                if (inRepresentation) {
                    this.inLabel = true;
                }
                break;

            case "StateDefinition":
                this.inStateDefinition = true;
                stateBuilder = State.builder()
                        .id(atts.getValue("id"));

                break;

            case "CodedDescription":
                this.inCodedDescription = true;
                itemBuilder = Item.builder();
                break;

            case "SummaryData":
                this.inSummaryData = true;
                break;

            case "Categorical":
                if (inSummaryData) {
                    final String currentDescriptorId = atts.getValue("ref");
                    currentDiscreteDescriptor = (DiscreteDescriptor) descriptors.stream()
                            .filter(d -> d.getId().equals(currentDescriptorId))
                            .findFirst().get();
                }
                this.inCategorical = true;
                break;

            case "State":
                if (inSummaryData && inCategorical) {
                    final String selectedStateId = atts.getValue("ref");
                    final State selectedState = currentDiscreteDescriptor.getPossibleSates().stream()
                            .filter(s -> s.getId().equals(selectedStateId))
                            .findFirst().get();
                    itemBuilder.describe(currentDiscreteDescriptor).withSelectedStates(selectedState);
                }
                this.inState = true;
                break;

            default:
                break;
        }
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) {
        switch (qName) {
            case "CategoricalCharacter":
                this.inCategoricalCharacter = false;
                descriptors.add(discreteDescriptorBuilder.build());
                break;

            case "Representation":
                this.inRepresentation = false;
                break;

            case "Label":
                this.inLabel = false;
                break;

            case "StateDefinition":
                this.inStateDefinition = false;
                discreteDescriptorBuilder.withPossibleState(stateBuilder.build());
                break;

            case "CodedDescription":
                this.inCodedDescription = false;
                items.add(itemBuilder.build());
                break;

            case "SummaryData":
                this.inSummaryData = false;
                break;

            case "Categorical":
                this.inCategorical = false;
                break;

            case "State":
                this.inState = false;
                break;


            default:
                break;
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) {
        final String stringContent = new String(ch, start, length);

        if (inLabel) {
            if (inCategoricalCharacter && !inStateDefinition) {
                discreteDescriptorBuilder.withName(stringContent);
            }
            if (inStateDefinition) {
                stateBuilder.name(stringContent);
            }
            if (inCodedDescription) {
                itemBuilder.withName(stringContent);
            }
        }
    }
}
