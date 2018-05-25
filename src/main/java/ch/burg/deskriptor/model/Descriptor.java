package ch.burg.deskriptor.model;

import lombok.Getter;

@Getter
public class Descriptor {

    private final String name;

    public Descriptor(final String name) {
        this.name = name;
    }
}