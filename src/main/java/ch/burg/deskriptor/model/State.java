package ch.burg.deskriptor.model;

import lombok.Getter;

@Getter
public class State {

    private final String name;

    public State(final String name) {
        this.name = name;
    }
}
