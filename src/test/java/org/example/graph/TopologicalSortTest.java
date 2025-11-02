package org.example.graph;

import org.example.GraphUtils.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortTest {
    @Test
    void testSimpleDAG() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        AlgorithmResult<List<Integer>> result = TopologicalSort.kahnAlgorithm(graph);
        List<Integer> order = result.getResult(); // assuming getResult() returns the list

        assertEquals(5, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        assertTrue(order.indexOf(1) < order.indexOf(3));
        assertTrue(order.indexOf(2) < order.indexOf(3));
        assertTrue(order.indexOf(3) < order.indexOf(4));
    }

    @Test
    void testSingleNode() {
        Graph graph = new Graph(1);

        AlgorithmResult<List<Integer>> result = TopologicalSort.kahnAlgorithm(graph);
        List<Integer> order = result.getResult();

        assertEquals(1, order.size());
        assertEquals(0, order.get(0));
    }

    @Test
    void testDisconnectedDAG() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);

        AlgorithmResult<List<Integer>> result = TopologicalSort.kahnAlgorithm(graph);
        List<Integer> order = result.getResult();

        assertEquals(4, order.size());
        assertTrue(order.contains(0));
        assertTrue(order.contains(1));
        assertTrue(order.contains(2));
        assertTrue(order.contains(3));
    }

    @Test
    void testCycle() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        AlgorithmResult<List<Integer>> result = TopologicalSort.kahnAlgorithm(graph);
        assertNull(result); // must return null when cycle
    }

    @Test
    void testEmptyGraph() {
        Graph graph = new Graph(0);

        AlgorithmResult<List<Integer>> result = TopologicalSort.kahnAlgorithm(graph);
        List<Integer> order = result.getResult();

        assertTrue(order.isEmpty());
    }
}
