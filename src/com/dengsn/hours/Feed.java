package com.dengsn.hours;

import com.dengsn.hours.model.journey.Call;
import com.dengsn.hours.csv.CSVIterator;
import com.dengsn.hours.graph.ConnectionGraph;
import com.dengsn.hours.graph.edge.Connection;
import com.dengsn.hours.graph.edge.Edge;
import com.dengsn.hours.model.Station;
import com.dengsn.hours.model.journey.Journey;
import com.dengsn.hours.model.journey.JourneyGraph;
import com.dengsn.hours.model.journey.JourneyStation;
import com.dengsn.hours.model.journey.Train;
import com.dengsn.hours.model.journey.TrainType;
import com.dengsn.hours.model.util.Agency;
import com.dengsn.hours.model.util.Service;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

public class Feed
{
  // Constants
  private final static DateTimeFormatter SERVICE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
  private final static DateTimeFormatter CALL_FORMATTER = DateTimeFormatter.ofPattern("H:mm:ss").withResolverStyle(ResolverStyle.LENIENT);
  
  // Variables  
  private final LocalDate date;
  
  private final Dict<Station> stations;
  private final List<Connection<Station>> connections;
  private final ConnectionGraph<Station> connectionGraph;
  
  private final Dict<Service> services;
  private final Dict<Agency> agencies;
  private final Dict<TrainType> types;
  private final Dict<Train> trains;
  private final Set<Journey> journeys;
  private final JourneyGraph journeyGraph;
  
  // Constructor
  public Feed(LocalDate date) throws FileNotFoundException
  {    
    this.date = date;

    // Read the transit feed and process the data          
    System.out.printf("Loading transit feed at %s, please wait...%n",this.date);
    
    // Read stations and connections and create the graph
    this.stations = this.readStations();
    this.connections = this.readConnections();
    this.connectionGraph = new ConnectionGraph<>(this.stations.asSet(),this.connections);
    System.out.println("Created connection graph: " + this.connectionGraph);
    
    // Read other data
    this.services = this.readServices();
    this.agencies = this.readAgencies();
    this.types = this.readTypes();
    this.trains = this.readTrains();
        
    // Map the trains to journeys
    this.journeys = new HashSet<>();
    this.trains.stream()
      .filter(train -> train.getType().getType() == TrainType.Type.RAIL)
      .filter(train -> train.getService().isValid(this.date))
      .forEach(train -> 
      {
        try
        {
          // Get the calls for this train and remove the first
          LinkedList<Call> calls = new LinkedList<>(train);
          Call first = calls.remove();
          
          // Iterate over the calls of this train
          while (!calls.isEmpty())
          {
            // Create a new journey
            Journey journey = new Journey(train);
            
            // Get the next call where the train halts
            Call departure = first;
            Call arrival = null;
            do
            {
              // Get a connection between the two calls
              Connection<Station> connection = null;
              do
              {
                // If there are no more calls, then no connection exists
                if (calls.isEmpty())
                  throw new IllegalStateException("No connections found after " + departure.getStation() + " from " + journey);
                
                // Get the next call and fetch a connection
                arrival = calls.remove();
                connection = this.getConnectionGraph().getEdgeBetween(departure.getStation(),arrival.getStation());
              } while (connection == null);
              
              // Create a new journey
              JourneyStation start = new JourneyStation(departure.getStation(),departure.getDeparture(),departure.getPlatform());
              JourneyStation end = new JourneyStation(arrival.getStation(),arrival.getArrival(),arrival.getPlatform());
              journey.add(new Connection<>(start,end,connection.getWeight()));
              
              // Update the last call
              departure = arrival;
            } while (!arrival.isHalting() && !calls.isEmpty());
            
            // Add the journey to the paths
            this.journeys.add(journey);
        
            // Set the first to the next
            first = arrival;
          }
        }
        catch (IllegalStateException ex)
        {
          System.err.println(ex.getMessage());
        }
      });
    
    // Create the journey graph
    this.journeyGraph = new JourneyGraph(this.journeys);
    System.out.println("Created journey graph: " + this.journeyGraph);
  }
  
  // Read stations
  private Dict<Station> readStations() throws FileNotFoundException
  {
    return new CSVIterator(new FileReader("data/stations.txt")).stream()
      .map(record -> new Station()
        .useId(record.get(0))
        .useName(record.get(1))
        .useLatitude(record.getDouble(2))
        .useLongitude(record.getDouble(3)))
      .collect(Dict.collector());
  }
  
