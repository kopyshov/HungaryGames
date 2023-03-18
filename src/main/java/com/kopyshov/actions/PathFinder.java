package com.kopyshov.actions;

import com.kopyshov.model.GraphNode;

import java.util.LinkedList;

public interface PathFinder {
    LinkedList<GraphNode> findPath();
}
