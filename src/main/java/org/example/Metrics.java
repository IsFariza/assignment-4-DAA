package org.example;

import java.util.HashMap;
import java.util.Map;


public class Metrics {
    private long startTime;
    private double executionTime;
    private int dfsVisits, dfsEdges, stackPush, queueAdds, queuePolls, relaxations;

    public void startTimer() {
        startTime = System.nanoTime();
    }
    public void stopTimer() {
        executionTime = (System.nanoTime() - startTime) / 1_000_000.0;
    }

    public void dfsVisit() { dfsVisits++; }
    public void dfsEdge() { dfsEdges++; }
    public void stackPush() { stackPush++; }
    public void queueAdd() { queueAdds++; }
    public void queuePoll() { queuePolls++; }
    public void relax() { relaxations++; }

    public void reset() {
        executionTime = 0;
        dfsVisits = dfsEdges = stackPush = queueAdds = queuePolls = relaxations = 0;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("executionTime", executionTime);
        map.put("dfsVisits", dfsVisits);
        map.put("dfsEdges", dfsEdges);
        map.put("stackPush", stackPush);
        map.put("queueAdds", queueAdds);
        map.put("queuePolls", queuePolls);
        map.put("relaxations", relaxations);
        return map;
    }
}

