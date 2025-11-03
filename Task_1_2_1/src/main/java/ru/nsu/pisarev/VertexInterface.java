package ru.nsu.pisarev;

import java.util.List;

/**
 * Interface defining operations for managing vertices in a graph structure.
 */
public interface VertexInterface {

    /**
     * Adds a new vertex to the graph.
     *
     * @param vertex the identifier of the vertex to add
     *
     * @throws IllegalStateException if the vertex already exists
     */
    void addVertex(int vertex);

    /**
     * Deletes a vertex and all its associated edges from the graph.
     *
     * @param vertex the identifier of the vertex to delete
     *
     * @throws NoSuchElementException if the vertex does not exist
     */
    void deleteVertex(int vertex);

    /**
     * Returns an array of adjacent vertices for the specified vertex.
     *
     * @param vertex the identifier of the vertex whose adjacency list is requested
     * @return a list of vertex identifiers adjacent to the given vertex,
     *         or an empty array if the vertex has no connections
     *
     * @throws NoSuchElementException if the vertex does not exist
     */
    List<Integer> getAdjacencyVertexList(int vertex);
}