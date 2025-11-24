package ru.nsu.pisarev.reader;

import ru.nsu.pisarev.AdjacencyListGraph;
import ru.nsu.pisarev.NoGraphElementException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class AdjacencyListReader implements GraphReader<AdjacencyListGraph>, AutoCloseable {
    private final BufferedReader reader;

    public AdjacencyListReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public AdjacencyListGraph read() throws IOException, NoGraphElementException {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isBlank())
                continue;
            String[] parts = line.trim().split("\\s+");
            int[] numbers = Arrays.stream(parts)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            if (!graph.hasVertex(numbers[0]))
                graph.addVertex(numbers[0]);
            for (int i = 1; i < numbers.length; i++)
                graph.addEdge(numbers[0], numbers[i]);
        }
        return graph;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
