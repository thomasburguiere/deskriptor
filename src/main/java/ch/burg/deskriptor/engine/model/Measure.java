package ch.burg.deskriptor.engine.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Measure {
    private final Double min;
    private final Double max;

    public static Measure ofMinAndMax(final Double minAndMax) {
        return new Measure(minAndMax, minAndMax);
    }

    public static IntermediateBuilder withMin(final double min) {
        return new IntermediateBuilder(min);
    }

    public double commonRatioWithAnotherMeasure(final Measure another) {
        final double minLowerTmp;
        final double maxUpperTmp;
        final double minUpperTmp;
        final double maxLowerTmp;
        double res;

        if (this.min <= another.min) {
            minLowerTmp = this.min;
            minUpperTmp = another.min;
        } else {
            minLowerTmp = another.min;
            minUpperTmp = this.min;
        }

        if (this.max >= another.max) {
            maxUpperTmp = this.max;
            maxLowerTmp = another.max;
        } else {
            maxUpperTmp = another.max;
            maxLowerTmp = this.max;
        }

        res = (maxLowerTmp - minUpperTmp) / (maxUpperTmp - minLowerTmp);

        if (res < 0) {
            res = 0;
        }
        return res;
    }


    private Measure(final Double min, final Double max) {
        this.min = min;
        this.max = max;
    }

    public static class IntermediateBuilder {
        private final double min;

        public IntermediateBuilder(final double min) {
            this.min = min;
        }

        public Measure andMax(final double max) {
            return new Measure(min, max);
        }
    }
}