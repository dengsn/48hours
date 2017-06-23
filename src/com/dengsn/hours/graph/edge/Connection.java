package com.dengsn.hours.graph.edge;

import com.dengsn.hours.graph.node.Node;
import com.dengsn.hours.model.Station;
import java.util.LinkedList;
import java.util.List;

public class Connection<N extends Node> extends Edge<N>
{
  // Variables
  private final N start;
  private final N end;
  private final double distance;
  
  // Constructor
  public Connection(N start, N end, double distance)
  {
    this.start = start;
    this.end = end;
    this.distance = distance;
  }
  public Connection(N start, N end)
  {
    this(start,end,Double.NaN);
  }
  
  // Management
  @Override public N getStart()
  {
    return this.start;
  }
  @Override public N getEnd()
  {
    return this.end;
  }
  @Override public double getWeight()
  {
    return this.distance;
  }
  
  // Returns all nodes in this edge
  @Override public List<N> getNodes()
  {
    List<N> list = new LinkedList<>();
    list.add(this.getStart());
    list.add(this.getEnd());
    return list;
  }
  
  // Returns if this connection contains the node
  @Override public boolean hasNode(N node)
  {
    return this.getStart().equals(node) || this.getEnd().equals(node);
  }
  
  // Mirror this connection
  @Override public Connection<N> mirror()
  {
    return new Connection<>(this.getEnd(),this.getStart(),this.getWeight());
  }
}
