package com.dengsn.hours.model.journey;

import com.dengsn.hours.graph.edge.Path;
import com.dengsn.hours.graph.node.Node;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

public class JourneyPath extends Path<JourneyStation,Journey>
{
  // Constructor
  public JourneyPath() 
  {
    super();
  }
  public JourneyPath(Journey edge)
  {
    super(edge);
  }
  public JourneyPath(Path<JourneyStation,Journey> source)
  {
    super(source);
  }
  
  // Add a edge to this path
  @Override public JourneyPath add(Journey edge)
  {
    return (JourneyPath)super.add(edge);
  }
  
  // Add an adjacent path to this path
  @Override public JourneyPath combine(Path<JourneyStation,Journey> path)
  {
    return (JourneyPath)super.combine(path);
  }
  
  // Get the first part of a pat until the given node
  @Override public JourneyPath subPath(int fromIndex, int toIndex)
  {
    return (JourneyPath)super.subPath(fromIndex,toIndex);
  }
  
  // Returns if this journey contains an edge with the given stations
  public boolean covers(Node start, Node end) 
  {
    if (this.isEmpty()) 
      throw new NoSuchElementException("The path is empty");
    
    Objects.requireNonNull(start);
    Objects.requireNonNull(end);
    
    return this.stream()
      .anyMatch(j -> j.getStart().getId().equals(start.getId()) && j.getEnd().getId().equals(end.getId()));
  }
  
  // Returns if a node is present
  public boolean covers(Node node)
  {
    return this.getNodes().stream()
      .anyMatch(j -> j.getId().equals(node.getId()));
  }
  
  // Returns if a node is present in a given time
  public boolean covers(Node node, LocalDateTime beginInclusive, LocalDateTime endExclusive)
  {
    return this.getNodes().stream()
      .filter(n -> n.getTime().isAfter(beginInclusive) && n.getTime().isBefore(endExclusive))
      .anyMatch(j -> j.getId().equals(node.getId()));
  }
}
