package test;

import org.junit.Test;
import sol.TravelGraph;
import src.City;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more
 * tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will
 * not be graded on those.
 * <p>
 */

public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a
     * different setup, we manually call the setup method at the top of the
     * test.
     * <p>
     */

    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    @Test
    public void testGetVertices() {
        this.createSimpleGraph();

        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

    // Adding a vertex/city to an empty graph and checking if it's in it
    @Test
    public void testAddVertex1() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Providence");
        graph.addVertex(c1);
        for (City c : graph.getVertices()) {
            assertEquals("Providence", c.toString());
        }
    }

    // Adding 3 vertices/cities to an empty graph and checking if they're in it
    @Test
    public void testAddVertex2() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Beijing");
        City c2 = new City("Shanghai");
        City c3 = new City("Nanjing");
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(c3);
        Set<City> set = graph.getVertices();
        assertTrue(set.contains(c1));
        assertTrue(set.contains(c2));
        assertTrue(set.contains(c3));
    }

    // Trying to add a vertex/city to a graph in which the city already exists
    @Test
    public void testAddVertex3() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Austin");
        graph.addVertex(c1);
        graph.addVertex(c1);
        assertEquals(1, graph.getVertices().size());
    }

    // Trying to add 2 cities with the same name to the graph
    @Test
    public void testAddVertex4() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Austin");
        City c2 = new City("Austin");
        graph.addVertex(c1);
        graph.addVertex(c2);
        assertEquals(1, graph.getVertices().size());
    }

    // Adding vertices to the graph, then seeing if they are contained in the
    // set returned by getVertices
    @Test
    public void testGetVertices1() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Barcelona");
        City c2 = new City("Phoenix");
        City c3 = new City("Memphis");
        City c4 = new City("Oklahoma City");
        City c5 = new City("Salt Lake City");
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(c3);
        graph.addVertex(c4);
        graph.addVertex(c5);
        Set<City> set = graph.getVertices();
        assertEquals(set.size(), 5);
        assertTrue(set.contains(c1));
        assertTrue(set.contains(c2));
        assertTrue(set.contains(c3));
        assertTrue(set.contains(c4));
        assertTrue(set.contains(c5));
    }

    // Calling getVertices on a graph with no vertices added (edge case)
    @Test
    public void testGetVertices2() {
        TravelGraph graph = new TravelGraph();
        Set<City> set = graph.getVertices();
        assertEquals(0, set.size());
    }

    // Adding an edge to a city in the map and checking if it is in the list
    // of outgoing edges for that city
    @Test
    public void testAddEdge1() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Atlanta");
        City c2 = new City("Orlando");
        Transport edge = new Transport(c1, c2, TransportType.BUS, 10, 5);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addEdge(c1, edge);
        Set<Transport> outgoing = c1.getOutgoing();
        assertTrue(outgoing.contains(edge));
        assertEquals(1, outgoing.size());
    }

    // Adding multiple outgoing edges to a city in the map and seeing if the
    // size of the list of outgoing edges for that city matches
    @Test
    public void testAddEdge2() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Atlanta");
        City c2 = new City("Orlando");
        City c3 = new City("Miami");
        City c4 = new City("Jacksonville");
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(c3);
        graph.addVertex(c4);
        Transport edge1 = new Transport(c1, c2, TransportType.BUS, 10, 30);
        Transport edge2 = new Transport(c1, c3, TransportType.PLANE, 20, 40);
        Transport edge3 = new Transport(c1, c4, TransportType.TRAIN, 30, 50);
        graph.addEdge(c1, edge1);
        graph.addEdge(c1, edge2);
        graph.addEdge(c1, edge3);
        Set<Transport> outgoing = c1.getOutgoing();
        assertTrue(outgoing.contains(edge1));
        assertTrue(outgoing.contains(edge2));
        assertTrue(outgoing.contains(edge3));
        assertEquals(3, outgoing.size());
    }

    // Adding an edge between 2 cities in a 3-vertex graph and making sure
    // that the other vertex's outgoing edges set is empty
    @Test
    public void testAddEdge3() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Raleigh");
        City c2 = new City("Charlotte");
        City c3 = new City("Annapolis");
        Transport edge = new Transport(c2, c3, TransportType.BUS, 10, 5);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(c3);
        graph.addEdge(c2, edge);
        Set<Transport> outgoing = c1.getOutgoing();
        assertEquals(outgoing.size(), 0);
        assertTrue(!outgoing.contains(edge));
    }

    // Adding an edge in which the source and destination cities are the same
    // (edge case)
    @Test
    public void testAddEdge4() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Raleigh");
        Transport edge = new Transport(c1, c1, TransportType.BUS, 15, 5);
        graph.addVertex(c1);
        graph.addEdge(c1, edge);
        Set<Transport> outgoing = c1.getOutgoing();
        assertEquals(outgoing.size(), 1);
        assertTrue(outgoing.contains(edge));
    }

    // Adding 2 different edges with the same attributes between the same 2
    // cities (edge case)
    @Test
    public void testAddEdge5() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Raleigh");
        City c2 = new City("Chicago");
        Transport edge1 = new Transport(c1, c2, TransportType.BUS, 10, 5);
        Transport edge2 = new Transport(c1, c2, TransportType.BUS, 10, 5);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addEdge(c1, edge1);
        graph.addEdge(c1, edge2);
        Set<Transport> outgoing = c1.getOutgoing();
        assertEquals(outgoing.size(), 2);
        assertTrue(outgoing.contains(edge1) &&
                outgoing.contains(edge2));
    }

    // Adding the same edge twice between 2 cities (edge case)
    @Test
    public void testAddEdge6() {
        TravelGraph graph = new TravelGraph();
        City c3 = new City("Evanston");
        City c4 = new City("Springfield");
        Transport edge1 = new Transport(c3, c4, TransportType.BUS, 20, 5);
        graph.addVertex(c3);
        graph.addVertex(c4);
        graph.addEdge(c3, edge1);
        graph.addEdge(c3, edge1);
        Set<Transport> outgoing = c3.getOutgoing();
        assertEquals(outgoing.size(), 1);
        assertTrue(outgoing.contains(edge1));
    }

    // Testing to see if the sources of various edges added to the cities in
    // the graph are correct (general cases)
    @Test
    public void testGetEdgeSource1() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Cincinnati");
        City c2 = new City("Cleveland");
        City c3 = new City("Akron");
        City c4 = new City("Toledo");
        Transport edge1 = new Transport(c1, c2, TransportType.BUS, 10, 5);
        Transport edge2 = new Transport(c3, c1, TransportType.PLANE, 40, 1);
        Transport edge3 = new Transport(c2, c3, TransportType.TRAIN, 32, 234);
        Transport edge4 = new Transport(c4, c1, TransportType.BUS, 64, 36);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(c3);
        graph.addVertex(c4);
        assertEquals(graph.getEdgeSource(edge1).toString(), "Cincinnati");
        assertEquals(graph.getEdgeSource(edge2).toString(), "Akron");
        assertEquals(graph.getEdgeSource(edge3).toString(), "Cleveland");
        assertEquals(graph.getEdgeSource(edge4).toString(), "Toledo");
    }

    // Running getEdgeSource on an edge in which the source and the
    // destination are the same
    @Test
    public void testGetEdgeSource2() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Cincinnati");
        Transport edge1 = new Transport(c1, c1, TransportType.BUS, 10, 5);
        graph.addVertex(c1);
        graph.addEdge(c1, edge1);
        assertEquals(graph.getEdgeSource(edge1).toString(), "Cincinnati");
    }

    // Running getEdgeSource when there are 2 edges between 2 cities in a graph
    @Test
    public void testGetEdgeSource3() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Ithaca");
        City c2 = new City("Syracuse");
        Transport edge1 = new Transport(c1, c2, TransportType.BUS, 10, 5);
        Transport edge2 = new Transport(c1, c2, TransportType.TRAIN, 144, 12);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addEdge(c1, edge1);
        graph.addEdge(c2, edge2);
        assertEquals(graph.getEdgeSource(edge1).toString(), "Ithaca");
        assertEquals(graph.getEdgeSource(edge2).toString(), "Ithaca");
    }

    // Getting the target city of an edge connecting 2 cities in the graph
    // (general case)
    @Test
    public void testGetEdgeTarget1() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Ithaca");
        City c2 = new City("Syracuse");
        Transport edge1 = new Transport(c1, c2, TransportType.BUS, 10, 5);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addEdge(c1, edge1);
        assertEquals(graph.getEdgeTarget(edge1).toString(), "Syracuse");
    }

    // Getting the target city of an edge in which the source and the target
    // are the same city
    @Test
    public void testGetEdgeTarget2() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Flushing");
        Transport edge1 = new Transport(c1, c1, TransportType.BUS, 10, 5);
        graph.addVertex(c1);
        graph.addEdge(c1, edge1);
        assertEquals(graph.getEdgeTarget(edge1).toString(), "Flushing");
    }

    // Getting the outgoing edges of a city with 3 edges connecting from it
    @Test
    public void testGetOutgoingEdges1() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Flushing");
        City c2 = new City("Corning");
        City c3 = new City("Manhattan");
        City c4 = new City("Soho");
        Transport edge1 = new Transport(c1, c2, TransportType.BUS, 10, 5);
        Transport edge2 = new Transport(c1, c3, TransportType.PLANE, 12, 5);
        Transport edge3 = new Transport(c1, c4, TransportType.TRAIN, 144, 6);
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(c3);
        graph.addVertex(c4);
        graph.addEdge(c1, edge1);
        graph.addEdge(c1, edge2);
        graph.addEdge(c1, edge3);
        assertEquals(3, graph.getOutgoingEdges(c1).size());
    }

    // Getting the outgoing edges of a city with no outgoing edges (edge case)
    @Test
    public void testGetOutgoingEdges2() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Marietta");
        graph.addVertex(c1);
        assertEquals(0, graph.getOutgoingEdges(c1).size());
    }

    // Getting the outgoing edges when the same edge is being added twice to
    // a city (edge case)
    @Test
    public void testGetOutgoingEdges3() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Seattle");
        City c2 = new City("Olympia");
        Transport edge1 = new Transport(c1, c2, TransportType.BUS, 10, 5);
        graph.addEdge(c1, edge1);
        graph.addEdge(c1, edge1);
        assertEquals(1, graph.getOutgoingEdges(c1).size());
    }

    // Creating 4 cities in a graph and getting the objects by their name
    // (general case)
    @Test
    public void testGetCityByName() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Sacramento");
        City c2 = new City("San Jose");
        City c3 = new City("San Francisco");
        City c4 = new City("Los Angeles");
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.addVertex(c3);
        graph.addVertex(c4);
        assertEquals(c1, graph.getCityByName("Sacramento"));
        assertEquals(c2, graph.getCityByName("San Jose"));
        assertEquals(c3, graph.getCityByName("San Francisco"));
        assertEquals(c4, graph.getCityByName("Los Angeles"));
    }

    // Trying to get cities by name from a graph in which a City object for
    // that city was never created
    @Test(expected = IllegalArgumentException.class)
    public void testGetCityByNameException1() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Dallas");
        City c2 = new City("Boise");
        graph.addVertex(c1);
        graph.addVertex(c2);
        graph.getCityByName("Houston");
    }

    // Trying to get cities by name from a graph in which a City object for
    // that city was created but never added to the graph (edge case)
    @Test(expected = IllegalArgumentException.class)
    public void testGetCityByNameException() {
        TravelGraph graph = new TravelGraph();
        City c1 = new City("Honolulu");
        City c2 = new City("Boise");
        graph.addVertex(c1);
        graph.getCityByName("Boise");
    }
}
