package ru.nsu.pisarev;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

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
        validate(vertex1, vertex2);
        graph.get(vertex1).add(vertex2);
        graph.get(vertex2).add(vertex1);
    }


    @Override
    public void deleteEdge(int vertex1, int vertex2) throws NoGraphElementException {
        validate(vertex1, vertex2);
        deleteEdgeForward(vertex1, vertex2);
        deleteEdgeForward(vertex2, vertex1);
    }




    @Override
    public List<Integer> getAdjacentVertices(int vertex) throws NoGraphElementException {
        validate(vertex);
        return graph.get(vertex);
    }

    @Override
    public void read(BufferedReader reader) throws IOException, NoGraphElementException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isBlank())
                continue;
            String[] parts = line.trim().split("\\s+");
            int[] numbers = Arrays.stream(parts)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            if (!graph.containsKey(numbers[0]))
                addVertex(numbers[0]);
            for (int i = 1; i < numbers.length; i++)
                addEdge(numbers[0], numbers[i]);
        }
    }



    @Override
    public String toString() {
        return "GraphAdjacencyList{" +
                "graph=" + graph +
                '}';
    }

    private void validate(int... vertices) throws NoGraphElementException {
        for (int vertex : vertices) {
            if (!graph.containsKey(vertex)) {
                throw new NoGraphElementException("Vertex doesn't exist:" + vertex);
            }
        }
    }
    private void deleteEdgeForward(int vertex1, int vertex2) throws NoGraphElementException {
        validate(vertex1, vertex2);
        graph.get(vertex1).remove(Integer.valueOf(vertex2));
    }
}
