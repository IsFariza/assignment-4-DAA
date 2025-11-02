package org.example.graph;

import org.example.Metrics;

public class AlgorithmResult<T> {
    private final T result;
    private final Metrics metrics;

    public AlgorithmResult(T result, Metrics metrics) {
        this.result = result;
        this.metrics = metrics;
    }

    public T getResult() { return result; }
    public Metrics getMetrics() { return metrics; }
}
