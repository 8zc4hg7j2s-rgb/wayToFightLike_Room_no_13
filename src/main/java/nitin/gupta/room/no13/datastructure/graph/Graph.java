package nitin.gupta.room.no13.datastructure.graph;

import java.util.*;

public class Graph<T extends Comparable<T>> {

    // Map to keep track of vertices by their data value (optional convenience)
    public Map<T, Vertex<T>> vertices = new HashMap<>();

    public Vertex<T> getVertex(T data) {
        return vertices.get(data);
    }

    public Set<T> getAllVertexData() {
        return vertices.keySet();
    }

    public Vertex<T> addVertex(T data) {
        return vertices.computeIfAbsent(data, Vertex::new);
    }

    public void addEdge(T from, T to) {
        addEdge(from, to, false);
    }

    public void addEdge(T from, T to, boolean directed) {
        Vertex<T> v1 = addVertex(from);
        Vertex<T> v2 = addVertex(to);
        v1.addNeighbor(v2);
        if (!directed) {
            v2.addNeighbor(v1);
        }


    }

    public List<T> bfs(T startData) {
        Vertex<T> start = vertices.get(startData);
        if (start == null) return Collections.emptyList();

        List<T> visitedOrder = new ArrayList<>(vertices.size());
        Set<Vertex<T>> visited = new HashSet<>(vertices.size() * 2);
        Deque<Vertex<T>> queue = new ArrayDeque<>(vertices.size());

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> current = queue.poll();
            visitedOrder.add(current.data);

            for (Vertex<T> neighbor : current.neighbors) {
                if (visited.add(neighbor)) {   // add() returns false if already present
                    queue.offer(neighbor);
                }
            }
        }
        return visitedOrder;
    }

    // ---------- Iterative DFS (explicit stack) ----------
    public List<T> dfsIterative(T startData) {
        Vertex<T> start = vertices.get(startData);
        if (start == null) return Collections.emptyList();

        List<T> visitedOrder = new ArrayList<>(vertices.size());
        Set<Vertex<T>> visited = new HashSet<>(vertices.size() * 2);
        Deque<Vertex<T>> stack = new ArrayDeque<>(vertices.size());

        stack.push(start);

        while (!stack.isEmpty()) {
            Vertex<T> current = stack.pop();

            if (!visited.add(current)) continue;   // skip if already visited
            visitedOrder.add(current.data);

            List<Vertex<T>> neighbors = current.neighbors;
            for (Vertex<T> neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
        return visitedOrder;
    }

    static class Edge<T extends Comparable<T>> {
        Vertex<T> target;
        double weight;

        Edge(Vertex<T> target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    static class VertexEdge<T extends Comparable<T>> implements Comparable<Vertex<T>> {
        T data;
        List<Edge<T>> edges = new ArrayList<>();

        VertexEdge(T data) {
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

    // Static generic Vertex class
    public static class Vertex<T extends Comparable<T>> implements Comparable<Vertex<T>> {
        T data;
        List<Vertex<T>> neighbors;

        Vertex(T data) {
            this.data = data;
            this.neighbors = new ArrayList<>();
        }

        void addNeighbor(Vertex<T> neighbor) {
            neighbors.add(neighbor);
        }

        @Override
        public int compareTo(Vertex<T> other) {
            return this.data.compareTo(other.data);
        }

        @Override
        public String toString() {
            return String.valueOf(data);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;
            Vertex<?> v = (Vertex<?>) o;
            return Objects.equals(data, v.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }
}