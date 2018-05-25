package ch.burg.deskriptor.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DescriptorTest {

    @Test
    public void should_be_able_to_build_descriptor_with_builder() {
        final Descriptor descriptor = Descriptor.builder()
                .withName("tail presence")
                .withPossibleStates(new State("yes"), new State("no"))
                .build();

        assertThat(descriptor.toString()).isEqualTo("Descriptor(name=tail presence, possibleSates=[State(name=yes), State(name=no)])");
    }

    @Test
    public void should_be_able_to_build_descriptor_with_no_duplicate_state_using_builder() {
        final Descriptor descriptor = Descriptor.builder()
                .withName("tail presence")
                .withPossibleState(new State("yes"))
                .withPossibleState(new State("yes"))
                .withPossibleState(new State("no"))
        .build();

        assertThat(descriptor.getPossibleSates()).hasSize(2);
    }

}