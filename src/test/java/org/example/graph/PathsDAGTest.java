package org.example.graph;

import org.example.GraphUtils.Graph;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PathsDAGTest {

    @Test
    void testShortestPath() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 4, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 1);
        graph.addEdge(4, 3, 2);

        List<Integer> topolOrder = List.of(0, 1, 2, 4, 3);
        int[] parent = new int[5];

        PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> result = PathsDAG.shortestPath(graph, 0, topolOrder, parent);
        int[] dist = result.getResult().getDistances();

        assertEquals(0, dist[0]);
        assertEquals(5, dist[1]);
        assertEquals(7, dist[2]);
        assertEquals(6, dist[3]); // 0->4->3 = 4+2=6
        assertEquals(4, dist[4]);
    }

    @Test
    void testLongestPath() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 4, 4);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 1);
        graph.addEdge(4, 3, 2);

        List<Integer> topolOrder = List.of(0, 1, 2, 4, 3);
        int[] parent = new int[5];

        PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> result = PathsDAG.longestPath(graph, 0, topolOrder, parent);
        int[] dist = result.getResult().getDistances();

        assertEquals(0, dist[0]);
        assertEquals(5, dist[1]);
        assertEquals(7, dist[2]);
        assertEquals(8, dist[3]); // 0->1->2->3 = 5+2+1=8
        assertEquals(4, dist[4]);
    }

    @Test
    void testSingleNode() {
        Graph graph = new Graph(1);
        List<Integer> topolOrder = List.of(0);
        int[] shortParent = new int[1];
        int[] longParent = new int[1];

        PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> shortPath = PathsDAG.shortestPath(graph, 0, topolOrder, shortParent);
        int[] shortDist = shortPath.getResult().getDistances();

        PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> longPath = PathsDAG.longestPath(graph, 0, topolOrder, longParent);
        int[] longDist = longPath.getResult().getDistances();

        assertEquals(0, shortDist[0]);
        assertEquals(-1, shortParent[0]);
        assertEquals(0, longDist[0]);
        assertEquals(-1, longParent[0]);
    }

    @Test
    void testDisconnectedGraph() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 0, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(4, 5, 4);

        List<Integer> topolOrder = List.of(0, 1, 2, 3, 4, 5);
        int[] parent = new int[6];

        PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> result = PathsDAG.shortestPath(graph, 0, topolOrder, parent);
        int[] dist = result.getResult().getDistances();

        assertEquals(0, dist[0]);
        assertEquals(1, dist[1]);
        assertEquals(Integer.MAX_VALUE, dist[2]);
        assertEquals(Integer.MAX_VALUE, dist[3]);
        assertEquals(Integer.MAX_VALUE, dist[4]);
        assertEquals(Integer.MAX_VALUE, dist[5]);
    }

    @Test
    void testReconstructShort() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 4, 5);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 2);

        List<Integer> topolOrder = List.of(0, 1, 2, 3, 4);
        int[] parent = new int[5];

        PathsDAG.shortestPath(graph, 0, topolOrder, parent);
        List<Integer> path = PathsDAG.reconstructPath(0, 4, parent);

        assertEquals(List.of(0, 2, 3, 4), path);
    }

    @Test
    void testReconstructLong() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 2);
        graph.addEdge(1, 4, 5);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 4, 2);

        List<Integer> topolOrder = List.of(0, 1, 2, 3, 4);
        int[] parent = new int[5];

        PathsDAG.longestPath(graph, 0, topolOrder, parent);
        List<Integer> path = PathsDAG.reconstructPath(0, 4, parent);

        assertEquals(List.of(0, 1, 4), path);
    }

    @Test
    void testEmptyGraph() {
        Graph graph = new Graph(0);
        List<Integer> topolOrder = new ArrayList<>();
        int[] parent = new int[0];

        PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> shortPath = PathsDAG.shortestPath(graph, 0, topolOrder, parent);
        int[] shortDist = shortPath.getResult().getDistances();

        PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> longPath = PathsDAG.longestPath(graph, 0, topolOrder, parent);
        int[] longDist = longPath.getResult().getDistances();

        assertEquals(0, shortDist.length);
        assertEquals(0, longDist.length);

        List<Integer> path = PathsDAG.reconstructPath(0, 0, parent);
        assertTrue(path.isEmpty());
    }
}
