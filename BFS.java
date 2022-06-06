package sol;

import src.IBFS;
import src.IGraph;

import java.util.*;

/**
 * The BFS<V, E> class implements the BFS algorithm that finds the
 * shortest/most direct path from a source vertex to a destination vertex. It
 * does this via the getPath, performBFS, and buildFinalPath methods.
 *
 * @param <V> represents a vertex in the graph
 * @param <E> represents an edge in the graph
 */

public class BFS<V, E> implements IBFS<V, E> {

    /**
     * Returns a list of edges that represent the most direct path (least
     * number of connections) from start to end.
     *
     * @param graph the graph including the vertices and the edges
     * @param start the start vertex
     * @param end   the end vertex
     * @return a list of edges representing the most direct path from the
     * start to end vertices
     */

    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        LinkedList<V> visited = new LinkedList<>();
        LinkedList<V> queue = new LinkedList<>(List.of(start));
        HashMap<V, E> nodeToEdge = new HashMap<>();
        return this.performBFS(graph, end, visited, queue, nodeToEdge);
    }

    /**
     * Recursive method that performs BFS by iterating through a queue until
     * it is empty and then building the path back up when the destination is
     * reached.
     *
     * @param graph      the graph that BFS will be performed on
     * @param end        the destination vertex
     * @param visited    a linked list containing all visited vertices in graph
     * @param queue      a linked list containing vertices that have not been
     *                   explored yet
     * @param nodeToEdge a hash map that contains mappings of vertices to the
     *                   edges that point to them
     * @return a list of edges representing the most direct path from the
     * start to end vertices
     */

    private List<E> performBFS(IGraph<V, E> graph, V end,
                               LinkedList<V> visited, LinkedList<V> queue,
                               HashMap<V, E> nodeToEdge) {
        while (!(queue.isEmpty())) {
            V currentNode = queue.getFirst();
            queue.removeFirst();

            visited.add(currentNode);
            for (E edge : graph.getOutgoingEdges(currentNode)) {
                V nextNode = graph.getEdgeTarget(edge);
                nodeToEdge.put(nextNode, edge);
                if (nextNode.equals(end))
                    return this.buildFinalPath(nodeToEdge, end, graph);
                queue.add(nextNode);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Builds the final path using the nodeToEdge hash map.
     *
     * @param nodeToEdge a hash map that contains mappings of vertices to the
     *                   edges that point to them
     * @param dest       the destination vertex
     * @param graph      the graph that BFS is being performed on
     * @return a list of edges representing the most direct path from source
     * to destination vertices
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