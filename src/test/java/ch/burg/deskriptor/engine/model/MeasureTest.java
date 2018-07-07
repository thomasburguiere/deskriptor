package ch.burg.deskriptor.engine.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MeasureTest {
    @Test
    public void should_have_common_ratio_of_one_third() {
        // given
        final Measure measure1 = Measure.withMin(0).andMax(10);
        final Measure measure2 = Measure.withMin(5).andMax(15);

        assertThat(measure1.commonRatioWithAnotherMeasure(measure2)).isBetween(0.333, 0.334);
        assertThat(measure2.commonRatioWithAnotherMeasure(measure1)).isBetween(0.333, 0.334);
    }


    @Test
    public void should_have_common_ratio_of_zero() {
        // given
        final Measure measure1 = Measure.withMin(0).andMax(5);
        final Measure measure2 = Measure.withMin(6).andMax(8);

        assertThat(measure1.commonRatioWithAnotherMeasure(measure2)).isEqualTo(0);
        assertThat(measure2.commonRatioWithAnotherMeasure(measure1)).isEqualTo(0);
    }

    @Test
    public void should_have_common_ratio_of_one_fifth() {
        // given
        final Measure measure1 = Measure.withMin(0).andMax(5);
        final Measure measure2 = Measure.withMin(2).andMax(3);

        assertThat(measure1.commonRatioWithAnotherMeasure(measure2)).isEqualTo(0.2);
        assertThat(measure2.commonRatioWithAnotherMeasure(measure1)).isEqualTo(0.2);
    }

}