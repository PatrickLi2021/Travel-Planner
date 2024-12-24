package sol;

import src.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * The TravelController class creates TravelController objects that handle
 * communication between the view and the model. TravelController objects
 * call methods in the model to compute the routes and then pass those
 * routes back to the view to display to the user
 */

public class TravelController implements ITravelController<City, Transport> {

    private TravelGraph graph;

    /**
     * Constructor for TravelController class
     */

    public TravelController() {
    }

    /**
     * Returns a string indicating whether the cities and transportation
     * files have been successfully loaded and if the graph has been created
     *
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return a string indicating whether the input CSVs have been
     * successfully loaded. If not, an error message is printed
     */

    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();
        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null;
        };
        try {
            /* pass in string for CSV and function to create City (vertex)
               using the city name. This line converts CSV to a hash map that
               is passed into addVertex */
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        Function<Map<String, String>, Void> addEdge = map -> {
            City origin = this.graph.getCityByName(map.get("origin"));
            City destination = this.graph.getCityByName(map.get(
                    "destination"));
            String transportString = map.get("type");
            TransportType transport = TransportType.fromString(transportString);
            double price = Double.parseDouble(map.get("price"));
            double duration = Double.parseDouble(map.get("duration"));
            this.graph.addEdge(origin, new Transport(origin, destination,
                    transport, price, duration));
            return null;
        };

        /* Pass in string for CSV and function to create add edges to cities.
           Applies the input Function to each city in the hash map */
        try {
            parser.parseTransportation(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }
        return "Successfully loaded cities and transportation files.";
    }

    /**
     * Returns a list of Transport objects representing the fastest route to
     * take from the input source city to the input destination city for a
     * particular graph
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a list of Transport objects containing edges comprising the
     * fastest route from a source to a destination
     */

    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Dijkstra<City, Transport> dijkstra = new Dijkstra<>();
        Function<Transport, Double> edgeTime = transport ->
                transport.getMinutes();
        City sourceCity = this.graph.getCityByName(source);
        City destCity = this.graph.getCityByName(destination);
        return dijkstra.getShortestPath(this.graph, sourceCity, destCity,
                edgeTime);
    }

    /**
     * Returns a list of Transport objects representing the cheapest route to
     * take from the input source city to the input destination city for a
     * particular graph
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a list of Transport objects containing edges comprising the
     * cheapest route from a source to a destination
     */

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Dijkstra<City, Transport> dijkstra = new Dijkstra<>();
        Function<Transport, Double> edgeCost = transport ->
                transport.getPrice();
        City sourceCity = this.graph.getCityByName(source);
        City destCity = this.graph.getCityByName(destination);
        return dijkstra.getShortestPath(this.graph, sourceCity, destCity,
                edgeCost);
    }

    /**
     * Returns a list of Transport objects representing the most direct route
     * to take from the input source city to the input destination city for a
     * particular graph
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return a list of Transport objects containing edges representing the
     * route with the least amount of connections
     */

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        City sourceCity = this.graph.getCityByName(source);
        City destCity = this.graph.getCityByName(destination);
        BFS<City, Transport> bfs = new BFS<>();
        return bfs.getPath(this.graph, sourceCity, destCity);
    }
}