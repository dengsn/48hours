package com.dengsn.hours;

import com.dengsn.hours.model.Station;
import com.dengsn.hours.model.journey.Journey;
import com.dengsn.hours.model.journey.JourneyStation;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class Algorithm
{
  // Variables  
  private final Feed feed;
  
  // Constructor
  public Algorithm(Feed feed) throws FileNotFoundException
  {    
    this.feed = feed;
  }
  
  // Get the longest possible path from a station
  /*public JourneyPath getLongestPossiblePath(Station station)
  {
    // Check for nulls
    Objects.requireNonNull(station);
    
    // Function for getting possible connections
    Function<JourneyStation,List<Journey>> connections = journey ->
    {
      return null;
    };
    
    System.out.println("Total journeys: " + this.paths.size());
    
    // Create a tree of connections during the day
    Map<JourneyStation,List<Journey>> tree = this.paths.stream()
      .map(Journey::getEnd)
      .collect(Collectors.toMap(Function.identity(),node -> this.paths.stream()
        .filter(e -> e.getStart().getStation().equals(node.getStation()))
        .filter(e -> e.getStart().getTime().isAfter(node.getTime().plusMinutes(1)) && e.getStart().getTime().isBefore(node.getTime().plusMinutes(30)))
        .collect(Collectors.toList()),(a,b) -> 
        {
          List<Journey> c = new LinkedList<>();
          c.addAll(a);
          c.addAll(b);
          return c;
        }));
    
    // Create all possible paths from the tree
    Queue<JourneyPath> queue = new PriorityQueue<>();
    LinkedList<JourneyPath> paths = new LinkedList<>();
    
    // Add all single paths from the tree
    tree.values().forEach(queue::add);
    
    int processed = 0;
    while (!queue.isEmpty())
    {
      // Get the first path in the queue
      JourneyPath path = queue.remove();
      paths.add(path);
      
      processed ++;
      if (processed % 100 == 0)
        System.out.printf("%d paths processed, %d paths in queue, currently at %s%n",processed,queue.size(),path);
      
      // Add all possible spurs to the queue
      tree.get(path.getEnd()).stream()
        .filter(p -> path.covers(p.getStart(),p.getEnd()))
        .map(p -> new JourneyPath(path).add(p))
        .forEach(queue::add);
    }
    
    Collections.sort(paths);
    System.out.println("Total possible paths: " + paths.size());
    
    return paths.getLast();
  }
  
  // Get the longest possible path from a station
  public JourneyPath getLongestPossiblePath()
  {
    return this.graph.getNodes().stream()
      .map(this::getLongestPossiblePath)
      .sorted((a,b) -> Double.compare(b.getWeight(),a.getWeight()))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("Cannot find a longest path"));
  }*/
  
  // Main function
  public static void main(String[] args) throws FileNotFoundException
  {
    System.out.println("Generate the most optimal and longest route to travel in 24 hours on the Dutch");
    System.out.println("railway system -- https://github.com/dengsn/48hours");
    System.out.println();
    
    Feed feed = new Feed(LocalDate.of(2017,6,24));
    
    Station ut = feed.getStations().get("ut");
    Station gn = feed.getStations().get("gn");
    
    feed.getJourneys().stream()
      .filter(n -> n.getStart().getStation().equals(ut))
      .sorted(Comparator.comparing(Journey::getStart))
      .forEach(System.out::println);
    
    //JourneyPath path = algo.getLongestPossiblePath(algo.getStation("ut"));
    
    //System.out.println(path);
    //for (Journey journey : path.getEdges())
      //System.out.println(journey);
  }
}
