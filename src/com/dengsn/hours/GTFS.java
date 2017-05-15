package com.dengsn.hours;

import com.dengsn.hours.csv.CSVIterator;
import com.dengsn.hours.model.agency.Agency;
import com.dengsn.hours.model.agency.AgencyDeserializer;
import com.dengsn.hours.model.route.Route;
import com.dengsn.hours.model.route.RouteDeserializer;
import com.dengsn.hours.model.station.Station;
import com.dengsn.hours.model.station.StationDeserializer;
import com.dengsn.hours.model.stoptime.StopTime;
import com.dengsn.hours.model.stoptime.StopTimeDeserializer;
import com.dengsn.hours.model.trip.Trip;
import com.dengsn.hours.model.trip.TripDeserializer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.dengsn.hours.model.station.StationType;
import com.dengsn.hours.csv.CSVDeserializer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GTFS
{
  // Variables
  private final Map<String,Agency> agencies = new HashMap<>();
  private final Map<String,Route> routes = new HashMap<>();
  private final Map<String,Station> stations = new HashMap<>();
  private final Map<String,Trip> trips = new HashMap<>();
  
  // Constructor
  public GTFS(File file)
  {
    System.out.println("Transit feed is loading...");
    
    try
    {            
      // Get the GTFS zip file
      ZipFile zip = new ZipFile(file);
      
      // Load agencies
      ZipEntry af = zip.getEntry("agency.txt");
      AgencyDeserializer ad = new AgencyDeserializer(this);
      
      new CSVIterator(new InputStreamReader(zip.getInputStream(af))).stream()
        .map(ad::deserialize)
        .forEach(a -> this.agencies.put(a.getIdentifier(),a));
      
      System.out.printf("- %d agencies%n",this.agencies.size());
      
      // Load routes
      ZipEntry rf = zip.getEntry("routes.txt");
      RouteDeserializer rd = new RouteDeserializer(this);
      
      new CSVIterator(new InputStreamReader(zip.getInputStream(rf))).stream()
        .map(rd::deserialize)
        .forEach(r -> this.routes.put(r.getIdentifier(),r));
      
      System.out.printf("- %d routes%n",this.routes.size());
    
      // Load stations
      ZipEntry sf = zip.getEntry("stops.txt");
      StationDeserializer sd = new StationDeserializer(this);
      
      new CSVIterator(new InputStreamReader(zip.getInputStream(sf))).stream()
        .map(sd::deserialize)
        .forEach(s -> this.stations.put(s.getIdentifier(),s));
      
      System.out.printf("- %d stops%n",this.stations.size());
      
      // Load trips
      ZipEntry tf = zip.getEntry("trips.txt");
      TripDeserializer td = new TripDeserializer(this);
      
      new CSVIterator(new InputStreamReader(zip.getInputStream(tf))).stream()
        .map(td::deserialize)
        .forEach(t -> this.trips.put(t.getIdentifier(),t));
      
      System.out.printf("- %d trips%n",this.trips.size());
      
      // Load stop times
      ZipEntry stf = zip.getEntry("stop_times.txt");
      CSVDeserializer<StopTime> std = new StopTimeDeserializer(this);
      
      new CSVIterator(new InputStreamReader(zip.getInputStream(stf))).stream()
        .map(std::deserialize)
        .forEach(st -> st.getTrip().getStopTimes().add(st));
      
      System.out.println("- stop times");
    }
    catch (IOException ex)
    {
      throw new RuntimeException("Missing file: " + ex.getMessage(),ex);
    }
  }
  public GTFS(String fileName)
  {
    this(new File(fileName));
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
  
  // Return the trips
  public Collection<Trip> getTrips()
  {
    return this.trips.values();
  }
  
  // Return a trip by identifier
  public Trip getTrip(String identifier)
  {
    return this.trips.get(identifier);
  }
  
  // Main function
  public static void main(String[] args) throws FileNotFoundException
  {
    GTFS g = new GTFS("gtfs-iffns-20170228.zip");
    
    g.getStations().stream()
      .filter(s -> s.getType() != StationType.STATION)
      .sorted()
      .forEach(System.out::println);
    
    /*g.getTrips().stream()
      .filter(t -> t.getRoute().getType() == RouteType.RAIL)
      .sorted()
      .limit(10)
      .forEach(System.out::println);*/
    
    //g.getStopTimes().stream()
    //  .limit(50)
    //  .forEachOrdered(System.out::println);
  }
}
