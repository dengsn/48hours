package com.dengsn.hours;

import com.dengsn.hours.edge.Connection;
import com.dengsn.hours.graph.Graph;
import com.dengsn.hours.graph.GraphVisualizer;
import com.dengsn.hours.node.Station;
import com.dengsn.hours.util.csv.CSVIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.JFrame;

public class Feed
{
  // Variables
  private final Map<String,Station> stations;
  private final Map<String,Agency> agencies;
  private final Map<String,Route> types;
  private final Map<String,AbstractTrain> trains;
  
  private final Graph<Station,Connection> graph;
  //private List<Train> trains;
  //private final Graph<JourneyStation,Journey> journeys;
  
  // Constructor
  public Feed(File file) throws FileNotFoundException
  {
    //System.setOut(new PrintStream(new FileOutputStream("48hours.log")));
    
    System.out.println("Loading transit feed...");
    
    Pattern platformPattern = Pattern.compile("([a-z]+)\\|(\\d+[abc]?(?:-\\d+[abc]?)?)");
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
      List<Connection> connections = new CSVIterator(new FileReader("data/connections.txt")).stream()
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
        .map(record -> new AbstractTrain()
          .useId(record.get(0))
          .useType(this.getType(record.get(1)))
          .useName(record.get(2))
          .useDirection(record.get(3)))
        .collect(Collectors.toMap(AbstractTrain::getId,Function.identity()));
      
      // Read train times
      Map<String,LinkedList<AbstractCall>> calls = new HashMap<>();
      //trains.keySet().forEach(id -> calls.put(id,new LinkedList<>()));
      new CSVIterator(new FileReader("data/train_times.txt")).stream()
        .map(record -> new AbstractCall()
          .useTrain(this.getTrain(record.get(0)))
          .useArrival(LocalTime.parse(record.get(1),timeFormat))
          .useDeparture(LocalTime.parse(record.get(2),timeFormat))
          .useStation(this.getStation(record.get(3)))
          .usePlatform(record.get(4))
          .useSequence(record.getInt(5)));
    
      /*this.trains = new LinkedList<>();
      List<AbstractTrain> ts = new LinkedList<>(trains.values());
      Collections.sort(ts,Comparator.comparing(AbstractTrain::getName));
      for (AbstractTrain train : ts)
      {
        if (train.getType().getType() != RouteType.RAIL)
          continue;
        
        LinkedList<AbstractCall> trainCalls = calls.get(train.getId());
        if (trainCalls.isEmpty())
          continue;
        
        Collections.sort(trainCalls,Comparator.comparing(r -> r.getSequence()));
        
        AbstractCall first = trainCalls.removeFirst();
        while (!trainCalls.isEmpty())
        {
          try
          {
            AbstractCall last = trainCalls.removeFirst();
            Train t = new Train(new Journey(this.graph.getShortestPath(first.getStation(),last.getStation()),first.getDeparture(),last.getArrival()));
          
            while (!(last.isPickup() || last.isDropoff()))
            {
              AbstractCall intermediate = last;
              last = trainCalls.removeFirst();
            
              t.add(new Journey(this.graph.getShortestPath(intermediate.getStation(),last.getStation()),intermediate.getDeparture(),last.getArrival()));
            }
          
            first = last;
          
            t.useId(train.getId());
            t.useName(train.getType().getLongName() + " " + train.getName());
          
            System.out.println(t);
          
            this.trains.add(t);
          }
          catch (IllegalStateException ex)
          {
            System.out.println(ex.getMessage());
          }
        }
      }*/
    }
    catch (FileNotFoundException ex)
    {
      throw new RuntimeException("Missing file: " + ex.getMessage(),ex);
    }
  }
  public Feed(String fileName) throws FileNotFoundException
  {
    this(new File(fileName));
  }
  
  // Return the stations
  public Collection<Station> getStations()
  {
    return this.stations.values();
  }
  
  // Return a station by identifier
  public Station getStation(String identifier)
  {
    return this.stations.get(identifier);
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
  
  // Return the types
  public Collection<Route> getTypes()
  {
    return this.types.values();
  }
  
  // Return a type by identifier
  public Route getType(String identifier)
  {
    return this.types.get(identifier);
  }
  
  // Return the trains
  public Collection<AbstractTrain> getTrains()
  {
    return this.trains.values();
  }
  
  // Return a type by identifier
  public AbstractTrain getTrain(String identifier)
  {
    return this.trains.get(identifier);
  }
  
  // Main function
  public static void main(String[] args) throws FileNotFoundException
  {
    Feed g = new Feed("gtfs-iffns-20170228.zip");
    
    /*g.getStations().stream()
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
    
    //g.getTrains().forEach(System.out::println);
    
    //System.out.println(g.graph.getShortestPath(g.getStation("lw"),g.getStation("vs")));
    
    //g.getStopTimes().stream()
    //  .limit(50)
    //  .forEachOrdered(System.out::println);
  }
}
