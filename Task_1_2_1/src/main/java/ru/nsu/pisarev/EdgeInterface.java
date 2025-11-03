package ru.nsu.pisarev;

/**
 * Interface defining operations for managing edges in a graph structure.
 */
public interface EdgeInterface {

    /**
     * Adds an undirected edge between two vertices in the graph.
     *
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     *
     * @throws NoSuchElementException if one of the vertexes doesn't exist in the graph
     */
    void addEdge(int vertex1, int vertex2);

    /**
     * Deletes an undirected edge between two vertices in the graph.
     *
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     *
     * @throws NoSuchElementException if the edge does not exist
     */
    void deleteEdge(int vertex1, int vertex2);
}
