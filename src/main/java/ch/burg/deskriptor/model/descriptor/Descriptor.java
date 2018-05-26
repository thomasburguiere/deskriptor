package ch.burg.deskriptor.model.descriptor;

import ch.burg.deskriptor.model.Treeable;

public interface Descriptor extends Treeable {
    boolean isNumerical();
    boolean isDiscrete();
}
