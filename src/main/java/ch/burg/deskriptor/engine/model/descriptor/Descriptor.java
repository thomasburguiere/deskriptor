package ch.burg.deskriptor.engine.model.descriptor;


public interface Descriptor {

    boolean isNumerical();

    boolean isDiscrete();

    String getName();

    String getId();
}
