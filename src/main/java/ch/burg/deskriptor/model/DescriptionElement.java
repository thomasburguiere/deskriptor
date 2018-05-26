package ch.burg.deskriptor.model;

import lombok.Getter;

import java.util.Set;

@Getter
public class DescriptionElement {

    private final Set<State> selectedStates;
    private final Measure measure;
    private final boolean unknown;


    private DescriptionElement(final Set<State> selectedStates, final Measure measure, final boolean unknown) {
        this.selectedStates = selectedStates;
        this.measure = measure;
        this.unknown = unknown;
    }

    public static DescriptionElement ofSelectedStates(final Set<State> states) {
        return new DescriptionElement(states, null, false);
    }

    public static DescriptionElement ofMeasure(final Measure measure) {
        return new DescriptionElement(null, measure, false);
    }

    public static DescriptionElement unknown(){
        return new DescriptionElement(null, null, true);
    }


}
