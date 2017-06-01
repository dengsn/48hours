package com.dengsn.hours.graph;

import com.dengsn.hours.node.Node;
import com.dengsn.hours.edge.Edge;
import com.dengsn.hours.edge.Path;
import java.util.PriorityQueue;
import java.util.Queue;

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
    return null;
  }
}
