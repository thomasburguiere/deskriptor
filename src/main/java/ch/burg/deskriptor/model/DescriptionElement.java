package ch.burg.deskriptor.model;

import lombok.Getter;

import java.util.Set;

@Getter
public class DescriptionElement {

    private final Set<State> selectedStates;
    private final Double measure;

    private DescriptionElement(final Set<State> selectedStates, final Double measure) {
        this.selectedStates = selectedStates;
        this.measure = measure;
    }

    public static DescriptionElement ofSelectedStates(final Set<State> states) {
        return new DescriptionElement(states, null);
    }


    public static DescriptionElement ofMeasure(final Double measure) {
        return new DescriptionElement(null, measure);
    }


}
