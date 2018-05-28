package ch.burg.deskriptor.service;

import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.tree.Node;

import java.util.List;
import java.util.Optional;
import java.util.Set;

class InapplicabilityCalculator {

    private final Descriptor descriptor;
    private final Item[] items;
    private List<Node<Descriptor>> dependencyNodes;

    private InapplicabilityCalculator(final Descriptor descriptor, final Item[] items) {
        this.descriptor = descriptor;
        this.items = items;
    }


    static InapplicabilityCalculator isDescriptorInapplicableForItems(final Descriptor descriptor, final Item... items) {
        return new InapplicabilityCalculator(descriptor, items);
    }

    boolean withDependencyNodes(List<Node<Descriptor>> dependencyNodes) {
        this.dependencyNodes = dependencyNodes;
        return calculate();
    }

    private boolean calculate() {
        final Optional<Node<Descriptor>> node = Node.getNodeContainingContentInList(descriptor, dependencyNodes);
        if (node.isPresent()) {
            for (final Item item : items) {
                if (isDescriptorInNodeInapplicableForItem(node.get(), item)) {
                    return true;
                }
            }
        }
        return false;
    }


    static boolean isDescriptorInNodeInapplicableForItem(final Node<Descriptor> descriptorNode, final Item item) {
        final Node<Descriptor> parentNode = descriptorNode.getParent();

        if (parentNode != null) {
            final Set<State> inapplicableStates = descriptorNode.getInapplicableState();

            final Set<State> parentDescriptorSelectedStates =
                    item.getSelectedStatesFor((DiscreteDescriptor) parentNode.getContent());


            int numberOfRemainingApplicableStates = parentDescriptorSelectedStates.size();


            for (final State inapplicableState : inapplicableStates) {
                if (parentDescriptorSelectedStates.contains(inapplicableState)) {
                    numberOfRemainingApplicableStates--;
                }
            }
            if (numberOfRemainingApplicableStates == 0) {
                return true;
            }

            return isDescriptorInNodeInapplicableForItem(parentNode, item);

        }
        return false;
    }
}
