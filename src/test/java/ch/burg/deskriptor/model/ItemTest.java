package ch.burg.deskriptor.model;


import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.descriptor.NumericalDescriptor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    public void should_be_able_to_create_item_with_builder() {
        final Item rat = Item.builder().withName("rat").build();

        assertThat(rat.toString()).isEqualTo("Item(name=rat, description={})");
    }

    @Test
    public void should_be_able_to_describe_item_with_discrete_descriptor() {
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
        assertThat(rat.getSelectedStatesFor(tailPresence)).containsExactly(present);
        assertThat(rat.getSelectedStatesFor(furColor)).containsExactly(white);
    }

    @Test
    public void should_be_able_to_describe_item_with_numerical_descriptor() {
        // given

        final NumericalDescriptor tailLength = NumericalDescriptor.builder()
                .withName("tailLength")
                .withMeasurementUnit("cm")
                .build();

        // when
        final Item rat = Item.builder().withName("rat")
                .describe(tailLength).withMeasure(3.1)
                .build();

        // then
        assertThat(rat.getMeasureFor(tailLength)).isEqualTo(3.1);
    }

}