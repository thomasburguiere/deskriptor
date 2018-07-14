package ch.burg.deskriptor.engine.service;

import ch.burg.deskriptor.engine.model.Item;
import ch.burg.deskriptor.engine.model.Measure;
import ch.burg.deskriptor.engine.model.State;
import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.engine.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.engine.model.descriptor.NumericalDescriptor;
import ch.burg.deskriptor.engine.model.tree.DescriptorNode;
import lombok.Getter;

import java.util.List;
import java.util.Set;

import static ch.burg.deskriptor.engine.service.DiscriminantPowerService.ScoreMethod.JACCARD;
import static ch.burg.deskriptor.engine.service.DiscriminantPowerService.ScoreMethod.SOKAL_MICHENER;
import static ch.burg.deskriptor.engine.service.DiscriminantPowerService.ScoreMethod.XPER;

public class DiscriminantPowerService {

    public enum ScoreMethod {
        SOKAL_MICHENER, JACCARD, XPER
    }


    public Double calculateDiscriminantPower(final Descriptor descriptor,
                                             final List<Item> items,
                                             final List<DescriptorNode> dependencyNodes,
                                             final ScoreMethod scoreMethod) {
        final double out;
        final int count;

        if (descriptor.isNumerical()) {
            final CalculationResult result =
                    dPowerForNumericalDescriptor((NumericalDescriptor) descriptor, items, dependencyNodes, scoreMethod);
            out = result.out;
            count = result.count;
        } else if (descriptor.isDiscrete()) {
            final CalculationResult result =
                    dPowerForDiscreteDescriptor((DiscreteDescriptor) descriptor, items, dependencyNodes, scoreMethod);
            out = result.out;
            count = result.count;
        } else {
            throw new IllegalStateException("descriptor must be either Discrete or Numerical");
        }


        if (out != 0 && count != 0) {
            // to normalize the number
            return out / count;
        }
        return out;
    }

    private static class CalculationResult {
        private double out = 0;
        private int count = 0;
    }

    private CalculationResult dPowerForNumericalDescriptor(final NumericalDescriptor descriptor,
                                                           final List<Item> items,
                                                           final List<DescriptorNode> dependencyNodes,
                                                           final ScoreMethod scoreMethod) {
        final CalculationResult result = new CalculationResult();
        for (int i1 = 0; i1 < items.size() - 1; i1++) {
            final Item item1 = items.get(i1);
            for (int i2 = i1 + 1; i2 < items.size(); i2++) {
                final Item item2 = items.get(i2);
                final double tmp;
                tmp = compareWithNumericalDescriptor(descriptor, item1, item2
                        , dependencyNodes// , descriptionMatrix, descriptorNodeMap
                        , scoreMethod
                );
                if (tmp >= 0) {
                    result.out += tmp;
                    result.count++;
                }
            }
        }
        return result;
    }

    private CalculationResult dPowerForDiscreteDescriptor(final DiscreteDescriptor descriptor,
                                                          final List<Item> items,
                                                          final List<DescriptorNode> dependencyNodes,
                                                          final ScoreMethod scoreMethod) {
        final CalculationResult result = new CalculationResult();

        if (descriptor.getPossibleSates().isEmpty()) {
            result.out += 0;
            result.count++;
        } else {
            for (int i1 = 0; i1 < items.size() - 1; i1++) {
                final Item item1 = items.get(i1);
                for (int i2 = i1 + 1; i2 < items.size(); i2++) {
                    final Item item2 = items.get(i2);
                    final float tmp =
                            compareWithDiscreteDescriptor(descriptor, item1, item2, scoreMethod, dependencyNodes);
                    if (tmp >= 0) {
                        result.out += tmp;
                        result.count++;
                    }
                }
            }
        }


        return result;
    }

    private static float compareWithDiscreteDescriptor(final DiscreteDescriptor descriptor,
                                                       final Item item1,
                                                       final Item item2,
                                                       final ScoreMethod scoreMethod,
                                                       final List<DescriptorNode> dependencyNodes) {


        if (InapplicabilityCalculator.isDescriptorInapplicableForItems(descriptor, item1, item2).withDependencyNodes(dependencyNodes)) {
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
        final StateOverlap stateOverlap = calculateStateOverlap(possibleSates, selectedStates1, selectedStates2);

        return getScoreFromCommonAbsentAndPresent(scoreMethod, stateOverlap);

    }

    private static StateOverlap calculateStateOverlap(final Set<State> possibleSates,
                                                      final Set<State> selectedStates1,
                                                      final Set<State> selectedStates2) {
        float commonAbsent = 0; // nb of common points which are absent
        float commonPresent = 0; // nb of common points which are present
        float other = 0;
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
        return new StateOverlap(commonAbsent, commonPresent, other);
    }

    @Getter
    private static class StateOverlap {
        private final float commonAbsent; // nb of common points which are absent
        private final float commonPresent; // nb of common points which are present
        private final float other;

        private StateOverlap(final float commonAbsent, final float commonPresent, final float other) {
            this.commonAbsent = commonAbsent;
            this.commonPresent = commonPresent;
            this.other = other;
        }
    }


    private static double compareWithNumericalDescriptor(
            final NumericalDescriptor descriptor,
            final Item item1,
            final Item item2,
            final List<DescriptorNode> dependencyNodes,
            final ScoreMethod scoreMethod
    ) {
        double commonRatio; // ratio of common values which are shared

        if (InapplicabilityCalculator.isDescriptorInapplicableForItems(descriptor, item1, item2).withDependencyNodes(dependencyNodes)) {
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


        return getScoreFromCommonRatio(scoreMethod, commonRatio);

    }

    private static float getScoreFromCommonAbsentAndPresent(final ScoreMethod scoreMethod,
                                                            final StateOverlap stateOverlap) {
        final float commonPresent = stateOverlap.getCommonPresent();
        final float commonAbsent = stateOverlap.getCommonAbsent();
        final float other = stateOverlap.getOther();


        if (scoreMethod == SOKAL_MICHENER) {
            if (commonPresent + commonAbsent + other == 0) {
                return 0;
            }
            return 1 - ((commonPresent + commonAbsent) / (commonPresent + commonAbsent + other));
        }
        if (scoreMethod == JACCARD) {
            if (commonPresent + other == 0) {
                return 0;
            }
            return 1 - (commonPresent / (commonPresent + other));
        }
        if (scoreMethod == XPER) {
            if ((commonPresent == 0) && (other > 0)) {
                return 1;
            }
            return 0;
        }

        throw new IllegalStateException(String.format("unsupported score method: %s", scoreMethod.name()));
    }

    private static double getScoreFromCommonRatio(final ScoreMethod scoreMethod, final double commonRatio) {
        final double out;


        if (scoreMethod == XPER) {
            if ((commonRatio <= 0)) {
                out = 1;
            } else {
                out = 0;
            }
            return out;
        }
        if (scoreMethod == SOKAL_MICHENER || scoreMethod == JACCARD) {
            return 1 - (commonRatio / 100);
        }

        throw new IllegalStateException(String.format("unsupported score method: %s", scoreMethod.name()));
    }
}
