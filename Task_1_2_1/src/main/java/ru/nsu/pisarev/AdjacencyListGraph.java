package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph implements Graph {

    private final Map<Integer, List<Integer>> graph = new HashMap<>();

    /**
     * Adds a new vertex to the graph.

     * If the vertex already exists in the graph, the method prints an error message
     * to the standard error stream and throws an {@link IllegalArgumentException}.
     * Otherwise, it adds the vertex with an empty adjacency list.

     * @param vertex the vertex to add
     * @throws IllegalArgumentException if the vertex already exists in the graph
     */
    @Override
    public void addVertex(int vertex) throws IllegalArgumentException {
        if (graph.containsKey(vertex)) {
            System.err.println("Graph contains vertex:"+vertex);
            throw new IllegalArgumentException();
        }
        graph.put(vertex, new ArrayList<>());
    }

    @Override
    public void deleteVertex(int vertex) throws NoGraphElementException {
        validate(vertex);
        for (Integer i : graph.get(vertex)) {
            deleteEdgeForward(i, vertex);
        }

        graph.get(vertex).clear();
        graph.remove(vertex, graph.get(vertex));
    }

    @Override
    public void addEdge(int vertex1, int vertex2) throws NoGraphElementException {
        validate(vertex1);
        validate(vertex2);
        graph.get(vertex1).add(vertex2);
        graph.get(vertex2).add(vertex1);
    }


    @Override
    public void deleteEdge(int vertex1, int vertex2) throws NoGraphElementException {
        validate(vertex1);
        validate(vertex2);
        deleteEdgeForward(vertex1, vertex2);
        deleteEdgeForward(vertex2, vertex1);
    }

    private void deleteEdgeForward(int vertex1, int vertex2) throws NoGraphElementException {
        validate(vertex1);
        validate(vertex2);
        graph.get(vertex1).remove(Integer.valueOf(vertex2));
    }


    @Override
    public List<Integer> getAdjacentVertices(int vertex) throws NoGraphElementException {
        validate(vertex);
        return graph.get(vertex);
    }

    private void validate(int vertex) throws NoGraphElementException {
        if (!graph.containsKey(vertex)) {
            throw new NoGraphElementException("Vertex doesn't exist:" + vertex);
        }
    }


    @Override
    public String toString() {
        return "GraphAdjacencyList{" +
                "graph=" + graph +
                '}';
    }
}
