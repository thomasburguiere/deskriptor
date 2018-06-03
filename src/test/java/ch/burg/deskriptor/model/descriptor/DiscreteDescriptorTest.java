package ch.burg.deskriptor.model.descriptor;

import ch.burg.deskriptor.model.State;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscreteDescriptorTest {

    @Test
    public void should_be_comparable() {
        DiscreteDescriptor dd1 = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleState(State.fromName("yes"))
                .withPossibleState(State.fromName("yes"))
                .withPossibleState(State.fromName("no"))
                .build();

        DiscreteDescriptor dd2 = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleState(State.fromName("no"))
                .withPossibleState(State.fromName("yes"))
                .build();

        assertThat(dd1.equals(dd2)).isTrue();


        dd1 = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleState(State.fromName("yes"))
                .withPossibleState(State.fromName("no"))
                .build();

        dd2 = DiscreteDescriptor.builder()
                .withName("fur color")
                .withPossibleState(State.fromName("yes"))
                .withPossibleState(State.fromName("no"))
                .build();

        assertThat(dd1.equals(dd2)).isFalse();


        dd1 = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleState(State.fromName("yes"))
                .withPossibleState(State.fromName("no"))
                .build();

        dd2 = DiscreteDescriptor.builder()
                .withName("tail presence")
                .withPossibleState(State.fromName("no"))
                .build();

        assertThat(dd1.equals(dd2)).isFalse();
    }


}