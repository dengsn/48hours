package com.dengsn.hours;

import com.dengsn.hours.edge.Connection;
import com.dengsn.hours.edge.Journey;
import com.dengsn.hours.edge.Train;
import com.dengsn.hours.graph.Graph;
import com.dengsn.hours.graph.GraphVisualizer;
import com.dengsn.hours.node.Station;
import com.dengsn.hours.util.csv.CSVIterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.JFrame;

public class Feed
{
  // Variables
  private final Map<String,Station> stations;
  private final Map<String,Agency> agencies;
  private final Map<String,Route> types;
  private final Map<String,Train> trains;
  private final Map<String,LinkedList<AbstractCall>> calls;
  private final List<Train> paths;
  
  private final Graph<Station,Connection> graph;
  //private final Graph<JourneyStation,Journey> journeys;
  
  // Constructor
  public Feed() throws FileNotFoundException
  {
    System.out.println("Loading transit feed...");
    
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm:ss").withResolverStyle(ResolverStyle.LENIENT);
    
    try
    {            
      // Read stations
      this.stations = new CSVIterator(new FileReader("data/stations.txt")).stream()
        .map(record -> new Station(record.get(0),record.get(1))
          .useLatitude(record.getDouble(2))
          .useLongitude(record.getDouble(3)))
        .collect(Collectors.toMap(Station::getId,Function.identity()));
      
      // Read connections
      List<Connection> connections = new CSVIterator(new FileReader("data/connections_all.txt")).stream()
        .map(record -> new Connection(this.getStation(record.get(0)),this.getStation(record.get(1)),record.getDouble(2)))
        .collect(Collectors.toCollection(LinkedList::new));
      
      // Create graph
      this.graph = new Graph<>(this.stations.values(),new LinkedList<>());
      connections.forEach(c -> {
        this.graph.getEdges().add(c);
        this.graph.getEdges().add(c.mirror());
      });
      
      // Read agencies
      this.agencies = new CSVIterator(new FileReader("data/agencies.txt")).stream()
        .map(record -> new Agency()
          .useId(record.get(0))
          .useName(record.get(1)))
        .collect(Collectors.toMap(Agency::getId,Function.identity()));
      
      // Read types
      this.types = new CSVIterator(new FileReader("data/types.txt")).stream()
        .map(record ->  new Route()
          .useId(record.get(0))
          .useAgency(this.getAgency(record.get(1)))
          .useName(record.get(2))
          .useType(RouteType.of(record.getInt(3))))
        .collect(Collectors.toMap(Route::getId,Function.identity()));
      
      // Read trains
      this.trains = new CSVIterator(new FileReader("data/trains.txt")).stream()
        .map(record -> new Train()
          .useId(record.get(3))
          .useType(this.getType(record.get(0)))
          .useName(record.get(2))
          .useDirection(record.get(4))
          .useService(record.get(2)))
        .collect(Collectors.toMap(Train::getId,Function.identity()));
      
      // Read train times
      this.calls = new LinkedHashMap<>();
      this.trains.keySet().forEach(id -> this.calls.put(id,new LinkedList<>()));
      
      new CSVIterator(new FileReader("data/train_times.txt")).stream()
        .map(record -> new AbstractCall()
          .useTrain(this.getTrain(record.get(0)))
          .useArrival(LocalTime.parse(record.get(1),timeFormat))
          .useDeparture(LocalTime.parse(record.get(2),timeFormat))
          .useStation(this.getStation(record.get(3)))
          .usePlatform(record.get(4))
          .useSequence(record.getInt(5))
          .useHalting(record.getInt(6) == 0))
        .forEach(call -> this.calls.get(call.getTrain().getId()).add(call));
      
      this.calls.values().forEach(list -> Collections.sort(list,Comparator.comparing(AbstractCall::getSequence)));
    
      this.paths = new LinkedList<>();
      for (Train train : this.trains.values())
      {
        if (train.getType().getType() != RouteType.RAIL)
          continue;
        
        LinkedList<AbstractCall> trainCalls = this.calls.get(train.getId());
        
        AbstractCall first = trainCalls.removeFirst();
        while (!trainCalls.isEmpty())
        {
          AbstractCall last;
          Connection connection;
            
          // Get the connection between two calls
          do 
          {
            last = trainCalls.removeFirst();
            connection = this.graph.getEdge(first.getStation(),last.getStation());
          } while (connection == null && !trainCalls.isEmpty());
            
          if (connection != null)
          {
            train.add(new Journey(connection,first.getDeparture(),last.getArrival()));
            first = last;
          }
        }
      }
    }
    catch (FileNotFoundException ex)
    {
      throw new RuntimeException("Missing file: " + ex.getMessage(),ex);
    }
  }
  
  // Return the stations
  public Collection<Station> getStations()
  {
    return this.stations.values();
  }
  
  // Return a station by identifier
  public Station getStation(String identifier)
  {
    if (this.stations.containsKey(identifier))
      return this.stations.get(identifier);
    else
      throw new NoSuchElementException(identifier);
  }
  
  // Return the agencies
  public Collection<Agency> getAgencies()
  {
    return this.agencies.values();
  }
  
  // Return an agency by identifier
  public Agency getAgency(String identifier)
  {
    if (this.agencies.containsKey(identifier))
      return this.agencies.get(identifier);
    else
      throw new NoSuchElementException(identifier);
  }
  
  // Return the types
  public Collection<Route> getTypes()
  {
    return this.types.values();
  }
  
  // Return a type by identifier
  public Route getType(String identifier)
  {
    if (this.types.containsKey(identifier))
      return this.types.get(identifier);
    else
      throw new NoSuchElementException(identifier);
  }
  
  // Return the trains
  public Collection<Train> getTrains()
  {
    return this.trains.values();
  }
  
  // Return a type by identifier
  public Train getTrain(String identifier)
  {
    if (this.trains.containsKey(identifier))
      return this.trains.get(identifier);
    else
      throw new NoSuchElementException(identifier);
  }
  
  // Main function
  public static void main(String[] args) throws FileNotFoundException
  {
    Feed g = new Feed();
    
    JFrame frame = new JFrame("Graph");
    frame.setContentPane(new GraphVisualizer(g.graph));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800,1000);
    frame.setVisible(true);
    
    /*g.getTrains().stream()
      .filter(t -> t.getType().getType() == RouteType.RAIL)
      .sorted((a,b) -> Integer.valueOf(a.getName()).compareTo(Integer.valueOf(b.getName())))
      .forEach(System.out::println);*/
    
    System.out.println(g.graph.dijkstraAlgorithm().getLongestPossiblePath(g.getStation("ut")));
  }
}
