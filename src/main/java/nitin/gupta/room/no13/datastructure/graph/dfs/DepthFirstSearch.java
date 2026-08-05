package nitin.gupta.room.no13.datastructure.graph.dfs;

import nitin.gupta.room.no13.datastructure.graph.Graph;

public class DepthFirstSearch {
    static void main() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        System.out.println("DFS (iterative): " + graph.dfsIterative(1));
    }
}
