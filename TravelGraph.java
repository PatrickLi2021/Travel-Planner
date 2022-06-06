package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.*;

/**
 * The TravelGraph class is part of the model in this program and uses City
 * objects to represent vertices of the graph and Transport objects to
 * represent edges in the graph through TravelGraph objects. It contains
 * methods to create the graph as well as access certain fields of a graph.
 */

public class TravelGraph implements IGraph<City, Transport> {

    private HashMap<String, City> citiesMap;

    /**
     * Constructor for the TravelGraph class. Initializes the citiesMap field
     */

    public TravelGraph() {
        this.citiesMap = new HashMap<>();
    }

    /**
     * Adds a mapping of a string representing a city name to the actual City
     * object to the hash map field
     *
     * @param vertex a City object representing a vertex in the graph
     */

    @Override
    public void addVertex(City vertex) {
        this.citiesMap.put(vertex.toString(), vertex);
    }

    /**
     * Adds an edge to the set of outgoing edges for a City object
     *
     * @param origin the origin of the edge (AKA the source city)
     * @param edge   the Transport edge coming out of a city that contains a
     *               source, destination, price, and time
     */

    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    /**
     * Returns a set of City objects containing all the vertices/cities in
     * the particular graph
     *
     * @return a set of City objects that represents the vertices of the
     * entire graph
     */

    @Override
    public Set<City> getVertices() {
        return new HashSet<>(this.citiesMap.values());
    }

    /**
     * Returns a city representing the source of the edge extending from it
     *
     * @param edge an edge that comes out of a particular City object
     * @return the City object representing the source of that edge
     */

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    /**
     * Returns a city representing the target of the edge coming to it
     *
     * @param edge an edge that point to a particular City object
     * @return the City object representing the target/destination of that edge
     */

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    /**
     * Returns a set of Transport objects that represents all the outgoing
     * edges from a particular City object
     *
     * @param fromVertex the vertex/city that all the edges extend out from
     * @return a set of Transport objects containing all outgoing edges
     */

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    /**
     * Returns a City object corresponding to the input name
     *
     * @param name a String representing the name of a particular city in the
     *             graph
     * @return a City object in the hash map that has the input name
     * @throws IllegalArgumentException if the string representing the City
     *                                  is not in the hash map
     */

    public City getCityByName(String name) {
        if (!this.citiesMap.containsKey(name)) {
            throw new IllegalArgumentException("City is not in the graph.");
        }
        return this.citiesMap.get(name);
    }
}