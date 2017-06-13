package com.dengsn.hours.graph;

import com.dengsn.hours.edge.Edge;
import com.dengsn.hours.edge.Path;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import com.dengsn.hours.node.Node;

public class Graph<N extends Node, E extends Edge<N>>
{
  // Variables
  private final Collection<N> nodes;
  private final List<E> edges;
  private BiPredicate<N,E> adjacency = (n,e) -> e.getStart().equals(n);
  
  // Constructor
  public Graph(Collection<N> nodes, List<E> edges)
  {
    this.nodes = nodes;
    this.edges = edges;
  }
  public Graph(Graph<N,E> source)
  {
    this.nodes = source.nodes;
    this.edges = source.edges;
  }
  
  // Returns all nodes
  public Collection<N> getNodes()
  {
    return this.nodes;
  }
  
  // Returns a node by name
  public N getNode(String node)
  {
    for (N n : this.getNodes())
    {
      if (n.getName().equalsIgnoreCase(node))
        return n;
      if (n.getId().equalsIgnoreCase(node))
        return n;
    }
    
    throw new NoSuchElementException(node);
  }
  
  // Returns all edges
  public List<E> getEdges()
  {
    return this.edges;
  }
  
  // Returns an edge between two nodes if existent
  public E getEdge(Node start, Node end)
  {
    return this.edges.stream()
      .filter(e -> e.getStart().equals(start) && e.getEnd().equals(end))
      .findFirst()
      .orElse(null);
  }
  
  // Returns the predicate for when a edge is adjacent to a node
  public BiPredicate<N,E> getAdjacency()
  {
    return this.adjacency;
  }
  
  // Uses a predicate for when an edge is direct to a node
  public Graph<N,E> useAdjacency(BiPredicate<N,E> adjacency)
  {
    this.adjacency = adjacency;
    return this;
  }
  
  // Returns all direct edges from a node
  public LinkedList<E> getDirectEdges(N node, List<E> ignore)
  {
    return this.getEdges().stream()
      .filter(e -> this.adjacency.test(node,e))
      .filter(e -> !ignore.contains(e))
      .collect(Collectors.toCollection(LinkedList::new));
  }
  public LinkedList<E> getDirectEdges(N node)
  {
    return this.getDirectEdges(node,new LinkedList<>());
  }  
  
  // Returns all neighbors of a node
  public Set<N> getNeighbors(N node)
  {
    return this.getDirectEdges(node).stream()
      .map(e -> (N)e.getOpposite(node))
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }
  
  // Calculates the forced connection from a node
  private Path<N,E> getForcedPath(E edge, List<E> ignore)
  {
    // Get the direct connections from this connection
    ignore.add(edge);
    LinkedList<E> list = this.getDirectEdges(edge.getEnd(),ignore);
    Path<N,E> path = new Path<>(edge);
    
    // Check for dead ends and intersections
    if (list.isEmpty() || list.size() > 1)
      return path;
    
    // Add all forced connections from the current connection
    E next = list.getFirst();
    return path.combine(this.getForcedPath(next,ignore));
  }
   
  // Returns all forced edges from a node
  public LinkedList<Path<N,E>> getForcedEdges(N node, List<E> ignore)
  {
    return this.getDirectEdges(node,ignore).stream()
      .map(c -> this.getForcedPath(c,ignore))
      .collect(Collectors.toCollection(LinkedList::new));
  }
  public LinkedList<Path<N,E>> getForcedEdges(N node)
  {
    return this.getForcedEdges(node,new LinkedList<>());
  }
  
  // Get the shortest path between two nodes
  public Path<N,E> getShortestPath(N start, N end)
  {
    return new DijkstraAlgorithm<>(this).getShortestPath(start,end);
  }
  
  // Get K-shortest paths between two nodes
  public List<Path<N,E>> getShortestPaths(N start, N end, int count)
  {
    return new YenAlgorithm<>(this).getShortestPaths(start,end,count);
  }
}
