package ch.burg.deskriptor.service;

import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.tree.Node;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscriminantPowerServiceTest {


    private final DiscriminantPowerService service = new DiscriminantPowerService();

    @Test
    public void should_calculate_discriminant_power_of_discrete_descriptors() {
        // given
        final State present = new State("present");
        final State absent = new State("absent");
        final DiscreteDescriptor tailPresence = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleStates(present, absent)
                .build();

        final State black = new State("black");
        final State white = new State("white");
        final DiscreteDescriptor furColor = DiscreteDescriptor.builder()
                .withName("fur color")
                .withPossibleStates(white, black)
                .build();

        final Node<Descriptor> dependencyTreeRootNode = Node.flatTree(List.of(tailPresence, furColor));

        final Item blackRat = Item.builder().withName("blackRat")
                .describe(tailPresence).withSelectedStates(present)
                .describe(furColor).withSelectedStates(black)
                .build();


        final Item whiteRat = Item.builder().withName("whiteRat")
                .describe(tailPresence).withSelectedStates(present)
                .describe(furColor).withSelectedStates(white)
                .build();

        final List<Item> items = List.of(blackRat, whiteRat);

        // when
        assertThat(service.calculateDiscriminantPower(tailPresence, items, dependencyTreeRootNode)).isEqualTo(0.0);
        assertThat(service.calculateDiscriminantPower(furColor, items, dependencyTreeRootNode)).isEqualTo(1.0);
    }

}