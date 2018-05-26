package ch.burg.deskriptor.model;


import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    public void should_be_able_to_create_item_with_builder() {
        final Item rat = Item.builder().withName("rat").build();

        assertThat(rat.toString()).isEqualTo("Item(name=rat, description={})");
    }

    @Test
    public void should_be_able_to_describe_item() {
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

        // when

        final Item rat = Item.builder().withName("rat")
                .describe(tailPresence).withSelectedStates(present)
                .describe(furColor).withSelectedStates(white)
            .build();

        // then
        assertThat(rat.getDescription().get(tailPresence).getSelectedStates()).containsExactly(present);
        assertThat(rat.getDescription().get(furColor).getSelectedStates()).containsExactly(white);
    }

}