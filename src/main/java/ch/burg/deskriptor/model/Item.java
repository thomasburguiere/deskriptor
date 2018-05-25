package ch.burg.deskriptor.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Item {

    private final String name;

    public Item(final Builder builder) {
        this.name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
