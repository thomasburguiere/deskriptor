package ch.burg.deskriptor.model;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.ToString;

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
        private State[] possibleStates;

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withPossibleStates(final State... possibleStates) {
            this.possibleStates = possibleStates;
            return this;
        }

        public Descriptor build() {
            return new Descriptor(this);
        }
    }
}