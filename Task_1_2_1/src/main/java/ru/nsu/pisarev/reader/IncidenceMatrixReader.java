package ru.nsu.pisarev.reader;

import ru.nsu.pisarev.IncidenceMatrixGraph;
import ru.nsu.pisarev.NoGraphElementException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixReader implements GraphReader<IncidenceMatrixGraph> {
    private final BufferedReader reader;

    public IncidenceMatrixReader(BufferedReader reader) {
        this.reader = reader;
    }

    public IncidenceMatrixGraph read() throws IOException, NoGraphElementException {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(1);
        List<int[]> edges = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isBlank())
                continue;
            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 2) {
                int v1 = Integer.parseInt(parts[0]);
                int v2 = Integer.parseInt(parts[1]);
                edges.add(new int[]{v1, v2});
            }
        }
        for (int[] e : edges) {
            graph.addEdge(e[0], e[1]);
        }
        return graph;
    }
}
