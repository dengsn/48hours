package com.dengsn.hours;

import com.dengsn.hours.edge.Connection;
import com.dengsn.hours.graph.Graph;
import com.dengsn.hours.graph.GraphVisualizer;
import com.dengsn.hours.util.deserializers.Agency;
import com.dengsn.hours.util.deserializers.Call;
import com.dengsn.hours.util.deserializers.Route;
import com.dengsn.hours.node.Station;
import com.dengsn.hours.util.deserializers.Train;
import com.dengsn.hours.util.csv.CSVDeserializer;
import com.dengsn.hours.util.csv.CSVIterator;
import com.dengsn.hours.util.deserializers.AgencyDeserializer;
import com.dengsn.hours.util.deserializers.CallDeserializer;
import com.dengsn.hours.util.deserializers.ConnectionDeserializer;
import com.dengsn.hours.util.deserializers.RouteDeserializer;
import com.dengsn.hours.util.deserializers.StationDeserializer;
import com.dengsn.hours.util.deserializers.TrainDeserializer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.JFrame;

public class Feed
{
  // Variables
  private final Map<String,Agency> agencies = new HashMap<>();
  private final Map<String,Route> routes = new HashMap<>();
  private final Map<String,Station> stations = new HashMap<>();
  private final Map<String,Train> trains = new HashMap<>();
  
  private final Graph<Station,Connection> graph;
  
  // Constructor
  public Feed(File file)
  {
    System.out.println("Loading transit feed...");
    
    try
    {            
      this.readAgencies(new FileReader("data/agency.txt"));
      this.readRoutes(new FileReader("data/routes.txt"));
      this.readStations(new FileReader("data/stops.txt"));
      this.readTrains(new FileReader("data/trips.txt"));
      this.readCalls(new FileReader("data/stop_times.txt"));
      
      CSVDeserializer<Connection> deserializer = new ConnectionDeserializer(this);
      CSVIterator it = new CSVIterator(new FileReader("data/distances.txt"));
      List<Connection> connections = it.stream()
        .map(deserializer::deserialize)     
        .collect(Collectors.toCollection(LinkedList::new));
      
      System.out.printf("- %d connections%n",it.position());
      
      this.graph = new Graph(this.stations.values(),new LinkedList<>());
      for (Connection c : connections)
      {
        this.graph.getEdges().add(c);
        this.graph.getEdges().add(c.mirror());
      }
    }
    catch (FileNotFoundException ex)
    {
      throw new RuntimeException("Missing file: " + ex.getMessage(),ex);
    }
  }
  public Feed(String fileName)
  {
    this(new File(fileName));
  }
  
  // Read agencies
  private void readAgencies(Reader reader)
  {
    CSVDeserializer<Agency> deserializer = new AgencyDeserializer(this);
    CSVIterator it = new CSVIterator(reader);
    it.stream()
      .map(deserializer::deserialize)
      .forEach(a -> this.agencies.put(a.getId(),a));
      
    System.out.printf("- %d agencies%n",it.position());
  }
  
  // Read routes
  private void readRoutes(Reader reader)
  {
    CSVDeserializer<Route> deserializer = new RouteDeserializer(this);
    CSVIterator it = new CSVIterator(reader);
    it.stream()
      .map(deserializer::deserialize)
      .forEach(r -> this.routes.put(r.getId(),r));
      
    System.out.printf("- %d routes%n",it.position());
  }
  
  // Read stations
  private void readStations(Reader reader)
  {
    CSVDeserializer<Station> deserializer = new StationDeserializer(this);
    CSVIterator it = new CSVIterator(reader);
    it.stream()
      .map(deserializer::deserialize)
      .filter(Objects::nonNull)
      .forEach(s -> this.stations.put(s.getId(),s));
      
    System.out.printf("- %d stations%n",it.position());
  }
  
  // Read trains
  private void readTrains(Reader reader)
  {
    CSVDeserializer<Train> deserializer = new TrainDeserializer(this);
    CSVIterator it = new CSVIterator(reader);
    it.stream()
      .map(deserializer::deserialize)      
      .forEach(t -> this.trains.put(t.getId(),t));
      
    System.out.printf("- %d trains%n",it.position());
  }
  
  // Read calls
  private void readCalls(Reader reader)
  {
    CSVDeserializer<Call> deserializer = new CallDeserializer(this);
    CSVIterator it = new CSVIterator(reader);
    it.stream()
      .map(deserializer::deserialize)      
      .forEach(c -> c.getTrain().getCalls().add(c));
      
    System.out.printf("- %d calls%n",it.position());
  }
  
  // Return the agencies
  public Collection<Agency> getAgencies()
  {
    return this.agencies.values();
  }
  
  // Return an agency by identifier
  public Agency getAgency(String identifier)
  {
    return this.agencies.get(identifier);
  }
  
  // Return the stops
  public Collection<Station> getStations()
  {
    return this.stations.values();
  }
  
  // Return a station by identifier
  public Station getStation(String identifier)
  {
    return this.stations.get(identifier);
  }
  
  // Return the routes
  public Collection<Route> getRoutes()
  {
    return this.routes.values();
  }
  
  // Return a route by identifier
  public Route getRoute(String identifier)
  {
    return this.routes.get(identifier);
  }
  
  // Return the trains
  public Collection<Train> getTrains()
  {
    return this.trains.values();
  }
  
  // Return a train by identifier
  public Train getTrain(String identifier)
  {
    return this.trains.get(identifier);
  }
  
  // Main function
  public static void main(String[] args) throws FileNotFoundException
  {
    Feed g = new Feed("gtfs-iffns-20170228.zip");
    
    /*g.getStations().stream()
      .sorted()
      .forEach(System.out::println);*/
    
    /*g.getTrains().stream()
      .filter(t -> t.getRoute().getType() == Route.Type.RAIL)
      .sorted()
      .limit(10)
      .forEach(System.out::println);*/
    
    JFrame frame = new JFrame("Graph");
    frame.setContentPane(new GraphVisualizer(g.graph));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800,1000);
    frame.setVisible(true); 
    
    g.graph.getShortestPath(g.getStation("lw"),g.getStation("mt"))
      .forEach(System.out::println);
    
    //g.getStopTimes().stream()
    //  .limit(50)
    //  .forEachOrdered(System.out::println);
  }
}
