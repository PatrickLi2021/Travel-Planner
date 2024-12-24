package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

/**
 * The Dijkstra class implements Dijkstra's algorithm to find the least cost
 * path (in terms of time or cost) from a source vertex and a destination
 * vertex. It does this via the getShortestPath, performDijkstra, and
 * buildFinalPath methods
 *
 * @param <V> the type of vertex in the graph
 * @param <E> the type of edge in the graph
 */

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    /**
     * Returns a list of edges representing the path of the least weight
     * (determined by edgeWeight parameter) from a
     * start to end vertex
     *
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight  a function that indicates how to weigh an edge for
     *                    a particular run of the algorithm
     * @return a list of edges representing the path of the least weight from a
     * start to end vertex
     */

    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        LinkedList<V> visited = new LinkedList<>();
        HashMap<V, Double> partialPathWeights = new HashMap<>();
        partialPathWeights.put(source, 0.0);
        HashMap<V, E> nodeToEdge = new HashMap<>();
        Comparator<V> lighterWeight = (node1, node2) -> {
            Double weight1 = partialPathWeights.get(node1);
            Double weight2 = partialPathWeights.get(node2);
            return Double.compare(weight1, weight2);
        };
        PriorityQueue<V> queue = new PriorityQueue<>(lighterWeight);

        for (V node : graph.getVertices()) {
            partialPathWeights.put(node, Double.MAX_VALUE);
        }

        partialPathWeights.put(source, 0.0);
        queue.add(source);

        return this.performDijkstra(graph, destination, visited, queue,
                nodeToEdge, partialPathWeights, edgeWeight);
    }

    /**
     * Recursive method that performs Dijkstra's algorithm. Returns the final
     * path built back up after destination is reached, or an empty list if
     * destination is never reached.
     *
     * @param graph      the graph that the algorithm will be performed on
     * @param dest       the destination vertex
     * @param visited    a linked list containing all vertices that have been
     *                   visited
     * @param queue      a PriorityQueue that sorts vertices by the weight of
     *                   the edge that points to it
     * @param nodeToEdge a hash map containing mappings of a vertex to the
     *                   weighted edge that points to it
     * @param weights    a hash map containing mappings of an edge to its
     *                   specific weight
     * @param edgeWeight a function that indicates how to weigh an edge for
     *                   a particular run of the algorithm
     * @return a list of edges representing the path of the least weight from a
     * start to end vertex
     */

    private List<E> performDijkstra(IGraph<V, E> graph, V dest,
                                    LinkedList<V> visited,
                                    PriorityQueue<V> queue,
                                    HashMap<V, E> nodeToEdge,
                                    HashMap<V, Double> weights,
                                    Function<E, Double> edgeWeight) {
        while (!queue.isEmpty()) {
            V newLightest = queue.poll();
            if (newLightest.equals(dest))
                return this.buildFinalPath(nodeToEdge, dest, graph);

            for (E edge : graph.getOutgoingEdges(newLightest)) {
                /* check if the end node isn't visited and see if the new
                   path is shorter than the original path */
                V neighbor = graph.getEdgeTarget(edge);
                if (!visited.contains(neighbor)) {
                    double newWeight = weights.get(newLightest) +
                            edgeWeight.apply(edge);
                    double currentWeight = weights.get(neighbor);
                    if (newWeight < currentWeight) {
                        weights.put(neighbor, newWeight);
                        queue.remove(neighbor);
                        queue.add(neighbor);
                        nodeToEdge.put(neighbor, edge);
                    }
                }
            }
            visited.add(newLightest);
        }
        return Collections.emptyList();
    }

    /**
     * Reconstructs the final path of edges using the nodeToEdge hash map.
     *
     * @param nodeToEdge a hash map containing mappings of a vertex to the
     *                   weighted edge that points to it
     * @param dest       the destination vertex
     * @param graph      the graph that Dijkstra's algorithm is being performed
     *                   on
     * @return a list of edges representing the path of the least weight from a
     * start to end vertex
     */

    private List<E> buildFinalPath(HashMap<V, E> nodeToEdge, V dest,
                                   IGraph<V, E> graph) {
        LinkedList<E> path = new LinkedList<>();
        V node = dest;
        while (nodeToEdge.containsKey(node)) {
            E incomingEdge = nodeToEdge.get(node);
            path.addFirst(incomingEdge);
            node = graph.getEdgeSource(incomingEdge);
        }
        return path;
    }
}