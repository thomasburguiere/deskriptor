package ch.burg.deskriptor.io.sdd;

import ch.burg.deskriptor.TestUtils;
import ch.burg.deskriptor.model.Dataset;
import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SddParserGenettaTest {

    private final SddParser parser = new SddParser();
    private final InputStream genettaINputStream = TestUtils.getInputStream("sdd/genetta.sdd.xml");

    private Dataset dataset;
    private boolean parsed = false;

    @Before
    public void setUp() throws Exception {
        if (!parsed) {
            dataset = parser.parse(genettaINputStream);
            parsed = true;
        }
    }

    @Test
    public void should_find_correct_number_of_discrete_descriptor() {

        final Set<Descriptor> descriptors = dataset.getDescriptors();

        final int number_of_discrete_descriptors_in_genetta_file = 45;
        assertThat(descriptors.size()).isEqualTo(number_of_discrete_descriptors_in_genetta_file);
    }

    @Test
    public void should_find_correct_states() {
        final DiscreteDescriptor descriptor = (DiscreteDescriptor) dataset.getDescriptors().stream()
                .filter(d -> d.getName().equals("Shape of crest of insertion of temporal muscles (upper part of the parietal)"))
                .findFirst().get();

        final Set<State> possibleSates = descriptor.getPossibleSates();

        assertThat(possibleSates.size()).isEqualTo(3);
        assertThat(possibleSates).containsOnly(
                State.fromName("large stripe"),
                State.fromName("very narrow, elevated"),
                State.fromName("very narrow stripe, not elevated")
        );
        final State largeStripe = possibleSates.stream().filter(s -> s.getName().equals("large stripe")).findFirst().get();
        final State veryNarrowElevated = possibleSates.stream().filter(s -> s.getName().equals("very narrow, elevated")).findFirst().get();
        final State veryNarrowNotElevated = possibleSates.stream().filter(s -> s.getName().equals("very narrow stripe, not elevated")).findFirst().get();

        assertThat(largeStripe.getId()).isEqualTo("s1");
        assertThat(veryNarrowElevated.getId()).isEqualTo("s2");
        assertThat(veryNarrowNotElevated.getId()).isEqualTo("s3");
    }

    @Test
    public void should_find_correct_number_of_items() {
        final int number_of_items_in_genetta_file = 19;


        final Set<Item> items = dataset.getItems();

        assertThat(items.size()).isEqualTo(number_of_items_in_genetta_file);
    }


}