package com.dengsn.hours.graph.algorithm;

import com.dengsn.hours.graph.Graph;
import com.dengsn.hours.graph.edge.Path;
import com.dengsn.hours.graph.edge.Edge;
import com.dengsn.hours.graph.node.Node;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Objects;

public class DijkstraAlgorithm<N extends Node, E extends Edge<N>>
{
  // Variables
  private final Graph<N,E> graph;
  
  // Constructor
  public DijkstraAlgorithm(Graph<N,E> graph)
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
}
