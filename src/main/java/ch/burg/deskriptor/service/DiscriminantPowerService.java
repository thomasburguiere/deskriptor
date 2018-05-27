package ch.burg.deskriptor.service;

import ch.burg.deskriptor.model.Item;
import ch.burg.deskriptor.model.Measure;
import ch.burg.deskriptor.model.State;
import ch.burg.deskriptor.model.descriptor.Descriptor;
import ch.burg.deskriptor.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.model.descriptor.NumericalDescriptor;
import ch.burg.deskriptor.model.tree.Node;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ch.burg.deskriptor.service.DiscriminantPowerService.ScoreMethod.JACCARD;
import static ch.burg.deskriptor.service.DiscriminantPowerService.ScoreMethod.SOKAL_MICHENER;

public class DiscriminantPowerService {

    public enum ScoreMethod {
        SOKAL_MICHENER, JACCARD, XPER
    }

    public Double calculateDiscriminantPower(final Descriptor descriptor,
                                             final List<Item> items,
                                             final Node<Descriptor> dependencyTreeRootNode,
                                             final ScoreMethod scoreMethod) {
        double out = 0;
        int count = 0;

        if (descriptor.isNumerical()) {
            for (int i1 = 0; i1 < items.size() - 1; i1++) {
                final Item item1 = items.get(i1);
                for (int i2 = i1 + 1; i2 < items.size(); i2++) {
                    final Item item2 = items.get(i2);
                    final double tmp;
                    tmp = compareWithNumericalDescriptor((NumericalDescriptor) descriptor, item1, item2
                            , dependencyTreeRootNode// , descriptionMatrix, descriptorNodeMap
                            , scoreMethod
                    );
                    if (tmp >= 0) {
                        out += tmp;
                        count++;
                    }
                }
            }
        }

        if (descriptor.isDiscrete()) {
            if (((DiscreteDescriptor) descriptor).getPossibleSates().isEmpty()) {
                out += 0;
                count++;
            } else {
                for (int i1 = 0; i1 < items.size() - 1; i1++) {
                    final Item item1 = items.get(i1);
                    for (int i2 = i1 + 1; i2 < items.size(); i2++) {
                        final Item item2 = items.get(i2);
                        final float tmp;
                        tmp = compareWithCategoricalDescriptor((DiscreteDescriptor) descriptor, item1,
                                item2, scoreMethod, dependencyTreeRootNode);
                        if (tmp >= 0) {
                            out += tmp;
                            count++;
                        }
                    }
                }
            }
        }


        if (out != 0 && count != 0) {
            // to normalize the number
            out = out / count;
        }
        return out;
    }

    private static float compareWithCategoricalDescriptor(final DiscreteDescriptor descriptor, final Item item1, final Item item2, final ScoreMethod scoreMethod, final Node<Descriptor> dependencyTreeRootNode) {

        final float out;

        float commonAbsent = 0; // nb of common points which are absent
        float commonPresent = 0; // nb of common points which are present
        float other = 0;


        if (isDescriptorInapplicableForItems(descriptor, item1, item2).withDependencyTree(dependencyTreeRootNode)) {
            return -1;
        }

        item2.isDescriptionUnknownFor(descriptor);


        final Set<State> possibleSates = descriptor.getPossibleSates();
        final Set<State> selectedStates1;
        final Set<State> selectedStates2;

        if (item1.isDescriptionUnknownFor(descriptor)) {
            selectedStates1 = possibleSates;
        } else {
            selectedStates1 = item1.getSelectedStatesFor(descriptor);
        }

        if (item2.isDescriptionUnknownFor(descriptor)) {
            selectedStates2 = possibleSates;
        } else {
            selectedStates2 = item2.getSelectedStatesFor(descriptor);
        }

        for (final State state : possibleSates) {
            if (selectedStates1.contains(state)) {
                if (selectedStates2.contains(state)) {
                    commonPresent++;
                } else {
                    other++;
                }
            } else {
                if (selectedStates2.contains(state)) {
                    other++;
                } else {
                    commonAbsent++;
                }
            }
        }

        if (scoreMethod == SOKAL_MICHENER) {
            if (commonPresent + commonAbsent + other == 0) {
                out = 0;
            } else {
                out = 1 - ((commonPresent + commonAbsent) / (commonPresent + commonAbsent + other));
            }
        } else if (scoreMethod == JACCARD) {
            if (commonPresent + other == 0) {
                out = 0;
            } else {

                out = 1 - (commonPresent / (commonPresent + other));
            }
        }
        // // yes or no method (Xper)
        else {
            if ((commonPresent == 0) && (other > 0)) {
                out = 1;
            } else {
                out = 0;
            }
        }

        return out;
    }


    private static double compareWithNumericalDescriptor(
            final NumericalDescriptor descriptor,
            final Item item1,
            final Item item2,
            final Node<Descriptor> dependencyTreeRootNode,
            final ScoreMethod scoreMethod
    ) {
        final double out;
        double commonRatio; // ratio of common values which are shared

        if (isDescriptorInapplicableForItems(descriptor, item1, item2).withDependencyTree(dependencyTreeRootNode)) {
            return -1;
        }


        final Measure measure1 = item1.getMeasureFor(descriptor);
        final Measure measure2 = item2.getMeasureFor(descriptor);


        if (measure1 == null || measure2 == null) {
            return 0;
        }

        commonRatio = measure1.commonRatioWithAnotherMeasure(measure2);


        if (commonRatio <= 0) {
            commonRatio = 0;
        }

        switch (scoreMethod) {
            case XPER:
                if ((commonRatio <= 0)) {
                    out = 1;
                } else {
                    out = 0;
                }
                break;
            //
            case SOKAL_MICHENER:
                out = 1 - (commonRatio / 100);
                break;
            //
            case JACCARD:
                out = 1 - (commonRatio / 100);
                break;

            default:
                if ((commonRatio <= 0)) {
                    out = 1;
                } else {
                    out = 0;
                }
                break;
        }

        return out;

    }


    private static InapplicabilityCalculator isDescriptorInapplicableForItems(final Descriptor descriptor, final Item... items) {
        return new InapplicabilityCalculator(descriptor, items);
    }

    private static class InapplicabilityCalculator {
        private final Descriptor descriptor;
        private final Item[] items;
        private Node<Descriptor> dependencyTreeRootNode;

        private InapplicabilityCalculator(final Descriptor descriptor, final Item[] items) {
            this.descriptor = descriptor;
            this.items = items;
        }

        private boolean withDependencyTree(final Node<Descriptor> dependencyTreeRootNode) {
            this.dependencyTreeRootNode = dependencyTreeRootNode;
            return calculate();
        }

        private boolean calculate() {
            final Optional<Node<Descriptor>> node = dependencyTreeRootNode.getNodeContainingContent(descriptor);
            if (node.isPresent()) {
                for (final Item item : items) {
                    if (isDescriptorInNodeInapplicableForItem(node.get(), item)) {
                        return true;
                    }
                }
            }
            return false;
        }


        private static boolean isDescriptorInNodeInapplicableForItem(final Node<Descriptor> descriptorNode, final Item item) {
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


}
