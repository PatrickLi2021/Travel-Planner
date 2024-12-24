package test;

import org.junit.Test;
import sol.BFS;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 */

public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     * <p>
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    // Finding most direct path from NYC to Providence based on handout graph
    // (general case)
    @Test
    public void testBFS1() {
        TravelController controller1 = new TravelController();
        controller1.load("data/cities1.csv", "data/transport1.csv");
        List<Transport> path = controller1.mostDirectRoute("New York City",
                "Providence");
        assertEquals(1, path.size());
    }

    // Finding the most direct route from SF to Pasadena based on CSV 4
    // (general case)
    @Test
    public void testBFS2() {
        TravelController controller2 = new TravelController();
        controller2.load("data/cities4.csv", "data/transport4.csv");
        List<Transport> path = controller2.mostDirectRoute("San Francisco",
                "Pasadena");
        assertEquals(2, path.size());
    }

    // Finding most direct path from Philadelphia to Boston using larger
    // graph (general case)
    @Test
    public void testBFS3() {
        TravelController controller3 = new TravelController();
        controller3.load("data/cities3.csv", "data/transport3.csv");
        List<Transport> path = controller3.mostDirectRoute("Philadelphia",
                "Boston");
        assertEquals(3, path.size());
    }

    // Finding most direct path from New Haven to Boston using larger graph
    // (general case)
    @Test
    public void testBFS4() {
        TravelController controller5 = new TravelController();
        controller5.load("data/cities3.csv", "data/transport3.csv");
        List<Transport> path = controller5.mostDirectRoute("New Haven",
                "Boston");
        assertEquals(2, path.size());
    }

    // Finding most direct path from Providence to Baltimore using larger
    // graph (general case)
    @Test
    public void testBFS5() {
        TravelController controller6 = new TravelController();
        controller6.load("data/cities3.csv", "data/transport3.csv");
        List<Transport> path = controller6.mostDirectRoute("Providence",
                "Baltimore");
        assertEquals(1, path.size());
    }

    // No possible path from starting point to destination (edge case)
    @Test
    public void testBFS6() {
        TravelController controller7 = new TravelController();
        controller7.load("data/cities4.csv", "data/transport4.csv");
        List<Transport> path = controller7.mostDirectRoute("Sacramento",
                "San Francisco");
        assertEquals(0, path.size());
    }

    // Origin is the same as the destination (edge case)
    @Test
    public void testBFS7() {
        TravelController controller8 = new TravelController();
        controller8.load("data/cities4.csv", "data/transport4.csv");
        List<Transport> path = controller8.mostDirectRoute("Palo Alto",
                "Palo Alto");
        assertEquals(0, path.size());
    }

    // Origin doesn't exist in the graph (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testBFS8() {
        TravelController controller9 = new TravelController();
        controller9.load("data/cities4.csv", "data/transport4.csv");
        List<Transport> path = controller9.mostDirectRoute("Dallas",
                "Los Angeles");
    }

    // Destination doesn't exist in the graph (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testBFS9() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities4.csv", "data/transport4.csv");
        List<Transport> path = controller10.mostDirectRoute("San Jose",
                "Pittsburgh");
    }

    // Both the origin and the destination don't exist in the graph (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testBFS10() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities4.csv", "data/transport4.csv");
        List<Transport> path = controller10.mostDirectRoute("Louisville",
                "Nashville");
    }

    // Graph just consists of a single city, and we're trying to find the path
    // from that city to itself (edge case)
    @Test
    public void testBFS11() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities7.csv", "data/transport7.csv");
        List<Transport> path = controller10.mostDirectRoute("Philadelphia",
                "Philadelphia");
        assertEquals(0, path.size());
    }

    // Path from the source city to the destination city does not exist (edge
    // case)
    @Test
    public void testBFS12() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities6.csv", "data/transport6.csv");
        List<Transport> path = controller10.mostDirectRoute("Berlin",
                "Rome");
        assertEquals(0, path.size());
    }

    // Calling BFS when there is no graph present (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testBFS13() {
        TravelController controller10 = new TravelController();
        controller10.load("data/cities8.csv", "data/transport8.csv");
        List<Transport> path = controller10.mostDirectRoute("Ashburn",
                "Richmond");
    }

    // Multiple paths with the fewest connections (edge case)
    @Test
    public void testBFS14() {
        TravelController controller = new TravelController();
        controller.load("data/cities5.csv", "data/transport5.csv");
        List<Transport> path = controller.mostDirectRoute("Reading", "Philadelphia");
        assertEquals(2, path.size());
    }
}