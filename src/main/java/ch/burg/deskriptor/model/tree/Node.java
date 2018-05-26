package ch.burg.deskriptor.model.tree;

import java.util.List;

public class Node<T extends Treeable> {

    private T content;
    private Node<T> parent;
    private List<Node<T>> children;
}
