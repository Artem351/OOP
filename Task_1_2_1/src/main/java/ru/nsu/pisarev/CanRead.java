package ru.nsu.pisarev;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public interface CanRead extends HasVertices, HasEdges {

    /**
     * Reads graph data from the given BufferedReader and initializes
     * the graph structure accordingly.
     *
     * @param reader the BufferedReader providing graph data input
     * @throws IOException             if an I/O error occurs while reading
     * @throws NoGraphElementException if the input data is missing required graph elements
     */

    default void read(BufferedReader reader) throws IOException, NoGraphElementException {
        String line;
        while ((line = reader.readLine()) != null) {
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
}
