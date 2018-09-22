package ch.burg.deskriptor.engine.model.tree;

import ch.burg.deskriptor.engine.model.State;
import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor(force = true)
public class DescriptorNode {
    private final Descriptor content;
    private final DescriptorNode parent;
    private final Set<State> inapplicableState;
    private final List<DescriptorNode> children;

    public DescriptorNode(final Descriptor content,
                          final DescriptorNode parent,
                          final Set<State> inapplicableState,
                          final List<DescriptorNode> children) {

        this.content = content;
        this.parent = parent;
        this.inapplicableState = inapplicableState;
        this.children = children;
    }

    public static List<DescriptorNode> flatTree(final Descriptor... childrenContent) {

        return Arrays.stream(childrenContent)
                .map(content -> new DescriptorNode(
                        content,
                        null,
                        null,
                        null))
                .collect(toList());

    }

    public static Optional<DescriptorNode> getNodeContainingContentInList(
            final Descriptor content,
            final List<DescriptorNode> list) {

        return list.stream().filter(n -> n.content.equals(content)).findFirst();
    }
}
