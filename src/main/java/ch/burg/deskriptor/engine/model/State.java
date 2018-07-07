package ch.burg.deskriptor.engine.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
public class State {

    private final String id;
    private final String name;

    public static State fromName(final String name) {
        return new State(null, name);
    }

    private State(final String id, final String name) {
        this.id = id;
        this.name = name;
    }
}
