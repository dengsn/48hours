package com.dengsn.hours.model.journey;

import com.dengsn.hours.graph.edge.Path;
import com.dengsn.hours.graph.node.Node;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
  
  // Return the duration of this journey
  public long getDuration()
  {
    return this.getStart().getTime().until(this.getEnd().getTime(),ChronoUnit.MINUTES);
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
  
  // Returns if a node is present
  public boolean covers(Node node)
  {
    return this.getNodes().stream()
      .anyMatch(j -> j.getId().equals(node.getId()));
  }
  
  // Returns if a node is present in a given time
  public boolean covers(Node node, LocalDateTime start, LocalDateTime end)
  {
    return this.getNodes().stream()
      .filter(n -> n.getTime().isAfter(start) && n.getTime().isBefore(end))
      .anyMatch(n -> n.getId().equals(node.getId()));
  }
  
  // Returns if the path contains two mirrored edges
  public boolean hasMirroredEdges()
  {
    for (int i = 0; i < this.getEdges().size(); i ++)
    {
      Journey a = this.getEdges().get(i);
      for (int j = i; j < this.getEdges().size(); j ++)
      {
        Journey b = this.getEdges().get(j);
        if (a.getStart().getStation().equals(b.getEnd().getStation()) && a.getEnd().getStation().equals(b.getStart().getStation()))
          return true;
      }
    }
    return false;
  }
}
