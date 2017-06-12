package com.dengsn.hours.edge;

import com.dengsn.hours.node.Station;
import java.util.LinkedList;
import java.util.List;

public final class Connection extends Edge<Station>
{
  // Variables
  private final Station start;
  private final Station end;
  private final double distance;
  
  // Constructor
  public Connection(Station start, Station end, double distance)
  {
    this.start = start;
    this.end = end;
    this.distance = distance;
  }
  public Connection(Station start, Station end)
  {
    this(start,end,Double.NaN);
  }
  
  // Management
  @Override public Station getStart()
  {
    return this.start;
  }
  @Override public Station getEnd()
  {
    return this.end;
  }
  @Override public double getWeight()
  {
    return this.distance;
  }
  
  // Returns all nodes in this connection
  @Override public List<Station> getNodes()
  {
    List<Station> list = new LinkedList<>();
    list.add(this.getStart());
    list.add(this.getEnd());
    return list;
  }
  
  // Returns if this connection contains the node
  @Override public boolean hasNode(Station node)
  {
    return this.getStart().equals(node) || this.getEnd().equals(node);
  }
  
  // Mirror this connection
  public Connection mirror()
  {
    return new Connection(this.getEnd(),this.getStart(),this.getWeight());
  }
  
  // Mirror this connection with the node as start
  public Connection mirrorTo(Station node)
  {
    if (this.getStart().equals(node))
      return this;
    else if (this.getEnd().equals(node))
      return this.mirror();
    else throw new IllegalArgumentException(this + " does not contain " + node);
  }
}
