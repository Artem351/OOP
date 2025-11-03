package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTest {
    @Test
    public void checkGraphAdjacencyList() {
    GraphAdjacencyList graph = new GraphAdjacencyList();
    graph.addVertex(10);
    graph.addVertex(20);
    graph.addVertex(30);
    graph.addVertex(40);

    graph.addEdge(10, 20);
    graph.addEdge(10, 30);
    graph.addEdge(10, 40);

    List<Integer> adj10 = graph.getAdjacencyVertexList(10);
    assertEquals(Set.of(20, 30, 40), new HashSet<>(adj10));

    graph.deleteVertex(30);
    assertFalse(graph.getAdjacencyVertexList(10).contains(30));
    assertTrue(graph.getAdjacencyVertexList(10).containsAll(List.of(20, 40)));
}

    @Test
    public void checkGraphAdjacencyMatrix() {
        GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(2, 3);

        assertEquals(List.of(1, 2), graph.getAdjacencyVertexList(0));
        graph.deleteEdge(0, 2);
        assertEquals(List.of(1), graph.getAdjacencyVertexList(0));

        graph.addVertex(4);
        graph.addEdge(4, 1);
        assertTrue(graph.getAdjacencyVertexList(4).contains(1));
    }

    @Test
    public void checkGraphIncidenceMatrix() {
        GraphIncidenceMatrix graph = new GraphIncidenceMatrix(4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        assertEquals(List.of(1), graph.getAdjacencyVertexList(0));
        assertEquals(Set.of(0, 2), new HashSet<>(graph.getAdjacencyVertexList(1)));

        graph.deleteEdge(1, 2);
        assertFalse(graph.getAdjacencyVertexList(1).contains(2));

        graph.addVertex(4);
        graph.addEdge(4, 0);
        assertTrue(graph.getAdjacencyVertexList(4).contains(0));
    }
}

