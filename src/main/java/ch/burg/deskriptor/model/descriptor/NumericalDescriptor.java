package ch.burg.deskriptor.model.descriptor;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NumericalDescriptor implements Descriptor {

    private final String name;
    private final String measurementUnit;

    public NumericalDescriptor(final Builder builder) {
        this.name = builder.name;
        this.measurementUnit = builder.measurementUnit;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean isNumerical() {
        return true;
    }

    @Override
    public boolean isDiscrete() {
        return false;
    }

    public static class Builder {
        private String name;
        private String measurementUnit;

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withMeasurementUnit(final String measurementUnit) {
            this.measurementUnit = measurementUnit;
            return this;
        }

        public NumericalDescriptor build() {
            return new NumericalDescriptor(this);
        }
    }
}