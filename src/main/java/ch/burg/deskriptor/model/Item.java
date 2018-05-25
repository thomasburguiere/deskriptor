package ch.burg.deskriptor.model;

import lombok.Getter;

@Getter
public class Item {

    private final String name;

    public Item(final String name) {
        this.name = name;
    }
}
