package test;

import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import src.City;
import src.IDijkstra;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's
 * correctly, but we still expect you to write more tests using the City and
 * Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will
 * not be graded on those.
 * <p>
 */

public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a
     * different setup, we manually call the setup method at the top of the
     * test.
     * <p>
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph, this.a, this.b,
                        edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b,
                edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    // Cheapest route from NYC to Boston based on handout graph (general case)
    @Test
    public void testDijkstra1() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities1.csv", "data/transport1.csv");
        List<Transport> path = controller2.cheapestRoute("New York City",
                "Boston");
        assertEquals(2, path.size());
        assertEquals(47, getTotalEdgePrice(path), DELTA);

    }

    // Fastest route from NYC to Boston based on handout graph (general case)
    @Test
    public void testDijkstra2() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities1.csv", "data/transport1.csv");
        List<Transport> path = controller2.fastestRoute("New York City",
                "Boston");
        assertEquals(1, path.size());
        assertEquals(50, getTotalEdgeTime(path), DELTA);
    }

    // Fastest route from NYC to Providence based on handout graph (general
    // case)
    @Test
    public void testDijkstra3() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities1.csv", "data/transport1.csv");
        List<Transport> path = controller2.fastestRoute("New York City",
                "Providence");
        assertEquals(2, path.size());
        assertEquals(130, getTotalEdgeTime(path), DELTA);
    }

    // Fastest route from Providence to Baltimore on larger graph (general case)
    @Test
    public void testDijkstra4() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities3.csv", "data/transport3.csv");
        List<Transport> path = controller2.fastestRoute("Providence",
                "Baltimore");
        assertEquals(2, path.size());
        assertEquals(6, getTotalEdgeTime(path), DELTA);
    }

    // Cheapest route from Providence to Baltimore on larger graph (general
    // case)
    @Test
    public void testDijkstra5() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities3.csv", "data/transport3.csv");
        List<Transport> path = controller2.cheapestRoute("Providence",
                "Baltimore");
        assertEquals(1, path.size());
        assertEquals(40, getTotalEdgePrice(path), DELTA);
    }

    // Cheapest route from Providence to New Haven on larger graph (general
    // case)
    @Test
    public void testDijkstra6() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities3.csv", "data/transport3.csv");
        List<Transport> path = controller2.cheapestRoute("Providence",
                "New Haven");
        assertEquals(3, path.size());
        assertEquals(260, getTotalEdgePrice(path), DELTA);
    }

    // Cheapest route from NYC to Providence on larger graph (general case)
    @Test
    public void testDijkstra7() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities3.csv", "data/transport3.csv");
        List<Transport> path = controller2.cheapestRoute("New York City",
                "Providence");
        assertEquals(5, path.size());
        assertEquals(440, getTotalEdgePrice(path), DELTA);
    }

    // Multiple paths with the cheapest cost (edge case)
    @Test
    public void testDijkstra8() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities5.csv", "data/transport5.csv");
        List<Transport> path = controller2.cheapestRoute("Reading",
                "Philadelphia");
        assertEquals(1, path.size());
        assertEquals(50, getTotalEdgePrice(path), DELTA);
    }

    // Multiple paths with the fastest time (edge case)
    @Test
    public void testDijkstra9() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities5.csv", "data/transport5.csv");
        List<Transport> path = controller2.fastestRoute("Reading",
                "Philadelphia");
        assertEquals(1, path.size());
        assertEquals(4, getTotalEdgeTime(path), DELTA);
    }

    // Origin is the same as the destination (edge case)
    @Test
    public void testDijkstra10() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities6.csv", "data/transport6.csv");
        List<Transport> path = controller2.fastestRoute("St. Petersburg",
                "St. Petersburg");
        assertEquals(0, path.size());
    }

    // Destination does not exist in graph (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstra11() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities6.csv", "data/transport6.csv");
        List<Transport> path = controller2.cheapestRoute("Liverpool",
                "Manchester");
        assertEquals(0, path.size());
    }

    // Origin does not exist in graph (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstra12() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities6.csv", "data/transport6.csv");
        List<Transport> path = controller2.cheapestRoute("Leicester",
                "Manchester");
        assertEquals(0, path.size());
    }

    // Graph just consists of a single city, and we're trying to find the
    // cheapest route from that city to itself (edge case)
    @Test
    public void testDijkstra13() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities7.csv", "data/transport7.csv");
        List<Transport> path = controller10.cheapestRoute("Philadelphia",
                "Philadelphia");
        assertEquals(0, path.size());
    }

    // Path from the source city to the destination city does not exist
    @Test
    public void testDijkstra14() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities6.csv", "data/transport6.csv");
        List<Transport> path = controller10.fastestRoute("Berlin",
                "Rome");
        assertEquals(0, path.size());
    }

    // Both the destination and the origin don't exist in the graph
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstra15() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities6.csv", "data/transport6.csv");
        List<Transport> path = controller10.fastestRoute("Munich",
                "Warsaw");
    }

    // Graph just consists of a single city, and we're trying to find the
    // cheapest route from that city to another city not in the graph (edge
    // case)
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstra16() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities7.csv", "data/transport7.csv");
        List<Transport> path = controller10.cheapestRoute("Philadelphia",
                "Pittsburgh");
    }

    // Calling Dijkstra's algorithm on an empty graph (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstra17() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities8.csv", "data/transport8.csv");
        List<Transport> path = controller10.cheapestRoute("Philadelphia",
                "Pittsburgh");
    }

    // Testing getShortestPath on a basic SimpleGraph (general case)
    @Test
    public void testGetShortestPath1() {
        this.createSimpleGraph();
        Dijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeCost = edge -> edge.getWeight();
        List<SimpleEdge> path = dijkstra.getShortestPath(this.graph, this.a,
                this.c, edgeCost);
        assertEquals(1, path.size());
        assertEquals(100, SimpleGraph.getTotalEdgeWeight(path), DELTA);
    }

    // Testing getShortestPath on a basic SimpleGraph (general case)
    @Test
    public void testGetShortestPath2() {
        this.createSimpleGraph();
        Dijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeCost = edge -> 3.0;
        List<SimpleEdge> path = dijkstra.getShortestPath(this.graph, this.a,
                this.e, edgeCost);
        assertEquals(1, path.size());
    }

    /**
     * Returns the total edge price for a given path (only used for testing)
     *
     * @param path the path that the total price is being calculated for
     * @return a double representing the total edge price for a certain path
     * by adding up the edge weights
     */

    public static double getTotalEdgePrice(List<Transport> path) {
        double total = 0;
        for (Transport transport : path) {
            total += transport.getPrice();
        }
        return total;
    }

    /**
     * Returns the total edge time for a given path (only used for testing)
     *
     * @param path the path that the total time is being calculated for
     * @return a double representing the total edge time for a certain path
     * by adding up the edge weights
     */

    public static double getTotalEdgeTime(List<Transport> path) {
        double total = 0;
        for (Transport transport : path) {
            total += transport.getMinutes();
        }
        return total;
    }
}