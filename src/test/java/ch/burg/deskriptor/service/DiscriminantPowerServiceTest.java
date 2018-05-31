package ch.burg.deskriptor.service;

import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.Measure;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.descriptor.NumericalDescriptor;
import ch.burg.deskriptor.model.tree.Node;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static ch.burg.deskriptor.service.DiscriminantPowerService.ScoreMethod.SOKAL_MICHENER;
import static ch.burg.deskriptor.service.DiscriminantPowerService.ScoreMethod.XPER;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscriminantPowerServiceTest {

    private final NumericalDescriptor tailLength = NumericalDescriptor.builder()
            .withName("tailLength")
            .withMeasurementUnit("cm")
            .build();


    private final State black = new State("black");
    private final State white = new State("white");
    private final DiscreteDescriptor furColor = DiscreteDescriptor.builder()
            .withName("fur color")
            .withPossibleStates(white, black)
            .build();

    private final List<Node<Descriptor>> dependencyTreeNodes = Node.flatTree(furColor, tailLength);


    private final DiscriminantPowerService service = new DiscriminantPowerService();

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_it_allows_to_discriminate_two_items_when_method_is_xper() {
        // given

        final Item blackRat = Item.builder().withName("blackRat")
                .describe(furColor).withSelectedStates(black)
                .build();


        final Item whiteRat = Item.builder().withName("whiteRat")
                .describe(furColor).withSelectedStates(white)
                .build();


        final List<Item> items = List.of(blackRat, whiteRat);

        // when
        assertThat(service.calculateDiscriminantPower(furColor, items, dependencyTreeNodes, XPER)).isEqualTo(1.0);
    }

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_it_doesnt_allow_to_discriminate_two_items_when_method_is_xper() {
        // given

        final Item blackRat1 = Item.builder().withName("blackRat1")
                .describe(furColor).withSelectedStates(black)
                .build();


        final Item blackRat2 = Item.builder().withName("blackRat2")
                .describe(furColor).withSelectedStates(black)
                .build();

        final List<Item> items = List.of(blackRat1, blackRat2);

        // when
        assertThat(service.calculateDiscriminantPower(furColor, items, dependencyTreeNodes, XPER)).isEqualTo(0.0);
    }

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_measures_dont_intersect_for_two_items_when_method_is_xper() {
        // given

        final Item rat1 = Item.builder().withName("rat1")
                .describe(tailLength).withMeasure(Measure.withMin(2).andMax(4))
                .build();


        final Item rat2 = Item.builder().withName("rat2")
                .describe(tailLength).withMeasure(Measure.withMin(5.5).andMax(6))
                .build();

        final List<Item> items = List.of(rat1, rat2);

        // when
        assertThat(service.calculateDiscriminantPower(tailLength, items, dependencyTreeNodes, XPER)).isEqualTo(1.0);
    }

    @Test
    public void should_return_score_of_0_for_discrete_descriptor_if_measures_intersect_for_two_items_when_method_is_xper() {
        // given

        final Item rat1 = Item.builder().withName("rat1")
                .describe(tailLength).withMeasure(Measure.withMin(2).andMax(4))
                .build();


        final Item rat2 = Item.builder().withName("rat2")
                .describe(tailLength).withMeasure(Measure.withMin(3.5).andMax(6))
                .build();

        final List<Item> items = List.of(rat1, rat2);

        // when
        assertThat(service.calculateDiscriminantPower(tailLength, items, dependencyTreeNodes, XPER)).isEqualTo(0.0);
    }

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_it_allows_to_discriminate_two_items_when_method_is_sokal() {
        // given

        final Item blackRat = Item.builder().withName("blackRat")
                .describe(furColor).withSelectedStates(black)
                .build();


        final Item whiteRat = Item.builder().withName("whiteRat")
                .describe(furColor).withSelectedStates(white)
                .build();


        final List<Item> items = List.of(blackRat, whiteRat);

        // when
        assertThat(service.calculateDiscriminantPower(furColor, items, dependencyTreeNodes, SOKAL_MICHENER)).isEqualTo(1.0);
    }

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_it_doesnt_allow_to_discriminate_two_items_when_method_is_sokal() {
        // given

        final Item blackRat1 = Item.builder().withName("blackRat1")
                .describe(furColor).withSelectedStates(black)
                .build();


        final Item blackRat2 = Item.builder().withName("blackRat2")
                .describe(furColor).withSelectedStates(black)
                .build();

        final List<Item> items = List.of(blackRat1, blackRat2);

        // when
        assertThat(service.calculateDiscriminantPower(furColor, items, dependencyTreeNodes, SOKAL_MICHENER)).isEqualTo(0.0);
    }

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_measures_dont_intersect_for_two_items_when_method_is_sokal() {
        // given

        final Item rat1 = Item.builder().withName("rat1")
                .describe(tailLength).withMeasure(Measure.withMin(2).andMax(4))
                .build();


        final Item rat2 = Item.builder().withName("rat2")
                .describe(tailLength).withMeasure(Measure.withMin(5.5).andMax(6))
                .build();

        final List<Item> items = List.of(rat1, rat2);

        // when
        assertThat(service.calculateDiscriminantPower(tailLength, items, dependencyTreeNodes, SOKAL_MICHENER)).isEqualTo(1.0);
    }

    @Test
    public void should_return_score_of_0_for_discrete_descriptor_if_measures_intersect_for_two_items_when_method_is_sokal() {
        // given

        final Item rat1 = Item.builder().withName("rat1")
                .describe(tailLength).withMeasure(Measure.withMin(2).andMax(4))
                .build();


        final Item rat2 = Item.builder().withName("rat2")
                .describe(tailLength).withMeasure(Measure.withMin(3).andMax(5))
                .build();

        final List<Item> items = List.of(rat1, rat2);

        // when
        assertThat(service.calculateDiscriminantPower(tailLength, items, dependencyTreeNodes, SOKAL_MICHENER)).isGreaterThan(0.9966);
        assertThat(service.calculateDiscriminantPower(tailLength, items, dependencyTreeNodes, SOKAL_MICHENER)).isLessThan(0.9967);
    }
}