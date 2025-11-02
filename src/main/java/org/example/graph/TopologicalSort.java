package org.example.graph;


import org.example.GraphUtils.Graph;
import org.example.Metrics;

import java.util.*;

public class TopologicalSort {
    public static AlgorithmResult<List<Integer>> kahnAlgorithm(Graph graph){
        Metrics metrics = new Metrics();
        metrics.reset();
        metrics.startTimer();
        int size = graph.getSize();

        //in degree is number of incoming edges for the node
        int[] inDegree = new int[size];
        for(int u=0;u<size;u++){
            for(int v : graph.getNeighbours(u)){
                inDegree[v]++;
            }
        }
        //queue to store nodes with in degree 0, they are the starting nodes
        Queue<Integer> queue = new LinkedList<>();
        for(int i=0;i<size;i++){
            if(inDegree[i]==0){
                queue.add(i);
                metrics.queueAdd();
            }
        }
        List<Integer> order = new ArrayList<>();
        //remove nodes from graph
        while(!queue.isEmpty()){
            int node = queue.poll();
            metrics.queuePoll();
            order.add(node);
            //for each neighbour of the node that depend on that node
            for(int neighbour : graph.getNeighbours(node)){
                inDegree[neighbour]--; //remove incoming edge
                if(inDegree[neighbour]==0){ //if the neighbour now does not have dependencies
                    queue.add(neighbour);
                    metrics.queueAdd();
                }
            }
        }
        //if some nodes were not processed, then that node is part of cycle, and we cant form order
        if(order.size()!=size){
            System.out.println("there is a cycle");
            return null;
        }

        metrics.stopTimer();


        return new AlgorithmResult<>(order, metrics);
    }
}
