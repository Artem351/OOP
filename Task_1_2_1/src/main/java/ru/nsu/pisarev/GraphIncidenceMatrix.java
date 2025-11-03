package ru.nsu.pisarev;

import java.util.List;
import java.io.*;
import java.util.*;

public class GraphIncidenceMatrix implements GraphInterface {
    private boolean[][] graph; // строки — рёбра, столбцы — вершины
    private int sizeVertexes;
    private int sizeEdges;

    public GraphIncidenceMatrix(int verticesAmount) {
        if (verticesAmount < 0)
            throw new IllegalArgumentException("Vertex count can't be negative");
        graph = new boolean[0][verticesAmount];
        sizeVertexes = verticesAmount;
        sizeEdges = 0;
    }

    @Override
    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<int[]> edges = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2) {
                    int v1 = Integer.parseInt(parts[0]);
                    int v2 = Integer.parseInt(parts[1]);
                    edges.add(new int[]{v1, v2});
                }
            }
            for (int[] e : edges) {
                addEdge(e[0], e[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException("File read error: " + e.getMessage());
        }
    }

    @Override
    public void addEdge(int vertex1, int vertex2) {
        checkVertexIndex(vertex1);
        checkVertexIndex(vertex2);

        boolean[][] newGraph = Arrays.copyOf(graph, sizeEdges + 1);
        newGraph[sizeEdges] = new boolean[sizeVertexes];
        newGraph[sizeEdges][vertex1] = true;
        newGraph[sizeEdges][vertex2] = true;
        graph = newGraph;
        sizeEdges++;
    }

    @Override
    public void deleteEdge(int vertex1, int vertex2) {
        int index = -1;
        for (int i = 0; i < sizeEdges; i++) {
            if (graph[i][vertex1] && graph[i][vertex2]) {
                index = i;
                break;
            }
        }
        if (index == -1)
            return;
        boolean[][] newGraph = new boolean[sizeEdges - 1][sizeVertexes];
        for (int i = 0, j = 0; i < sizeEdges; i++) {
            if (i != index) newGraph[j++] = graph[i];
        }
        graph = newGraph;
        sizeEdges--;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex != sizeVertexes)
            throw new IllegalArgumentException("Can only add vertex at end index = " + sizeVertexes);

        for (int i = 0; i < sizeEdges; i++) {
            graph[i] = Arrays.copyOf(graph[i], sizeVertexes + 1);
        }
        sizeVertexes++;
    }

    @Override
    public void deleteVertex(int vertex) {
        checkVertexIndex(vertex);

        boolean[][] newGraph = new boolean[sizeEdges][sizeVertexes - 1];
        for (int i = 0; i < sizeEdges; i++) {
            int idx = 0;
            for (int j = 0; j < sizeVertexes; j++) {
                if (j == vertex) continue;
                newGraph[i][idx++] = graph[i][j];
            }
        }
        sizeVertexes--;
        graph = newGraph;
    }

    @Override
    public List<Integer> getAdjacencyVertexList(int vertex) {
        checkVertexIndex(vertex);

        Set<Integer> adj = new HashSet<>();
        for (int i = 0; i < sizeEdges; i++) {
            if (graph[i][vertex]) {
                for (int j = 0; j < sizeVertexes; j++) {
                    if (j != vertex && graph[i][j]) adj.add(j);
                }
            }
        }
        return new ArrayList<>(adj);
    }
    private void checkVertexIndex(int vertex) throws NoSuchElementException{
        if (vertex < 0 || vertex >= sizeVertexes) {
            throw new NoSuchElementException("Invalid vertex index: " + vertex);
        }
    }
}
