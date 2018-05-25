package ch.burg.deskriptor.model;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
public class Descriptor {

    private final String name;

    private final ImmutableSet<State> possibleSates;

    public Descriptor(final Builder builder) {
        this.name = builder.name;
        this.possibleSates = ImmutableSet.copyOf(builder.possibleStates);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Set<State> possibleStates = new HashSet<>();

        public Builder withName(final String name) {
            this.name = name;
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

        public Descriptor build() {
            return new Descriptor(this);
        }
    }
}