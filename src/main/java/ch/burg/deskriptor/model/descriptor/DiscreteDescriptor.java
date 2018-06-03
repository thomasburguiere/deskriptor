package ch.burg.deskriptor.model.descriptor;

import ch.burg.deskriptor.model.State;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class DiscreteDescriptor implements Descriptor {

    private final String id;
    private final String name;

    private final Set<State> possibleSates;

    public DiscreteDescriptor(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.possibleSates = builder.possibleStates;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean isNumerical() {
        return false;
    }

    @Override
    public boolean isDiscrete() {
        return true;
    }

    public static class Builder {
        private String id;
        private String name;
        private Set<State> possibleStates = new HashSet<>();

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withPossibleStates(final String... possibleStatesNames) {

            this.possibleStates = Arrays.stream(possibleStatesNames)
                    .map(State::fromName)
                    .collect(Collectors.toSet());
            return this;
        }

        public Builder withPossibleStates(final State... possibleStates) {
            this.possibleStates = new HashSet<>(Arrays.asList(possibleStates));
            return this;
        }

        public Builder withPossibleState(final State state) {
            this.possibleStates.add(state);
            return this;
        }

        public DiscreteDescriptor build() {
            return new DiscreteDescriptor(this);
        }
    }
}