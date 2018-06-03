package ch.burg.deskriptor.service;

import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.Measure;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.descriptor.NumericalDescriptor;
import ch.burg.deskriptor.model.tree.Node;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static ch.burg.deskriptor.service.DiscriminantPowerService.ScoreMethod.JACCARD;
import static ch.burg.deskriptor.service.DiscriminantPowerService.ScoreMethod.SOKAL_MICHENER;
import static ch.burg.deskriptor.service.DiscriminantPowerService.ScoreMethod.XPER;
import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscriminantPowerServiceTest {

    private final NumericalDescriptor tailLength = NumericalDescriptor.builder()
            .withName("tailLength")
            .withMeasurementUnit("cm")
            .build();


    private final State black = State.fromName("black");
    private final State white = State.fromName("white");
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

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_it_allows_to_discriminate_two_items_when_method_is_jaccard() {
        // given

        final Item blackRat = Item.builder().withName("blackRat")
                .describe(furColor).withSelectedStates(black)
                .build();


        final Item whiteRat = Item.builder().withName("whiteRat")
                .describe(furColor).withSelectedStates(white)
                .build();


        final List<Item> items = List.of(blackRat, whiteRat);

        // when
        assertThat(service.calculateDiscriminantPower(furColor, items, dependencyTreeNodes, JACCARD)).isEqualTo(1.0);
    }

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_it_doesnt_allow_to_discriminate_two_items_when_method_is_jaccard() {
        // given

        final Item blackRat1 = Item.builder().withName("blackRat1")
                .describe(furColor).withSelectedStates(black)
                .build();


        final Item blackRat2 = Item.builder().withName("blackRat2")
                .describe(furColor).withSelectedStates(black)
                .build();

        final List<Item> items = List.of(blackRat1, blackRat2);

        // when
        assertThat(service.calculateDiscriminantPower(furColor, items, dependencyTreeNodes, JACCARD)).isEqualTo(0.0);
    }

    @Test
    public void should_return_score_of_1_for_discrete_descriptor_if_measures_dont_intersect_for_two_items_when_method_is_jaccard() {
        // given

        final Item rat1 = Item.builder().withName("rat1")
                .describe(tailLength).withMeasure(Measure.withMin(2).andMax(4))
                .build();


        final Item rat2 = Item.builder().withName("rat2")
                .describe(tailLength).withMeasure(Measure.withMin(5.5).andMax(6))
                .build();

        final List<Item> items = List.of(rat1, rat2);

        // when
        assertThat(service.calculateDiscriminantPower(tailLength, items, dependencyTreeNodes, JACCARD)).isEqualTo(1.0);
    }

    @Test
    public void should_return_score_of_0_for_discrete_descriptor_if_measures_intersect_for_two_items_when_method_is_jaccard() {
        // given

        final Item rat1 = Item.builder().withName("rat1")
                .describe(tailLength).withMeasure(Measure.withMin(2).andMax(4))
                .build();


        final Item rat2 = Item.builder().withName("rat2")
                .describe(tailLength).withMeasure(Measure.withMin(3.5).andMax(6))
                .build();

        final List<Item> items = List.of(rat1, rat2);

        // when
        final Double discriminantPower = service.calculateDiscriminantPower(tailLength, items, dependencyTreeNodes, JACCARD);
        assertThat(discriminantPower).isGreaterThan(0.99874);
        assertThat(discriminantPower).isLessThan(0.99876);
    }


    @Test
    public void should_throw_an_exception_when_calculating_dp_for_descriptor_which_is_neither_discrete_nor_numerical() {
        final Descriptor anotherTypeOfDescriptor = new Descriptor() {
            @Override
            public boolean isNumerical() {
                return false;
            }

            @Override
            public boolean isDiscrete() {
                return false;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getId() {
                return null;
            }
        };
        try {
            service.calculateDiscriminantPower(anotherTypeOfDescriptor, EMPTY_LIST, dependencyTreeNodes, XPER);
            Assert.fail("IllegalStateException should have been thrown");
        } catch (final IllegalStateException ignored) {
        }
    }
}