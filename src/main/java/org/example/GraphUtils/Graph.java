package org.example.GraphUtils;


import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int id;
    private boolean directed;
    private int size;
    private List<List<Integer>> adjacencyList;
    private List<Edge> edges;
    private int source;
    private String weightModel = "edge";
    public Graph(){}

    public Graph(int size) {
        this.size = size;
        this.directed = true;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        this.edges = new ArrayList<>();
        this.source = 0;
    }

    public int getSource(){return source;}
    public int getId() { return id; }
    public int getSize() { return size; }
    public boolean isDirected() { return directed; }

    public void addEdge(int u, int v) {
        adjacencyList.get(u).add(v);
        edges.add(new Edge(u, v, 1)); // default weight 1
        if (!directed) {
            adjacencyList.get(v).add(u);
        }
    }

    public void addEdge(int u, int v, int w) {
        adjacencyList.get(u).add(v);
        edges.add(new Edge(u, v, w));
        if (!directed) {
            adjacencyList.get(v).add(u);
        }
    }

    public void buildAdjacencyList() {
        if (size <= 0) {
            adjacencyList = new ArrayList<>();
            return;
        }

        if (adjacencyList == null) {
            adjacencyList = new ArrayList<>();
        } else {
            adjacencyList.clear();
        }

        for (int i = 0; i < size; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        if (edges != null) {
            for (Edge e : edges) {
                if (e.getU() >= 0 && e.getU() < size && e.getV() >= 0 && e.getV() < size) {
                    adjacencyList.get(e.getU()).add(e.getV());
                }
            }
        }
    }




//    public void buildAdjacencyList() {
//        adjacencyList.clear();
//        for (int i = 0; i < size; i++) adjacencyList.add(new ArrayList<>());
//        for (Edge e : edges) {
//            adjacencyList.get(e.getU()).add(e.getV());
//        }
//    }

    public List<Integer> getNeighbours(int u) {
        return adjacencyList.get(u);
    }

    public Graph getTranspose() {
        Graph transposed = new Graph(size);
        for (int u = 0; u < size; u++) {
            for (int v : adjacencyList.get(u)) {
                transposed.addEdge(v, u);
            }
        }
        return transposed;
    }

    public List<Edge> getEdges() {
        return edges;
    }


}

