package com.dengsn.hours.graph.algorithm;

import com.dengsn.hours.graph.Graph;
import com.dengsn.hours.graph.edge.Path;
import com.dengsn.hours.graph.edge.Edge;
import com.dengsn.hours.graph.node.Node;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class YenAlgorithm<N extends Node, E extends Edge<N>>
{
  // Variables
  private final Graph<N,E> graph;
  
  // Constructor
  public YenAlgorithm(Graph<N,E> graph)
  {
    this.graph = graph;
  }
  
  // Calculate the x shortest paths using Eppsteins's algorithm
  public List<Path<N,E>> getShortestPaths(N start, N end, int count)
  {
    // Create a list of solutions
    LinkedList<Path<N,E>> solutions = new LinkedList<>();
    LinkedList<Path<N,E>> heap = new LinkedList<>();
    
    // Add the shortest path to the list
    solutions.add(new DijkstraAlgorithm<>(this.graph).getShortestPath(start,end));
    
    // Add less optimal solutions
    for (int k = 1; k < count; k ++)
    {
      // Get the last solution
      Path<N,E> solution = solutions.getLast();
      
      // Get spur paths from the last solution
      for (int i = 1; i < solution.getEdges().size(); i ++)
      {
        // Remove the root connections from the graph
        Path<N,E> rootPath = solution.subPath(0,i);
        
        // Create a new graph with the root path removed
        Graph<N,E> spurGraph = new Graph<>(this.graph);
        for (Path<N,E> path : solutions)
        {
          if (path.size() >= rootPath.size() && path.subPath(0,i).equals(rootPath))
            spurGraph.getEdges().remove(path.getEdges().get(i));
        }
        rootPath.forEach(spurGraph.getEdges()::remove);
        
        // Calculate the new shortest path
        Path<N,E> spurPath = new DijkstraAlgorithm<>(spurGraph).getShortestPath(rootPath.getEnd(),end);
        if (spurPath == null)
          continue;
        Path<N,E> path = rootPath.combine(spurPath);
        
        // Add the path to the heap
        heap.add(path);
      }
      
      // Check if the heap is empty
      if (heap.isEmpty())
        break;
      
      // Add the most efficient solution
      Collections.sort(heap);
      solutions.add(heap.removeFirst());
    }
    
    // Return the sorted list of solutions
    return solutions.stream()
      .sorted()
      .collect(Collectors.toCollection(LinkedList::new));
  }
}
