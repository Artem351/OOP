package ru.nsu.pisarev;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph implements Graph {

    private final Map<Integer, List<Integer>> graph = new HashMap<>();

    /*
     * The vertex identifier can be any integer
     */
    @Override
    public void addVertex(int vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
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

    public void deleteEdgeForward(int vertex1, int vertex2) throws NoGraphElementException {
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
    public void read(BufferedReader br) throws IOException, NoGraphElementException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank())
                continue;
            String[] parts = line.trim().split("\\s+");
            int[] numbers = Arrays.stream(parts)
                    .mapToInt(Integer::parseInt)
                    .toArray();
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
}
