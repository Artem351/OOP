package ru.nsu.pisarev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphAdjacencyList implements GraphInterface{

    private final Map<Integer, List<Integer>> graph = new HashMap<>();

    /*
    * The vertex identifier can be any integer
    * */
    @Override
    public void addVertex(int vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void deleteVertex(int vertex) throws NoSuchElementException{
        checkVertexValue(vertex);
        for (Integer i : graph.get(vertex)) {
            deleteEdgeForward(i,vertex);
        }

        graph.get(vertex).clear();
        graph.remove(vertex,graph.get(vertex));
    }

    @Override
    public void addEdge(int vertex1,int vertex2) throws NoSuchElementException{
        checkVertexValue(vertex1);
        checkVertexValue(vertex2);
        graph.get(vertex1).add(vertex2);
        graph.get(vertex2).add(vertex1);
    }


    @Override
    public void deleteEdge(int vertex1, int vertex2)throws NoSuchElementException {
        checkVertexValue(vertex1);
        checkVertexValue(vertex2);
        deleteEdgeForward(vertex1,vertex2);
        deleteEdgeForward(vertex2,vertex1);
    }

    public void deleteEdgeForward(int vertex1, int vertex2)throws NoSuchElementException {
        checkVertexValue(vertex1);
        checkVertexValue(vertex2);
        if (graph.containsKey(vertex1)) {
            graph.get(vertex1).remove(Integer.valueOf(vertex2));
        }
    }



    @Override
    public List<Integer> getAdjacencyVertexList(int vertex) throws NoSuchElementException{
        checkVertexValue(vertex);
        return graph.get(vertex);
    }

    @Override
    public String toString() {
        return "GraphAdjacencyList{" +
                "graph=" + graph +
                '}';
    }
    private void checkVertexValue(int vertex) throws NoSuchElementException{
        if (!graph.containsKey(vertex)){
            throw new NoSuchElementException("Vertex doesn't exist:"+vertex);
        }
    }

    @Override
    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank())
                    continue;
                String[] parts = line.trim().split("\\s+");
                int[] numbers = Arrays.stream(parts)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                addVertex(numbers[0]);
                for(int i=1;i<numbers.length;i++)
                    addEdge(numbers[0],numbers[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
