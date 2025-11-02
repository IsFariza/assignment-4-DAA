package org.example.graph;

import org.example.GraphUtils.Edge;
import org.example.GraphUtils.Graph;
import org.example.Metrics;

import java.util.*;

public class PathsDAG {
    public static AlgorithmResult<PathDAGResult> shortestPath(Graph graph, int src, List<Integer> topolOrder, int[] parent) {
        Metrics metrics = new Metrics();
        metrics.startTimer();

        if (graph == null) {
            metrics.stopTimer();
            return new AlgorithmResult<>(new PathDAGResult(new int[0], new int[0]), metrics);
        }
        int size = graph.getSize();

        if (size == 0 || topolOrder == null || parent == null || src < 0 || src >= size) {
            metrics.stopTimer();
            return new AlgorithmResult<>(new PathDAGResult(new int[0], new int[0]), metrics);
        }

        int[] dist = new int[size];
        for (int i = 0; i < size; i++) {
            dist[i] = Integer.MAX_VALUE; //initially the path to all nodes is unknown, so it's infinity
            parent[i] = -1;
        }
        dist[src] = 0;

        //edge relaxation
        for (int u : topolOrder) {
            if (dist[u] != Integer.MAX_VALUE) {
                for (Edge edge : graph.getEdges()) {
                    if (edge.getU() == u) {
                        int v = edge.getV();
                        int weight = edge.getWeight();
                        //since it's shortest path, we need only the edges that less than current
                        if (dist[v] > dist[u] + weight) {
                            dist[v] = dist[u] + weight;
                            parent[v] = u;
                            metrics.relax();
                        }
                    }
                }
            }
        }

        metrics.stopTimer();
        return new AlgorithmResult<>(new PathDAGResult(dist, parent), metrics);
    }


    public static AlgorithmResult<PathDAGResult> longestPath(Graph graph, int src, List<Integer> topolOrder, int[] parent) {
        Metrics metrics = new Metrics();
        metrics.startTimer();

        if (graph == null) {
            metrics.stopTimer();
            return new AlgorithmResult<>(new PathDAGResult(new int[0], new int[0]), metrics);
        }

        int size = graph.getSize();
        if (size == 0 || topolOrder == null || parent == null || src < 0 || src >= size) {
            metrics.stopTimer();
            return new AlgorithmResult<>(new PathDAGResult(new int[0], new int[0]), metrics);
        }

        int[] dist = new int[size];
        for (int i = 0; i < size; i++) {
            dist[i] = Integer.MIN_VALUE;
            parent[i] = -1;
        }
        dist[src] = 0;

        //same logic for relaxation
        for (int u : topolOrder) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (Edge edge : graph.getEdges()) {
                    if (edge.getU() == u) {
                        int v = edge.getV();
                        int weight = edge.getWeight();
                        //but now we need longest path, so update if current edge is shorter
                        if (dist[v] < dist[u] + weight) {
                            dist[v] = dist[u] + weight;
                            parent[v] = u;
                            metrics.relax();
                        }
                    }
                }
            }
        }

        metrics.stopTimer();
        return new AlgorithmResult<>(new PathDAGResult(dist, parent), metrics);
    }

    public static List<Integer> reconstructPath(int src, int target, int[] parent) {
        if (parent.length == 0) return Collections.emptyList();

        //walk backwards from the target to source node (source has no parent, that's why -1)
        List<Integer> path = new ArrayList<>();
        for (int i = target; i != -1; i = parent[i]) {
            path.add(i);
        }
        //now reverse, since we built the path backwards
        Collections.reverse(path);
        if (path.isEmpty() || path.get(0) != src) {
            return Collections.emptyList();
        }
        return path;
    }

    public static class PathDAGResult {
        int[] dist;
        int[] parent;
        public PathDAGResult(int[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
        public int[] getDistances() { return dist; }
        public int[] getParent() { return parent; }
    }

    public static class AlgorithmResult<T> {
        private final T result;
        private final Metrics metrics;
        public AlgorithmResult(T result, Metrics metrics) {
            this.result = result;
            this.metrics = metrics;
        }
        public T getResult() { return result; }
        public Metrics getMetrics() { return metrics; }
    }
}
