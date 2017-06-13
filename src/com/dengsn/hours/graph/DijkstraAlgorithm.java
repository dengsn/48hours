package com.dengsn.hours.graph;

import com.dengsn.hours.edge.Edge;
import com.dengsn.hours.edge.Path;
import java.util.PriorityQueue;
import java.util.Queue;
import com.dengsn.hours.node.Node;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DijkstraAlgorithm<N extends Node, E extends Edge<N>>
{
  // Variables
  private final Graph<N,E> graph;
  
  // Constructor
  DijkstraAlgorithm(Graph<N,E> graph)
  {
    this.graph = graph;
  }
  
  // Get the shortest path between two stations using Dijkstra's algorithm
  public Path<N,E> getShortestPath(N start, N end)
  {
    // Check for nulls
    Objects.requireNonNull(start);
    Objects.requireNonNull(end);
    
    // Check if start equals end
    if (start.equals(end))
      throw new IllegalStateException("Cannot find a shortest path between equal nodes: " + start.toString());
    
    // Check if start or end has connections
    if (this.graph.getDirectEdges(start).isEmpty())
      throw new IllegalStateException("Cannot find a shortest path from a node with no edges: " + start.toString());
    if (this.graph.getDirectEdges(end).isEmpty())
      throw new IllegalStateException("Cannot find a shortest path to a node with no edges: " + end.toString());
    
    // Create the queue
    Queue<Path<N,E>> queue = new PriorityQueue<>();
    
    // Handle first station
    this.graph.getDirectEdges(start).stream()
      .map(Path<N,E>::new)
      .forEach(queue::add);
    
    // Iterate over all stations in the queue
    while (!queue.isEmpty())
    {
      // If the next path is to our end, then return it
      Path<N,E> path = queue.remove();
      if (path.getEnd().equals(end))
        return path;
      
      // Iterate over connections from this station
      this.graph.getDirectEdges(path.getEnd(),path.getEdges()).stream()
        .map(connection -> new Path<>(path).add(connection))
        .forEach(alternative -> 
        {
          // If the distance is less, then add to the queue
          Path<N,E> current = queue.stream()
            .filter(p -> p.getEnd().equals(alternative.getEnd()))
            .findFirst()
            .orElse(null);
          if (current == null || alternative.compareTo(current) < 0)
            queue.add(alternative);
        });
    }
    
    // No shortest path was found
    throw new IllegalStateException("Cannot find a shortest path between " + start.toString() + " and " + end.toString());
  }
  
  // Get the longest possible path from a station
  public Path<N,E> getLongestPossiblePath(N node)
  {
    // Check for nulls
    Objects.requireNonNull(node);

    // Check if node has connections
    if (this.graph.getDirectEdges(node).isEmpty())
      throw new IllegalStateException("Cannot find a shortest path from a node with no edges: " + node.toString());
    
    // Create the queue
    Queue<Path<N,E>> queue = new PriorityQueue<>();
    Map<N,Path<N,E>> paths = new LinkedHashMap<>();
    
    // Handle first station
    this.graph.getDirectEdges(node).stream()
      .map(Path<N,E>::new)
      .forEach(queue::add);
    
    // Iterate over all stations in the queue
    while (!queue.isEmpty())
    {
      // Get the next path from the queue
      Path<N,E> path = queue.remove();
      
      // Iterate over connections from this station
      this.graph.getDirectEdges(path.getEnd(),path.getEdges()).stream()
        .map(connection -> new Path<>(path).add(connection))
        .forEach(alternative -> 
        {
          // If the distance is less, then add to the queue
          Path<N,E> current = paths.get(alternative.getEnd());
          if (current == null || alternative.compareTo(current) > 0)
          {
            queue.add(alternative);
            paths.put(alternative.getEnd(),alternative);
          }
        });
    }
    
    // Return the longest path available
    return paths.values().stream()
      .sorted((a,b) -> Double.compare(b.getWeight(),a.getWeight()))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("Cannot find a longest path from " + node));
  }
  
  // Get the longest possible path from a station
  public Path<N,E> getLongestPossiblePath()
  {
    return this.graph.getNodes().stream()
      .map(this::getLongestPossiblePath)
      .sorted((a,b) -> Double.compare(b.getWeight(),a.getWeight()))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("Cannot find a longest path"));
  }
}