  // Read connections
  private List<Connection<Station>> readConnections() throws FileNotFoundException
  {
    // Read connections
    List<Connection<Station>> connections = new CSVIterator(new FileReader("data/connections.txt")).stream()
      .map(record -> new Connection<>(this.stations.get(record.get(0)),this.stations.get(record.get(1)),record.getDouble(2)))
      .collect(Collectors.toCollection(LinkedList::new));
    
    // Add reversed connections
    ListIterator<Connection<Station>> it = connections.listIterator();
    while (it.hasNext())
      it.add(it.next().mirror());
    
    return connections;
  }
  
  // Read services
  private Dict<Service> readServices() throws FileNotFoundException
  {
    Dict<Service> services = new Dict<>();
    
    new CSVIterator(new FileReader("data/services.txt")).stream()
      .forEach(record -> {
        String id = record.get(0);
        if (!services.hasObject(id))
          services.add(new Service().useId(id));
        services.get(id).add(LocalDate.parse(record.get(1),SERVICE_FORMATTER));
      });
    
    return services;
  }
  
  // Read agencies
  private Dict<Agency> readAgencies() throws FileNotFoundException
  {
    return new CSVIterator(new FileReader("data/agencies.txt")).stream()
      .map(record -> new Agency()
       .useId(record.get(0))
        .useName(record.get(1)))
      .collect(Dict.collector());
  }
  
  // Read types
  private Dict<TrainType> readTypes() throws FileNotFoundException
  {
    return new CSVIterator(new FileReader("data/types.txt")).stream()
      .map(record ->  new TrainType()
        .useId(record.get(0))
        .useAgency(this.getAgencies().get(record.get(1)))
        .useName(record.get(2))
        .useType(TrainType.Type.of(record.getInt(3))))
      .collect(Dict.collector());
  }
  
  // Read trains and calls
  private Dict<Train> readTrains() throws FileNotFoundException
  {
    // Read trains
    Dict<Train> trains =  new CSVIterator(new FileReader("data/trains.txt")).stream()
      .map(record -> new Train()
        .useId(record.get(3))
        .useService(this.getServices().get(record.get(1)))
        .useType(this.getTypes().get(record.get(0)))
        .useName(record.get(2))
        .useDirection(record.get(4)))
      .collect(Dict.collector());
    
    // Read calls
    new CSVIterator(new FileReader("data/calls.txt")).stream()
      .map(record -> new Call()
        .useTrain(trains.get(record.get(0)))
        .useArrival(this.date.atTime(LocalTime.parse(record.get(1),CALL_FORMATTER)))
        .useDeparture(this.date.atTime(LocalTime.parse(record.get(2),CALL_FORMATTER)))
        .useStation(this.getStations().get(record.get(3)))
        .usePlatform(record.get(4))
        .useSequence(record.getInt(5))
        .useHalting(record.getInt(6) == 0))
      .sorted((a,b) -> {
        if (a.getTrain().equals(b.getTrain()))
          return Integer.compare(a.getSequence(),b.getSequence());
        else
          return a.getTrain().getName().compareTo(b.getTrain().getName());
      })
      .forEach(call -> call.getTrain().add(call));
    
    return trains;
  }
  
  // Return the date
  public LocalDate getDate()
  {
    return this.date;
  }
  
  // Return the stations
  public Dict<Station> getStations()
  {
    return this.stations;
  }
  
  // Retuen the connections
  public List<Connection<Station>> getConnections()
  {
    return this.connections;
  }
  
  // Return the connection graph
  public ConnectionGraph<Station> getConnectionGraph()
  {
    return this.connectionGraph;
  }
  
  // Return the services
  public Dict<Service> getServices()
  {
    return this.services;
  }
  
  // Return the agencies
  public Dict<Agency> getAgencies()
  {
    return this.agencies;
  }
  
  // Return the types
  public Dict<TrainType> getTypes()
  {
    return this.types;
  }
  
  // Return the trains
  public Dict<Train> getTrains()
  {
    return this.trains;
  }
  
  // Return the journeys
  public Set<Journey> getJourneys()
  {
    return this.journeys;
  }
  
  // Return the journey graph
  public JourneyGraph getJourneyGraph()
  {
    return this.journeyGraph;
  }
}
