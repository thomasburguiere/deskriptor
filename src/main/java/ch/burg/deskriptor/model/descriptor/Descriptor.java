package ch.burg.deskriptor.model.descriptor;

import ch.burg.deskriptor.model.tree.Treeable;

public interface Descriptor extends Treeable {
    boolean isNumerical();
    boolean isDiscrete();
    String getName();
}
