package ch.burg.deskriptor.engine.model.descriptor;


import ch.burg.deskriptor.engine.model.State;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DescriptorTest {

    @Test
    public void should_be_able_to_build_descriptor_with_builder() {
        final DiscreteDescriptor discreteDescriptor = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleStates(State.fromName("yes"), State.fromName("no"))
                .build();

        final String expectedString = "DiscreteDescriptor(name=tail presence, possibleSates=[State(name=yes), State(name=no)])";
        assertThat(discreteDescriptor.toString()).isEqualTo(expectedString);
    }

    @Test
    public void should_be_able_to_build_numerical_descriptor_with_builder() {
        final DiscreteDescriptor discreteDescriptor = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleStates(State.fromName("yes"), State.fromName("no"))
                .build();

        final String expectedString = "DiscreteDescriptor(name=tail presence, possibleSates=[State(name=yes), State(name=no)])";
        assertThat(discreteDescriptor.toString()).isEqualTo(expectedString);
    }

    @Test
    public void should_be_able_to_build_descriptor_with_no_duplicate_state_using_builder() {
        final DiscreteDescriptor discreteDescriptor = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleStates("yes", "yes", "no")
                .build();

        Assertions.assertThat(discreteDescriptor.getPossibleSates()).hasSize(2);
        final List<String> stateNameList = discreteDescriptor.getPossibleSates().stream()
                .map(s -> s.getName())
                .collect(Collectors.toList());
        assertThat(stateNameList).containsExactly("yes", "no");
    }

}