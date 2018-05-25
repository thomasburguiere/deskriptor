package ch.burg.deskriptor.model;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
public class Item {

    private final String name;
    private final Map<Descriptor, Set<State>> description;

    public Item(final ItemBuilder itemBuilder) {
        this.name = itemBuilder.name;
        this.description = itemBuilder.description;
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static class ItemBuilder {
        private String name;
        private Map<Descriptor, Set<State>> description = new HashMap<>();

        public ItemBuilder withName(final String name) {
            this.name = name;
            return this;
        }

        public Item build() {
            return new Item(this);
        }

        public DescriptionBuilder describe(final Descriptor descriptor) {
            return new DescriptionBuilder(this, descriptor);
        }

        public static class DescriptionBuilder {
            private final ItemBuilder parentItemItemBuilder;
            private final Descriptor descriptor;

            public DescriptionBuilder(final ItemBuilder parentItemItemBuilder, final Descriptor descriptor) {
                this.parentItemItemBuilder = parentItemItemBuilder;
                this.descriptor = descriptor;
            }

            public ItemBuilder withSelectedStates(final State... selectedStates) {
                parentItemItemBuilder.description.put(descriptor, new HashSet<>(Arrays.asList(selectedStates)));
                return parentItemItemBuilder;
            }
        }
    }

}
