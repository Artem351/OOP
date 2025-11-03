package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.*;
import java.util.*;


public class MyTest {

    @Test
    public void checkGraphAdjacencyListBasic() {
        AdjacencyListGraph graph = new AdjacencyListGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addEdge(10, 20);
        graph.addEdge(10, 30);

        assertEquals(Set.of(20, 30), new HashSet<>(graph.getAdjacentVertices(10)));
        assertEquals(Set.of(10), new HashSet<>(graph.getAdjacentVertices(20)));

        graph.deleteEdge(10, 20);
        assertEquals(Set.of(30), new HashSet<>(graph.getAdjacentVertices(10)));
        assertEquals(Collections.emptySet(), new HashSet<>(graph.getAdjacentVertices(20)));

        graph.deleteVertex(30);
        assertThrows(NoGraphElementException.class, () -> graph.getAdjacentVertices(30));
    }

    @Test
    public void checkGraphAdjacencyListExceptions() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(1);
        assertThrows(NoGraphElementException.class, () -> graph.addEdge(1, 2));
        assertThrows(NoGraphElementException.class, () -> graph.deleteEdge(5, 1));
        assertThrows(NoGraphElementException.class, () -> graph.getAdjacentVertices(100));
    }

    @Test
    public void checkGraphAdjacencyMatrixBasic() {
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        assertEquals(List.of(1), graph.getAdjacentVertices(0));
        assertEquals(Set.of(0, 2), new HashSet<>(graph.getAdjacentVertices(1)));

        graph.deleteEdge(1, 2);
        assertEquals(List.of(0), graph.getAdjacentVertices(1));

        graph.addVertex(3);
        graph.addEdge(2, 3);
        assertEquals(List.of(3), graph.getAdjacentVertices(2));
    }

    @Test
    public void checkGraphAdjacencyMatrixDeleteVertex() {
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);
        graph.deleteVertex(1);
        assertEquals(List.of(), graph.getAdjacentVertices(0));
        assertEquals(List.of(2), graph.getAdjacentVertices(1)); // former vertex 2 now index 1
    }

    @Test
    public void checkGraphIncidenceMatrixBasic() {
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertEquals(List.of(1), graph.getAdjacentVertices(0));
        assertEquals(Set.of(0, 2), new HashSet<>(graph.getAdjacentVertices(1)));

        graph.deleteEdge(1, 2);
        assertEquals(List.of(0), graph.getAdjacentVertices(1));

        graph.addVertex(4);
        graph.addEdge(4, 0);
        assertEquals(List.of(0), graph.getAdjacentVertices(4));
    }

    @Test
    public void checkGraphIncidenceMatrixDeleteVertex() {
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.deleteVertex(1);
        assertEquals(List.of(), graph.getAdjacentVertices(0));
        assertEquals(List.of(), graph.getAdjacentVertices(1));
    }

    @Test
    public void checkGraphIncidenceMatrixExceptions() {
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(2);
        assertThrows(NoGraphElementException.class, () -> graph.addEdge(0, 5));
        assertThrows(NoGraphElementException.class, () -> graph.getAdjacentVertices(10));
        assertThrows(NoGraphElementException.class, () -> graph.deleteVertex(99));
    }

    @Test
    public void checkReadFromFileAdjacencyList() throws IOException {
        File tmp = File.createTempFile("graphList", ".txt");
        try (FileWriter w = new FileWriter(tmp)) {
            w.write("1\n");
            w.write("2\n");
            w.write("3\n");
            w.write("4\n");
            w.write("1 2 3\n");
            w.write("2 4\n");
        }
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.read(tmp.getAbsolutePath());

        assertTrue(graph.getAdjacentVertices(1).containsAll(List.of(2, 3)));
        assertTrue(graph.getAdjacentVertices(2).contains(4));
    }

    @Test
    public void checkReadFromFileAdjacencyMatrix() throws IOException {
        File tmp = File.createTempFile("graphMatrix", ".txt");
        try (FileWriter w = new FileWriter(tmp)) {
            w.write("0 1\n1 2\n2 3\n");
        }
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(4);
        graph.readFromFile(tmp.getAbsolutePath());
        assertTrue(graph.getAdjacentVertices(0).contains(1));
        assertTrue(graph.getAdjacentVertices(1).contains(2));
    }

    @Test
    public void checkReadFromFileIncidenceMatrix() throws IOException {
        File tmp = File.createTempFile("graphInc", ".txt");
        try (FileWriter w = new FileWriter(tmp)) {
            w.write("0 1\n");
            w.write("1 2\n");
            w.write("2 3\n");
        }
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(4);
        graph.readFromFile(tmp.getAbsolutePath());
        assertTrue(graph.getAdjacentVertices(1).contains(2));
        assertTrue(graph.getAdjacentVertices(0).contains(1));
    }
}
