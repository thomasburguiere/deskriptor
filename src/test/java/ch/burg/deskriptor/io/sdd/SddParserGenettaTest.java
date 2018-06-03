package ch.burg.deskriptor.io.sdd;

import ch.burg.deskriptor.TestUtils;
import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import org.junit.Test;

import java.io.InputStream;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SddParserGenettaTest {

    private final SddParser parser = new SddParser();



    @Test
    public void should_parse_and_find_correct_number_of_discrete_descriptor() throws Exception {

        final InputStream inputStream = TestUtils.getInputStream("sdd/genetta.sdd.xml");


        final Set<Descriptor> descriptors = parser.parse(inputStream).getDescriptors();

        final int number_of_discrete_descriptors_in_genetta_file = 45;
        assertThat(descriptors.size()).isEqualTo(number_of_discrete_descriptors_in_genetta_file);
    }

    @Test
    public void should_parse_and_find_correct_number_of_items() throws Exception {
        final int number_of_items_in_genetta_file = 19;

        final InputStream inputStream = TestUtils.getInputStream("sdd/genetta.sdd.xml");


        final Set<Item> items = parser.parse(inputStream).getItems();

        assertThat(items.size()).isEqualTo(number_of_items_in_genetta_file);
    }


}