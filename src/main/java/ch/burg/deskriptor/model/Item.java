package ch.burg.deskriptor.model;

import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ch.burg.deskriptor.model.DescriptionElement.ofSelectedStates;
import static java.util.Arrays.asList;

@Getter
@ToString
public class Item {

    private final String name;
    private final Map<Descriptor, DescriptionElement> description;

    public Item(final ItemBuilder itemBuilder) {
        this.name = itemBuilder.name;
        this.description = itemBuilder.description;
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public static class ItemBuilder {
        private String name;
        private Map<Descriptor, DescriptionElement> description = new HashMap<>();

        public ItemBuilder withName(final String name) {
            this.name = name;
            return this;
        }

        public Item build() {
            return new Item(this);
        }

        public DiscreteDescriptionBuilder describe(final DiscreteDescriptor descriptor) {
            return new DiscreteDescriptionBuilder(this, descriptor);
        }

        public static class DiscreteDescriptionBuilder {
            private final ItemBuilder parentItemItemBuilder;
            private final DiscreteDescriptor descriptor;

            public DiscreteDescriptionBuilder(final ItemBuilder parentItemItemBuilder, final DiscreteDescriptor descriptor) {
                this.parentItemItemBuilder = parentItemItemBuilder;
                this.descriptor = descriptor;
            }

            public ItemBuilder withSelectedStates(final State... selectedStates) {
                final DescriptionElement descriptionElement = ofSelectedStates(new HashSet<>(asList(selectedStates)));
                parentItemItemBuilder.description.put(descriptor, descriptionElement);
                return parentItemItemBuilder;
            }
        }
    }

}
