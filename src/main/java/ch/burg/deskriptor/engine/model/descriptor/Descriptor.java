package ch.burg.deskriptor.engine.model.descriptor;

import ch.burg.deskriptor.engine.model.tree.Treeable;

public interface Descriptor extends Treeable {

    boolean isNumerical();
    boolean isDiscrete();

    String getName();
    String getId();
}
