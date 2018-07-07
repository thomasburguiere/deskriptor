package ch.burg.deskriptor.engine.model;

import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.engine.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.engine.model.descriptor.NumericalDescriptor;
import ch.burg.deskriptor.engine.model.tree.Treeable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ch.burg.deskriptor.engine.model.DescriptionElement.ofMeasure;
import static ch.burg.deskriptor.engine.model.DescriptionElement.ofSelectedStates;
import static java.util.Arrays.asList;

@Getter
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
public class Item implements Treeable {

    private final String id;
    private final String name;
    private final Map<Descriptor, DescriptionElement> description;

    public Item(final ItemBuilder itemBuilder) {
        this.id = itemBuilder.id;
        this.name = itemBuilder.name;
        this.description = itemBuilder.description;
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public Measure getMeasureFor(final NumericalDescriptor numericalDescriptor) {
        return description.get(numericalDescriptor).getMeasure();
    }

    public Set<State> getSelectedStatesFor(final DiscreteDescriptor discreteDescriptor) {
        return description.get(discreteDescriptor).getSelectedStates();
    }

    public boolean isDescriptionUnknownFor(final Descriptor descriptor) {
        return description.get(descriptor).isUnknown();
    }

    public static class ItemBuilder {
        private String id;
        private String name;
        private Map<Descriptor, DescriptionElement> description = new HashMap<>();

        public ItemBuilder withId(final String id) {
            this.id = id;
            return this;
        }
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
            private final DiscreteDescriptor discreteDescriptor;
            public DiscreteDescriptionBuilder(final ItemBuilder parentItemItemBuilder, final DiscreteDescriptor discreteDescriptor) {
                this.parentItemItemBuilder = parentItemItemBuilder;
                this.discreteDescriptor = discreteDescriptor;
            }

            public ItemBuilder withSelectedStates(final State... selectedStates) {
                final DescriptionElement descriptionElement = ofSelectedStates(new HashSet<>(asList(selectedStates)));
                parentItemItemBuilder.description.put(discreteDescriptor, descriptionElement);
                return parentItemItemBuilder;
            }

        }

        public NumericalDescriptorBuilder describe(final NumericalDescriptor numericalDescriptor) {
            return new NumericalDescriptorBuilder(this, numericalDescriptor);
        }

        public static class NumericalDescriptorBuilder {

            private final ItemBuilder parentItemBuilder;
            private final NumericalDescriptor numericalDescriptor;

            public NumericalDescriptorBuilder(final ItemBuilder parentItemBuilder, final NumericalDescriptor numericalDescriptor) {
                this.parentItemBuilder = parentItemBuilder;
                this.numericalDescriptor = numericalDescriptor;
            }

            public ItemBuilder withMeasure(final Measure measure) {
                final DescriptionElement descriptionElement = ofMeasure(measure);
                parentItemBuilder.description.put(numericalDescriptor, descriptionElement);
                return parentItemBuilder;
            }
        }

    }
}
