package ch.burg.deskriptor.engine.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString(exclude = {"id"})
@EqualsAndHashCode(exclude = {"id"})
@NoArgsConstructor(force = true)
public class State {

    private final String id;
    private final String name;

    private State(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static State fromName(final String name) {
        return new State(null, name);
    }
}
