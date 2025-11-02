package org.example.IOHandler;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.GraphUtils.Graph;
import org.example.GraphUtils.GraphList;


import java.io.FileReader;
import java.util.List;

public class InputReader {
    public static List<Graph> jsonReadInput() {
        try (FileReader reader = new FileReader("input.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GraphList graphs = gson.fromJson(reader, GraphList.class);
            if (graphs == null || graphs.getGraphs() == null) {
                System.out.println("No graphs found in JSON.");
                return null;
            }

            //build adjacency lists for each graph
            for (Graph g : graphs.getGraphs()) {
                g.buildAdjacencyList();
            }

            return graphs.getGraphs();
        } catch (Exception e) {
            System.out.println("Parsing error: " + e.getMessage());
        }
        return null;
    }
}
