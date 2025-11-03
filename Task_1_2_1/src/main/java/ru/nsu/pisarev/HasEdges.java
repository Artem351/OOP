package ru.nsu.pisarev;

/**
 * Interface defining operations for managing edges in a graph structure.
 */
public interface HasEdges {

    /**
     * Adds an undirected edge between two vertices in the graph.
     *
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     *
     * @throws NoGraphElementException if one of the vertexes doesn't exist in the graph
     */
    void addEdge(int vertex1, int vertex2) throws NoGraphElementException;

    /**
     * Deletes an undirected edge between two vertices in the graph.
     *
     * @param vertex1 the first vertex of the edge
     * @param vertex2 the second vertex of the edge
     *
     * @throws NoGraphElementException if the edge does not exist
     */
    void deleteEdge(int vertex1, int vertex2) throws NoGraphElementException;
}
