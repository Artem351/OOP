package ru.nsu.pisarev;

import java.io.BufferedReader;
import java.io.IOException;

public interface CanReadFromFile extends HasVertices, HasEdges {

    /**
     * Reads graph data from the given BufferedReader and initializes
     * the graph structure accordingly.
     *
     * @param reader the BufferedReader providing graph data input
     * @throws IOException             if an I/O error occurs while reading
     * @throws NoGraphElementException if the input data is missing required graph elements
     */

    void read(BufferedReader reader) throws IOException, NoGraphElementException;
}
