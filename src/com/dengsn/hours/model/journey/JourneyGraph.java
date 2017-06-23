package com.dengsn.hours.model.journey;

import com.dengsn.hours.graph.Graph;
import com.dengsn.hours.graph.edge.Connection;
import com.dengsn.hours.graph.edge.Path;
import com.dengsn.hours.model.Station;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class JourneyGraph extends Graph<JourneyStation,Journey>
{
  // Variables
  private Map<Station,List<Journey>> tree;
  
  // Constructor
  public JourneyGraph(Collection<Journey> journeys)
  { 
    // Iterate over the journeys
    for (Journey journey : journeys)
    {
      // Add all journeys to the graph
      this.getEdges().add(journey);
      this.getNodes().add(journey.getStart());
      this.getNodes().add(journey.getEnd());
    }
    
    // Calculate the tree
    this.calculateTree();
  }
  
  // Calculate the tree
  public void calculateTree()
  {
    // Map the edges to a station
    this.tree = new HashMap<>();
    for (Journey e : this.getEdges())
    {
      // Map all journeys to a station
      Station station = e.getStart().getStation();
      if (!this.tree.containsKey(station))
        this.tree.put(station,new LinkedList<>());
      this.tree.get(station).add(e);
    }
    
    // Iterate over the stations and sort the list
    for (List<Journey> list : this.tree.values())
      Collections.sort(list,Comparator.comparing(Journey::getStart));
  }
  
  // Returns all edges from a station
  public List<Journey> getEdgesFrom(Station station)
  {
    if (this.tree.containsKey(station))
      return this.tree.get(station);
    else
      return Collections.emptyList();
  }
  
  // Remove edges between stations
  public void removeConnection(Station start, Station end, Graph<Station,Connection<Station>> graph)
  {
    System.out.printf("Trying to remove from %s to %s...%n",start,end);
    
    Path<Station,Connection<Station>> path = graph.getShortestPath(start,end);
    
    ListIterator<Journey> it = this.getEdges().listIterator();
    while (it.hasNext())
    {
      Journey journey = it.next();
      if (path.passes(journey.toStationPath()))
        it.remove();
    }
  }
  
  // Returns all direct edges from a node
  public List<Journey> getDirectEdges(JourneyStation node, List<Journey> ignore, Journey last)
  {
    BiPredicate<Journey,Journey> ignoreForward = (a,b) -> a.passes(b);
    BiPredicate<Journey,Journey> ignoreBackward = (a,b) -> a.passesMirrored(b);
    BiPredicate<Journey,Journey> ignoreBoth = ignoreForward.or(ignoreBackward);
    
    // Get all edges from the node
    List<Journey> edges = this.getEdgesFrom(node.getStation());
    List<Journey> useable;
    
    // Create a collector to get only the first train in any direction
    Collector<Journey,?,LinkedList<Journey>> first = Collector.of(
      LinkedList<Journey>::new, 
      (LinkedList<Journey> list, Journey journey) -> 
      {
        if (!list.stream().map(Journey::toStationPath).anyMatch(e -> e.equals(journey.toStationPath())))
          list.add(journey);
      },
      (LinkedList<Journey> a, LinkedList<Journey> b) ->
      {
        a.addAll(b);
        return a;
      });
    
    // Create a predicate to check if we can catch the train
    Predicate<Journey> catchable = (Journey e) ->
    {
      if (last != null && last.getTrain().equals(e.getTrain()))
        return true;
      else if (last != null && last.getEnd().getPlatform().equals(e.getStart().getPlatform()) && e.getStart().getTime().isAfter(node.getTime()))
        return true;
      else
        return e.getStart().getTime().isAfter(node.getTime().plusMinutes(2));
    };
          
    // Get the recent edges with both directions ignored
    useable = edges.stream()
      .filter(catchable)
      .filter(e -> e.getStart().getTime().isBefore(node.getTime().plusMinutes(30)))
      .filter(e -> !ignore.stream().anyMatch(j -> ignoreBoth.test(e,j)))
      .collect(first);
    if (!useable.isEmpty())
      return useable;
    
    // Get the recent edges with forward directions ignored
    useable = edges.stream()
      .filter(catchable)
      .filter(e -> e.getStart().getTime().isBefore(node.getTime().plusMinutes(30)))
      .filter(e -> !ignore.stream().anyMatch(j -> ignoreForward.test(e,j)))
      .collect(first);
    if (!useable.isEmpty())
      return useable;
    
    // Get the next edges with both directions ignored
    useable = edges.stream()
      .filter(catchable)
      .filter(e -> !ignore.stream().anyMatch(j -> ignoreBoth.test(e,j)))
      .findFirst()
      .map(Collections::singletonList)
      .orElseGet(Collections::emptyList);
    if (!useable.isEmpty())
      return useable;
    
    // Get the next edges with forward directions ignored
    useable = edges.stream()
      .filter(catchable)
      .filter(e -> !ignore.stream().anyMatch(j -> ignoreForward.test(e,j)))
      .findFirst()
      .map(Collections::singletonList)
      .orElseGet(Collections::emptyList);
    return useable;
  }
  
  // Returns all direct edges from a node
  @Override public List<Journey> getDirectEdges(JourneyStation node, List<Journey> ignore)
  {
    return this.getDirectEdges(node,ignore,null);
  }
  
  // Returns all direct edges from a node without ignore list
  @Override public List<Journey> getDirectEdges(JourneyStation node)
  {
    return this.getDirectEdges(node,new LinkedList<>());
  }
  
  // Returns all neighbors of a node
  @Override public Set<JourneyStation> getNeighbors(JourneyStation node)
  {
    return this.getDirectEdges(node).stream()
      .map(e -> (JourneyStation)e.getOpposite(node))
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }
  
  // Returns the longest path possible
  public JourneyPath getLongestPossiblePath(JourneyStation start)
  {
    // Check for nulls
    Objects.requireNonNull(start);
    
    // Create helper variables
    LocalDateTime h10 = start.getTime().toLocalDate().atTime(10,0);
    LocalDateTime h14 = start.getTime().toLocalDate().atTime(14,0);
    
    // Create a new queue
    //Queue<JourneyPath> queue = new LinkedList<>();
    //Queue<JourneyPath> queue = new PriorityQueue<>((a,b) -> Double.compare(b.getWeight() / b.getDuration(),a.getWeight() / a.getDuration()));
    //Queue<JourneyPath> queue = new PriorityQueue<>((a,b) -> Double.compare(b.getWeight(),a.getWeight()));
    Queue<JourneyPath> queue = new PriorityQueue<>((JourneyPath a, JourneyPath b) -> Double.compare(a.getDuration(),b.getDuration()));
    JourneyPath longest = null;
    
    // Add all starting paths
    this.getDirectEdges(start).stream()
      .map(JourneyPath::new)
      .forEach(queue::add);
    
    int processed = 0;
    while (!queue.isEmpty())
    {
      // Get the first path
      JourneyPath path = queue.remove();
      
      processed ++;
      if (processed % 10000 == 0)
        System.out.printf("%d paths processed, %d paths in queue, currently at %s%n",processed,queue.size(),path);
      
      // Iterate over connections from this station
      Iterator<JourneyPath> it = this.getDirectEdges(path.getEnd(),path.getEdges(),path.getEdges().get(path.size() - 1)).stream()
        .sorted(Comparator.comparing(Journey::getStart))
        .map(p -> new JourneyPath(path).add(p))
        .filter(p -> p.getEnd().getTime().isBefore(start.getTime().plusDays(1).toLocalDate().atStartOfDay()))
        .filter(p -> p.getEnd().getTime().isBefore(h14) || p.covers(new Station().useId("ut"),h10,h14))
        .iterator();
      while (it.hasNext())
      {
         JourneyPath p = it.next();
        
        // Add the connection to the stack
        queue.add(p);
        
        // Check if this path is the longest
        if (longest == null || p.compareTo(longest) > 0)
        {
          longest = p;
          System.out.println("Found new longest path: " + longest);
        }
      }
    }

    return longest;
  }
  public JourneyPath getLongestPossiblePath(Station start, LocalDateTime time)
  {
    return this.getLongestPossiblePath(start.atTime(time));
  }
}
