package ch.burg.deskriptor.model.tree;

import ch.burg.deskriptor.model.State;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Getter
public class Node<T extends Treeable> {

    private final T content;
    private final Node<T> parent;
    private final Set<State> inapplicableState;
    private final List<Node<T>> children;

    public Node(final T content,
                final Node<T> parent,
                final Set<State> inapplicableState,
                final List<Node<T>> children) {

        this.content = content;
        this.parent = parent;
        this.inapplicableState = inapplicableState;
        this.children = children;
    }

    @SafeVarargs
    public static <T extends Treeable> List<Node<T>> flatTree(final T ...childrenContent) {

        return Arrays.stream(childrenContent)
                .map(content -> new Node<>(
                        content,
                        null,
                        null,
                        null))
                .collect(toList());

    }

    public static <T extends Treeable> Optional<Node<T>> getNodeContainingContentInList(
            final T content,
            final List<Node<T>> list) {

        return list.stream().filter(n -> n.content.equals(content)).findFirst();
    }

}
