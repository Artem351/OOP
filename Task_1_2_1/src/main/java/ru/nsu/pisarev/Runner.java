package ru.nsu.pisarev;

public class Runner {
    public static final String FILE_PATH = "C:\\Users\\artpe\\Desktop\\OOP\\Task_1_2_1\\src\\main\\java\\ru\\nsu\\pisarev\\test.txt";
    public static void main(String[] args) {
        try {
            GraphAdjacencyList graphAdjacencyList = new GraphAdjacencyList();
            graphAdjacencyList.addVertex(10);
            graphAdjacencyList.addVertex(20);
            graphAdjacencyList.addVertex(30);
            graphAdjacencyList.addVertex(40);
            graphAdjacencyList.addEdge(10, 20);
            graphAdjacencyList.addEdge(10, 30);
            graphAdjacencyList.addEdge(10, 40);
            System.out.println(graphAdjacencyList);
            //graphAdjacencyList.deleteEdge(1,4);
            graphAdjacencyList.deleteVertex(30);
            System.out.println(graphAdjacencyList);
            System.out.println(graphAdjacencyList.getAdjacencyVertexList(10));
            graphAdjacencyList.readFromFile(FILE_PATH);
            System.out.println(graphAdjacencyList);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
