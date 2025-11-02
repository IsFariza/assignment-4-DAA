package org.example.graph;

import org.example.GraphUtils.Graph;

import java.util.*;

public class Condensation {

    public static Graph buildCondensationGraph(Graph graph, List<List<Integer>> sccList) {
        if (graph == null || graph.getSize() == 0 || sccList == null || sccList.isEmpty()) {
            return new Graph(0);
        }
        //map vertex to its SCC, maps original edges (between vertices) into new edges (between SCCs)
        Map<Integer, Integer> vertexToSCC = new HashMap<>();
        if (sccList.isEmpty()) {
            return new Graph(0);
        }
        for (int i = 0; i < sccList.size(); i++) {
            for (int v : sccList.get(i)) {
                vertexToSCC.put(v, i);
            }
        }

        Graph condensationGraph = new Graph(sccList.size());
        Set<String> addedEdges = new HashSet<>();

        for (int u = 0; u < graph.getSize(); u++) {
            Integer sccU = vertexToSCC.get(u);
            if (sccU == null) continue;
            for (int v : graph.getNeighbours(u)) {
                Integer sccV = vertexToSCC.get(v);
                if (!sccU.equals(sccV)) {
                    String edgeKey = sccU + "-" + sccV;
                    if (!addedEdges.contains(edgeKey)) {
                        condensationGraph.addEdge(sccU, sccV);
                        addedEdges.add(edgeKey);
                    }
                }
            }
        }

        return condensationGraph;
    }
}

