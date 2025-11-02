

package org.example.IOHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.GraphUtils.Graph;
import org.example.graph.SCC;
import org.example.graph.Condensation;
import org.example.graph.TopologicalSort;
import org.example.graph.PathsDAG;
import org.example.graph.AlgorithmResult;

import java.io.FileWriter;
import java.util.*;

public class OutputWriter {

    public static void writeAllGraphsOutput(String outputPath) {
        List<Graph> graphs = InputReader.jsonReadInput();

        if (graphs.isEmpty()) {
            System.out.println("No graphs");
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Map<String, Object>> outputList = new ArrayList<>();

        try {
            for (Graph graph : graphs) {
                Map<String, Object> graphData = new LinkedHashMap<>();

                if (graph == null || graph.getSize() == 0) {
                    graphData.put("graph_id", graph.getId());
                    outputList.add(graphData);
                    continue;
                }

                graphData.put("graph_id", graph.getId());
                graphData.put("vertices", graph.getSize());
                graphData.put("directed", graph.isDirected());
                graphData.put("source_vertex", graph.getSource());

                Map<String, Object> metricsData = new LinkedHashMap<>();

                AlgorithmResult<List<List<Integer>>> sccResult = SCC.kosarajuAlgorithm(graph);
                metricsData.put("SCC_metrics", sccResult.getMetrics().toMap());
                graphData.put("strongly_connected_components", sccResult.getResult());

                Graph condensationGraph = Condensation.buildCondensationGraph(graph, sccResult.getResult());

                AlgorithmResult<List<Integer>> topoResult = TopologicalSort.kahnAlgorithm(condensationGraph);

                metricsData.put("TopologicalSort_metrics", topoResult.getMetrics().toMap());
                graphData.put("topological_order", topoResult.getResult());


                List<Integer> topoOrder = topoResult.getResult();
                if (!topoOrder.isEmpty()) {
                    int source = condensationGraph.getSource();
                    int[] parent = new int[condensationGraph.getSize()];

                    PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> shortestResult =
                            PathsDAG.shortestPath(condensationGraph, source, topoOrder, parent);
                    graphData.put("shortest_path_distances", shortestResult.getResult().getDistances());

                    metricsData.put("ShortestPath_metrics", shortestResult.getMetrics().toMap());
                    graphData.put("shortest_path_distances", shortestResult.getResult().getDistances());

                    PathsDAG.AlgorithmResult<PathsDAG.PathDAGResult> longestResult =
                            PathsDAG.longestPath(condensationGraph, source, topoOrder, parent);
                    metricsData.put("LongestPath_metrics", longestResult.getMetrics().toMap());
                    graphData.put("longest_path_distances", longestResult.getResult().getDistances());
                } else {
                    graphData.put("shortest_path_distances", new int[0]);
                    graphData.put("longest_path_distances", new int[0]);
                }

                graphData.put("metrics", metricsData);
                outputList.add(graphData);

            }


            try (FileWriter writer = new FileWriter(outputPath)) {
                gson.toJson(outputList, writer);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}