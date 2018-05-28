package ch.burg.deskriptor.service;

import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.Measure;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.descriptor.NumericalDescriptor;
import ch.burg.deskriptor.model.tree.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InapplicabilityCalculatorTest {


    private final State present = new State("present");
    private final State absent = new State("absent");
    private final DiscreteDescriptor tailPresence = DiscreteDescriptor.builder().withName("tailPresence")
            .withPossibleStates(present, absent)
            .build();

    private final NumericalDescriptor tailLength = NumericalDescriptor.builder().withName("tailLength")
            .withMeasurementUnit("cm")
            .build();

    private final Node<Descriptor> tailPresenceNode = new Node<>(tailPresence, null, null, new ArrayList<>());
    private final Node<Descriptor> tailLengthNode = new Node<>(tailLength, tailPresenceNode, Set.of(absent), null);


    private final Item normalRat1 = Item.builder()
            .withName("normalRat1")
            .describe(tailPresence).withSelectedStates(present)
            .describe(tailLength).withMeasure(Measure.withMin(1).andMax(5))
            .build();

    private final Item normalRat2 = Item.builder()
            .withName("normalRat2")
            .describe(tailPresence).withSelectedStates(present)
            .describe(tailLength).withMeasure(Measure.withMin(4).andMax(7))
            .build();

    private final Item tailLessRat = Item.builder()
            .withName("tailLessRat")
            .describe(tailPresence)
            .withSelectedStates(absent)
            .build();

    @Before
    public void beforeEach() {
        tailPresenceNode.getChildren().add(tailLengthNode);
    }

    @Test
    public void should_calculate_that_a_descriptor_is_inapplicable_for_single_item() {

        // given
        final Item testedItem = tailLessRat;

        // when
        final boolean isInapplicable = InapplicabilityCalculator.isDescriptorInNodeInapplicableForItem(tailLengthNode, testedItem);

        // then
        assertThat(isInapplicable).isTrue();
    }

    @Test
    public void should_calculate_that_a_descriptor_is_applicable_for_single_item() {

        // given
        final Item testedItem = normalRat1;

        // when
        final boolean isInapplicable = InapplicabilityCalculator.isDescriptorInNodeInapplicableForItem(tailLengthNode, testedItem);

        // then
        assertThat(isInapplicable).isFalse();
    }

    @Test
    public void should_calculate_that_a_descriptor_is_inapplicable_for_two_items_if_its_inapplicable_for_one_of_them() {

        // given
        final Item itemWhereApplicable = normalRat1;
        final Item itemWhereInapplicable = tailLessRat;

        // when
        final boolean isInapplicable =
                InapplicabilityCalculator.isDescriptorInapplicableForItems(tailLength, itemWhereApplicable, itemWhereInapplicable)
                        .withDependencyNodes(List.of(tailLengthNode, tailPresenceNode));

        // then
        assertThat(isInapplicable).isTrue();
    }

    @Test
    public void should_calculate_that_a_descriptor_is_applicable_for_two_items_if_its_applicable_for_each_of_them() {

        // given
        final Item itemWhereApplicable1 = normalRat1;
        final Item itemWhereApplicable2 = normalRat2;

        // when
        final boolean isInapplicable =
                InapplicabilityCalculator.isDescriptorInapplicableForItems(tailLength, itemWhereApplicable1, itemWhereApplicable2)
                        .withDependencyNodes(List.of(tailLengthNode, tailPresenceNode));

        // then
        assertThat(isInapplicable).isFalse();
    }

}