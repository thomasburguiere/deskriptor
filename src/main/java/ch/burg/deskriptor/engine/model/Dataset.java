package ch.burg.deskriptor.engine.model;

import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.engine.model.tree.Node;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class Dataset {

    private final Set<Descriptor> descriptors;
    private final Set<Item> items;

    private final List<Node<Descriptor>> descriptorDependencyNodes;

    public Dataset(final Set<Descriptor> descriptors,
                   final Set<Item> items,
                   final List<Node<Descriptor>> descriptorDependencyNodes) {
        this.descriptors = descriptors;
        this.items = items;
        this.descriptorDependencyNodes = descriptorDependencyNodes;
    }

}
