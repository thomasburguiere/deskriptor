package ch.burg.deskriptor.engine.model;

import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.engine.model.tree.DescriptorNode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(force = true)
public class Dataset {

    private final Set<Descriptor> descriptors;
    private final Set<Item> items;

    private final List<DescriptorNode> descriptorDependencyNodes;

    public Dataset(final Set<Descriptor> descriptors,
                   final Set<Item> items,
                   final List<DescriptorNode> descriptorDependencyNodes) {
        this.descriptors = descriptors;
        this.items = items;
        this.descriptorDependencyNodes = descriptorDependencyNodes;
    }

}
