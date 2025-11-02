package org.example.graph;

import org.example.GraphUtils.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SCCTest {
    @Test
    void testSimpleSCC() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(3, 4);

        AlgorithmResult<List<List<Integer>>> result = SCC.kosarajuAlgorithm(graph);
        List<List<Integer>> sccList = result.getResult();

        assertEquals(3, sccList.size());
    }

    @Test
    void testSingleNode() {
        Graph graph = new Graph(1);
        AlgorithmResult<List<List<Integer>>> result = SCC.kosarajuAlgorithm(graph);
        List<List<Integer>> sccList = result.getResult();

        List<List<Integer>> expected = new ArrayList<>();
        List<Integer> inner = new ArrayList<>();
        inner.add(0);
        expected.add(inner);

        assertEquals(1, sccList.size());
        assertEquals(expected.get(0), sccList.get(0));
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        graph.addEdge(2, 3);
        graph.addEdge(4, 5);

        AlgorithmResult<List<List<Integer>>> result = SCC.kosarajuAlgorithm(graph);
        List<List<Integer>> sccList = result.getResult();

        //must be 5, because 0->1 and 1->0 form one SCC, and all other nodes (2, 3, 4, 5) are their own SCCs
        assertEquals(5, sccList.size());
    }

    @Test
    void testFullyConnectedGraph() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);

        AlgorithmResult<List<List<Integer>>> result = SCC.kosarajuAlgorithm(graph);
        List<List<Integer>> sccList = result.getResult();

        assertEquals(1, sccList.size());
    }

    @Test
    void testEmptyGraph() {
        Graph graph = new Graph(0);

        AlgorithmResult<List<List<Integer>>> result = SCC.kosarajuAlgorithm(graph);
        List<List<Integer>> sccList = result.getResult();

        assertTrue(sccList.isEmpty());
    }
}
