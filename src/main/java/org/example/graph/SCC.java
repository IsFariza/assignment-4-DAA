package org.example.graph;

import org.example.GraphUtils.Graph;
import org.example.Metrics;

import java.util.*;


public class SCC {
    public static AlgorithmResult<List<List<Integer>>> kosarajuAlgorithm(Graph graph){
        Metrics metrics = new Metrics();
        metrics.reset();
        metrics.startTimer();
        int size = graph.getSize();
        Set<Integer> visited = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>(); //stores nodes in the order of finishing time from the first DFS

        for(int i=0; i<size; i++){//visit each unvisited node
            if(!visited.contains(i)){
                dfsOriginalGraph(graph, i, visited, stack, metrics);
            }
        }

        Graph transposed = graph.getTranspose(); //reverse the edges
        visited.clear();

        List<List<Integer>> sccList = new ArrayList<>();

        //so now we process nodes in decreasing order of finishing time, like in stack
        while(!stack.isEmpty()){
            int v = stack.pop();
            metrics.stackPush();
            if(!visited.contains(v)){
                List<Integer> components = new ArrayList<>();
                dfsTransposedGraph(transposed,v, visited, components, metrics);
                sccList.add(components);
            }
        }
        metrics.stopTimer();

        return new AlgorithmResult<>(sccList, metrics);
    }
    public static void dfsOriginalGraph(Graph graph, int v, Set<Integer> visited, Deque<Integer> stack, Metrics metrics) {
        visited.add(v);
        metrics.dfsVisit();
        for (int u : graph.getNeighbours(v)) {
            metrics.dfsEdge();
            if (!visited.contains(u)) {
                dfsOriginalGraph(graph, u, visited, stack, metrics);
            }
        }
        stack.push(v);
        metrics.stackPush();
    }
    public static void dfsTransposedGraph(Graph graph, int start, Set<Integer> visited, List<Integer> component, Metrics metrics) {
        visited.add(start);
        metrics.dfsVisit();
        for (int neighbour : graph.getNeighbours(start)) {
            metrics.dfsEdge();
            if (!visited.contains(neighbour)) {
                dfsTransposedGraph(graph, neighbour, visited, component, metrics);
            }
        }

        component.add(start);
        metrics.stackPush();
    }

}
