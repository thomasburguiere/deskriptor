package ch.burg.deskriptor.engine.model.descriptor;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumericalDescriptorTest {

    @Test
    public void should_be_comparable() {
        NumericalDescriptor nd1 = NumericalDescriptor.builder().withName("a").withMeasurementUnit("cm").build();
        NumericalDescriptor nd2 = NumericalDescriptor.builder().withName("a").withMeasurementUnit("cm").build();

        assertThat(nd1.equals(nd2)).isTrue();


        nd1 = NumericalDescriptor.builder().withName("a").withMeasurementUnit("mm").build();
        nd2 = NumericalDescriptor.builder().withName("a").withMeasurementUnit("cm").build();

        assertThat(nd1.equals(nd2)).isFalse();


        nd1 = NumericalDescriptor.builder().withName("a").withMeasurementUnit("cm").build();
        nd2 = NumericalDescriptor.builder().withName("b").withMeasurementUnit("cm").build();

        assertThat(nd1.equals(nd2)).isFalse();
    }

}