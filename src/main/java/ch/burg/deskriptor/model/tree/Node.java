package ch.burg.deskriptor.model.tree;

import ch.burg.deskriptor.model.State;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Node<T extends Treeable> {

    private final T content;
    private final Node<T> parent;
    private final Set<State> inapplicableState;
    private final List<Node<T>> children;

    private Node(final T content,
                 final Node<T> parent,
                 final Set<State> inapplicableState,
                 final List<Node<T>> children) {

        this.content = content;
        this.parent = parent;
        this.inapplicableState = inapplicableState;
        this.children = children;
    }

    public static <T extends Treeable> Node<T> flatTree(final List<T> childrenContent) {

        final List<Node<T>> children = childrenContent.stream()
                .map(content -> new Node<>(
                        content,
                        null,
                        null,
                        null))
                .collect(Collectors.toList());


        return new Node<T>(null, null, null, children);
    }

    public Optional<Node<T>> getNodeContainingContent(final T content) {
        return children.stream().filter(n -> n.content.equals(content)).findFirst();
    }

}
