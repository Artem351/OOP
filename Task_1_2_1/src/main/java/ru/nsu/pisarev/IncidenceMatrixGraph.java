package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class IncidenceMatrixGraph implements Graph {
    /**
     * Graph representation
     * rows - edges, columns - vertexes
     */
    private boolean[][] graph;
    private int sizeVertexes;
    private int sizeEdges;

    public IncidenceMatrixGraph(int verticesAmount) {
        if (verticesAmount < 0)
            throw new IllegalArgumentException("Vertex count can't be negative");
        sizeVertexes = verticesAmount;
        sizeEdges = 0;
        graph = new boolean[sizeEdges][sizeVertexes];
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
    public boolean hasVertex(int vertex) {
        return 0 < vertex && vertex < sizeVertexes;
    }

    @Override
    public void deleteVertex(int vertex) throws NoGraphElementException {
        validate(vertex);

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
    public void addEdge(int vertex1, int vertex2) throws NoGraphElementException {
        validateEdge(vertex1,vertex2);

        boolean[][] newGraph = new boolean[sizeEdges+1][sizeVertexes];
        int counter1 = 0;
        int counter2;
        for (boolean[] booleans : graph) {
            counter2 = 0;
            for (boolean b : booleans) {
                newGraph[counter1][counter2]=b;
                counter2++;
            }
            counter1++;
        }


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
    public List<Integer> getAdjacentVertices(int vertex) throws NoGraphElementException {
        validate(vertex);

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



    private void validateEdge(int vertex1,int vertex2) throws NoGraphElementException {
        if (vertex1 == sizeVertexes){
            sizeVertexes++;
        }
        else{
            validate(vertex1);
        }
        if (vertex2 == sizeVertexes){
            sizeVertexes++;
        }
        else{
            validate(vertex2);
        }
    }
    private void validate(int vertex) throws NoGraphElementException {
        if (vertex < 0 || vertex >= sizeVertexes) {
            throw new NoGraphElementException("Invalid vertex index: " + vertex);
        }
    }
}
