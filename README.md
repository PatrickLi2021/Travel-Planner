# Travel Planner

This project implements a travel planner-style application that allows users to plan travel routes that meet their needs. Specifically, users can load a travel graph, then request routes according to one of 3 priorities: **price** (the route that costs the least amount of money), **time** (the route with the lowest total travel time), **directness** (the route with the fewest number of connections).

Users will load graphs and request routes through text prompts in the console. The system's algorithm will compute routes, which will then be printed back to users.

## Background and Usage
Let's say that we have the following route data:

<img width="789" alt="Screenshot 2024-12-24 at 1 19 27 PM" src="https://github.com/user-attachments/assets/7ad230a6-74b4-4753-ba01-a189342de181" />

The data contain **Cities** and **Transportation**, corresponding to the vertices and edges of your graph. The graph is directed, meaning that transportation is not guaranteed to go both ways (i.e. there is a bus that goes from New York City to Providence but not the other way around). Each transportatin entity contains information about its _mode_ (e.g. train, bus, etc.), _cost_ and _time_.

If a user requests the fewest connections to get from New York City to Providence, they'll be given a direct route that costs $40 and takes 225 minutes. If instead they ask for the fastest such route, however, they'll be given a route that goes through Boston and takes a total of 130 minutes.

Here's an example of what the interface looks like for a user to load a graph and request a (fast) route. The valid commands at the prompt are load, fast, cheap, and direct. Note that we have to put "New York City" in quotes since its name is more than one word.

<img width="737" alt="Screenshot 2024-12-24 at 1 25 09 PM" src="https://github.com/user-attachments/assets/7559043b-1000-4dd9-824f-aa5f328d806f" />

If an error occurs, the interface should print an informative message such as 

<img width="732" alt="Screenshot 2024-12-24 at 1 25 38 PM" src="https://github.com/user-attachments/assets/c9c89242-2d37-4d49-8358-5b7127096334" />

The cities and transit options between them should be in CSV format. The system also includes a CSV parser (`TravelCSVParser.java`) and the command-line interface code (`REPL.java`). The latter is associated with an interface `ITravelController` that requires the `load`, `fast`, `cheap`, and `direct` methods.

Running the program (via `Main.java`) will call the `run` method in `REPL.java`. The `run` method presents a prompt to the user (in the terminal window). At this point, the user can enter commands (`load`, `fast`, `cheap`, `direct`) as shown in the above interface demo. The REPL code will call the methods declared in the `ITravelController` interface to execute those commands.

The implementation of `load` (from `ITravelController`) will call methods in the `TravelCSVParser` class. The implementations of `fast`, `cheap`, and `direct` in `TravelController` will call the `IBFS` and `IDijkstra` methods that are implemented.

## Architecture
The classes for this project are organized around a **model-view-controller** architecture.
- The model portion consists of the classes for the graph data strcuture and the implementations of the algorithms.
- The view portion consists of the CSV parser and the command-line REPL.
- The controller handles communication between the view and the model. User requests to load graphs or compute routes will be sent from the view to the controller, the controller will call methods in the model to compute the routes and then pass those routes back to the view to display to the user.

## Relevant Files:
Here are the following relevant classes in the `src` directory:
- A `City` class used to represent the vertices of your graph
- An `IBFS` interface with the header for the method that performs BFS
- An `IDijkstra` interface with the header for the method that performs Dijkstra's lowest cost algorithm
- An `IGraph` interface with methods for creating and getting information from graphs
- An `ITravelController` interface with methods to load graphs from files and find routes with different priorities
- A `Main` class that is used to run the project
- A `REPL` class containing code to allow the user to interact with the program
- A `Transport` class that is used to represent the edges of your graph
- A `TransportType` enum that defines an enumerated type (fixed set of values) for transit options, specifically plane, train, and bus
- A `TravelCSVParser` class to parse CSV files into `Map` objects that can be used to create graphs

Here are the relevant classes in the `sol` directory:
- A `TravelController` class that implements the `ITravelController` interface`
- A `TravelGraph` class that implements the `IGraph` interface
- A `Dijkstra` class that implements the `IDijkstra` interface
- A `BFS` class that implements the `IBFS` interface
