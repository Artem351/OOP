package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.List;



public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] graph;
    private int size;


    public AdjacencyMatrixGraph(int verticesAmount) {
        if (verticesAmount < 0) {
            throw new IllegalArgumentException("Vertex count can't be negative");
        }
        graph = new boolean[verticesAmount][verticesAmount];
        size = verticesAmount;
    }

    /**
     * @param vertex1 index of the first vertex
     * @param vertex2 index of the second vertex
     */
    @Override
    public void addEdge(int vertex1, int vertex2) throws NoGraphElementException {
        checkVertexIndex(vertex1);
        checkVertexIndex(vertex2);
        graph[vertex1][vertex2] = true;
        graph[vertex2][vertex1] = true;
    }

    /**
     * @param vertex1 index of the first vertex
     * @param vertex2 index of the second vertex
     */
    @Override
    public void deleteEdge(int vertex1, int vertex2) throws NoGraphElementException {
        checkVertexIndex(vertex1);
        checkVertexIndex(vertex2);
        graph[vertex1][vertex2] = false;
        graph[vertex2][vertex1] = false;
    }


    @Override
    public void addVertex(int vertex) {
        boolean[][] newGraph = new boolean[size + 1][size + 1];
        for (int i = 0; i < size; i++) {
            System.arraycopy(graph[i], 0, newGraph[i], 0, size);
        }
        graph = newGraph;
        size++;
    }

    /**
     * @param vertex index of the vertex to delete
     */
    @Override
    public void deleteVertex(int vertex) throws NoGraphElementException {
        checkVertexIndex(vertex);
        boolean[][] newGraph = new boolean[size - 1][size - 1];

        for (int i = 0, ni = 0; i < size; i++) {
            if (i == vertex)
                continue;
            for (int j = 0, nj = 0; j < size; j++) {
                if (j == vertex)
                    continue;
                newGraph[ni][nj++] = graph[i][j];
            }
            ni++;
        }

        graph = newGraph;
        size--;
    }

    /**
     * @param vertex index of the vertex
     */
    @Override
    public List<Integer> getAdjacentVertices(int vertex) throws NoGraphElementException {
        checkVertexIndex(vertex);
        List<Integer> adjacencyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (graph[vertex][i]) {
                adjacencyList.add(i);
            }
        }
        return adjacencyList;
    }
    private void checkVertexIndex(int vertex) throws NoGraphElementException {
        if (vertex < 0 || vertex >= size) {
            throw new NoGraphElementException("Invalid vertex index: " + vertex);
        }
    }


}
