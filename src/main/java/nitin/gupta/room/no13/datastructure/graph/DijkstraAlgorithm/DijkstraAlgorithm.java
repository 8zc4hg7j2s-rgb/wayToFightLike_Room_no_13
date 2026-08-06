package nitin.gupta.room.no13.datastructure.graph.DijkstraAlgorithm;

import java.util.*;


class Graph<T extends Comparable<T>> {

    private final Map<T, Vertex<T>> vertices = new HashMap<>();

    public Vertex<T> addVertex(T data) {
        return vertices.computeIfAbsent(data, Vertex::new);
    }

    public void addEdge(T from, T to, double weight, boolean directed) {
        Vertex<T> v1 = addVertex(from);
        Vertex<T> v2 = addVertex(to);

        v1.addEdge(v2, weight);
        if (!directed && v1 != v2) {
            v2.addEdge(v1, weight);
        }
    }

    public Vertex<T> getVertex(T data) {
        return vertices.get(data);
    }

    public Set<T> getAllVertexData() {
        return vertices.keySet();
    }

    static class Edge<T extends Comparable<T>> {
        Vertex<T> target;
        double weight;

        Edge(Vertex<T> target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class Vertex<T extends Comparable<T>> implements Comparable<Vertex<T>> {
        T data;
        List<Edge<T>> edges = new ArrayList<>();

        Vertex(T data) {
            this.data = data;
        }

        void addEdge(Vertex<T> target, double weight) {
            edges.add(new Edge<>(target, weight));
        }

        @Override
        public int compareTo(Vertex<T> other) {
            return this.data.compareTo(other.data);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;
            return Objects.equals(data, ((Vertex<?>) o).data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }
}

public class DijkstraAlgorithm<T extends Comparable<T>> {

    private final Graph<T> graph;

    public DijkstraAlgorithm(Graph<T> graph) {
        this.graph = graph;
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A", "B", 4, false);
        graph.addEdge("A", "C", 1, false);
        graph.addEdge("C", "B", 2, false);
        graph.addEdge("B", "D", 5, false);
        graph.addEdge("C", "D", 8, false);

        DijkstraAlgorithm<String> dijkstra = new DijkstraAlgorithm<>(graph);
        Result<String> result = dijkstra.run("A");

        System.out.println("Distances from A: " + result.distances);
        System.out.println("Path A -> D: " + result.pathTo("D"));
    }

    public Result<T> run(T startData) {
        Graph.Vertex<T> start = graph.getVertex(startData);
        if (start == null) {
            return new Result<>(Collections.emptyMap(), Collections.emptyMap());
        }

        Map<T, Double> distances = new HashMap<>();
        Map<T, T> previous = new HashMap<>();
        Set<Graph.Vertex<T>> visited = new HashSet<>();

        for (T data : graph.getAllVertexData()) {
            distances.put(data, Double.POSITIVE_INFINITY);
        }
        distances.put(startData, 0.0);

        PriorityQueue<Graph.Vertex<T>> pq =
                new PriorityQueue<>(Comparator.comparingDouble(v -> distances.get(v.data)));
        pq.offer(start);

        while (!pq.isEmpty()) {
            Graph.Vertex<T> current = pq.poll();
            if (!visited.add(current)) continue;   // already finalized

            double currentDist = distances.get(current.data);

            for (Graph.Edge<T> edge : current.edges) {
                Graph.Vertex<T> neighbor = edge.target;
                if (visited.contains(neighbor)) continue;

                double newDist = currentDist + edge.weight;
                if (newDist < distances.get(neighbor.data)) {
                    distances.put(neighbor.data, newDist);
                    previous.put(neighbor.data, current.data);
                    pq.offer(neighbor);   // stale duplicates handled by visited check above
                }
            }
        }

        return new Result<>(distances, previous);
    }

    // Result holder: shortest distances + predecessor map (for path reconstruction)
    public static class Result<T> {
        public final Map<T, Double> distances;
        public final Map<T, T> previous;

        Result(Map<T, Double> distances, Map<T, T> previous) {
            this.distances = distances;
            this.previous = previous;
        }

        public List<T> pathTo(T target) {
            if (!distances.containsKey(target) || distances.get(target) == Double.POSITIVE_INFINITY) {
                return Collections.emptyList();
            }
            LinkedList<T> path = new LinkedList<>();
            T current = target;
            while (current != null) {
                path.addFirst(current);
                current = previous.get(current);
            }
            return path;
        }
    }
}