package ch.burg.deskriptor.model;

import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;

@Getter
public class Descriptor {

    private final String name;

    private final ImmutableList<State> possibleSates;

    public Descriptor(final String name, final List<State> possibleStates) {
        this.name = name;
        this.possibleSates = ImmutableList.copyOf(possibleStates);
    }
}