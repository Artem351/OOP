package ru.nsu.pisarev.reader;

import ru.nsu.pisarev.AdjacencyMatrixGraph;
import ru.nsu.pisarev.NoGraphElementException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class AdjacencyMatrixReader {

    private final BufferedReader reader;

    public AdjacencyMatrixReader(BufferedReader reader) {
        this.reader = reader;
    }

    public AdjacencyMatrixGraph read() throws IOException, NoGraphElementException {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(1);

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isBlank())
                continue;
            String[] parts = line.trim().split("\\s+");
            int[] numbers = Arrays.stream(parts)
                    .mapToInt(Integer::parseInt)
                    .toArray();
                graph.addVertex(numbers[0]);
            for (int i = 1; i < numbers.length; i++)
                graph.addEdge(numbers[0], numbers[i]);
        }
        return graph;
    }
}
