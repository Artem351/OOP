package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;


public class MyTest {

    @Test
    public void checkGraphAdjacencyListBasic() {
        GraphAdjacencyList graph = new GraphAdjacencyList();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addEdge(10, 20);
        graph.addEdge(10, 30);

        assertEquals(Set.of(20, 30), new HashSet<>(graph.getAdjacencyVertexList(10)));
        assertEquals(Set.of(10), new HashSet<>(graph.getAdjacencyVertexList(20)));

        graph.deleteEdge(10, 20);
        assertEquals(Set.of(30), new HashSet<>(graph.getAdjacencyVertexList(10)));
        assertEquals(Collections.emptySet(), new HashSet<>(graph.getAdjacencyVertexList(20)));

        graph.deleteVertex(30);
        assertThrows(NoSuchElementException.class, () -> graph.getAdjacencyVertexList(30));
    }

    @Test
    public void checkGraphAdjacencyListExceptions() {
        GraphAdjacencyList graph = new GraphAdjacencyList();
        graph.addVertex(1);
        assertThrows(NoSuchElementException.class, () -> graph.addEdge(1, 2));
        assertThrows(NoSuchElementException.class, () -> graph.deleteEdge(5, 1));
        assertThrows(NoSuchElementException.class, () -> graph.getAdjacencyVertexList(100));
    }

    @Test
    public void checkGraphAdjacencyMatrixBasic() {
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        assertEquals(List.of(1), graph.getAdjacencyVertexList(0));
        assertEquals(Set.of(0, 2), new HashSet<>(graph.getAdjacencyVertexList(1)));

        graph.deleteEdge(1, 2);
        assertEquals(List.of(0), graph.getAdjacencyVertexList(1));

        graph.addVertex(3);
        graph.addEdge(2, 3);
        assertEquals(List.of(3), graph.getAdjacencyVertexList(2));
    }

    @Test
    public void checkGraphAdjacencyMatrixDeleteVertex() {
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);
        graph.deleteVertex(1);
        assertEquals(List.of(), graph.getAdjacencyVertexList(0));
        assertEquals(List.of(2), graph.getAdjacencyVertexList(1)); // former vertex 2 now index 1
    }

    @Test
    public void checkGraphIncidenceMatrixBasic() {
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertEquals(List.of(1), graph.getAdjacencyVertexList(0));
        assertEquals(Set.of(0, 2), new HashSet<>(graph.getAdjacencyVertexList(1)));

        graph.deleteEdge(1, 2);
        assertEquals(List.of(0), graph.getAdjacencyVertexList(1));

        graph.addVertex(4);
        graph.addEdge(4, 0);
        assertEquals(List.of(0), graph.getAdjacencyVertexList(4));
    }

    @Test
    public void checkGraphIncidenceMatrixDeleteVertex() {
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.deleteVertex(1);
        assertEquals(List.of(), graph.getAdjacencyVertexList(0));
        assertEquals(List.of(), graph.getAdjacencyVertexList(1));
    }

    @Test
    public void checkGraphIncidenceMatrixExceptions() {
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(2);
        assertThrows(NoSuchElementException.class, () -> graph.addEdge(0, 5));
        assertThrows(NoSuchElementException.class, () -> graph.getAdjacencyVertexList(10));
        assertThrows(NoSuchElementException.class, () -> graph.deleteVertex(99));
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
        GraphAdjacencyList graph = new GraphAdjacencyList();
        graph.readFromFile(tmp.getAbsolutePath());

        assertTrue(graph.getAdjacencyVertexList(1).containsAll(List.of(2, 3)));
        assertTrue(graph.getAdjacencyVertexList(2).contains(4));
    }

    @Test
    public void checkReadFromFileAdjacencyMatrix() throws IOException {
        File tmp = File.createTempFile("graphMatrix", ".txt");
        try (FileWriter w = new FileWriter(tmp)) {
            w.write("0 1\n1 2\n2 3\n");
        }
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(4);
        graph.readFromFile(tmp.getAbsolutePath());
        assertTrue(graph.getAdjacencyVertexList(0).contains(1));
        assertTrue(graph.getAdjacencyVertexList(1).contains(2));
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
        assertTrue(graph.getAdjacencyVertexList(1).contains(2));
        assertTrue(graph.getAdjacencyVertexList(0).contains(1));
    }
}
