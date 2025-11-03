package ru.nsu.pisarev;

public class Main {
    public static final String FILE_PATH = "C:\\Users\\artpe\\Desktop\\OOP\\Task_1_2_1\\src\\main\\java\\ru\\nsu\\pisarev\\test.txt";
    public static void main(String[] args) {
        try {
            AdjacencyListGraph adjacencyListGraph = new AdjacencyListGraph();
            adjacencyListGraph.addVertex(10);
            adjacencyListGraph.addVertex(20);
            adjacencyListGraph.addVertex(30);
            adjacencyListGraph.addVertex(40);
            adjacencyListGraph.addEdge(10, 20);
            adjacencyListGraph.addEdge(10, 30);
            adjacencyListGraph.addEdge(10, 40);
            System.out.println(adjacencyListGraph);
            //graphAdjacencyList.deleteEdge(1,4);
            adjacencyListGraph.deleteVertex(30);
            System.out.println(adjacencyListGraph);
            System.out.println(adjacencyListGraph.getAdjacentVertices(10));
            adjacencyListGraph.read(FILE_PATH);
            System.out.println(adjacencyListGraph);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
