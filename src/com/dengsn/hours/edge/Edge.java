package com.dengsn.hours.edge;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import com.dengsn.hours.node.Node;

public abstract class Edge<N extends Node> implements Comparable<Edge<N>>
{
  // Management
  public abstract N getStart();
  public abstract N getEnd();
  public abstract double getWeight();
  
  // Returns all nodes in this edge
  public abstract List<N> getNodes();
  
  // Returns if this edge contains the node
  public boolean hasNode(N node)
  {
    return this.getNodes().contains(node);
  }
  
  // Returns if this edge contains all the specified nodes
  public boolean hasNodes(Collection<N> nodes)
  {
    return nodes.stream()
      .allMatch(this::hasNode);
  }
  
  // Mirror this edge
  //public abstract Edge<N> mirror();
  
  // Mirror this edge with the specified node as start
  /*public Edge<N> mirrorTo(N node)
  {
    if (this.getStart().equals(node))
      return this;
    else if (this.getEnd().equals(node))
      return this.mirror();
    else throw new IllegalStateException(this + " does not contain " + node);
  }*/
  
  // Returns the oppposite node in this edge
  public N getOpposite(N node)
  {
    if (this.getStart().equals(node))
      return this.getEnd();
    else if (this.getEnd().equals(node))
      return this.getStart();
    else
      throw new IllegalStateException(this + " does not contain " + node);
  }
  
  // Returns the node that connects the edge to this one or null if it doesn't connect
  public N getConnecting(Edge<N> edge)
  {
    if (this.getStart().equals(edge.getStart()) || this.getStart().equals(edge.getEnd()))
      return this.getStart();
    else if (this.getEnd().equals(edge.getStart()) || this.getEnd().equals(edge.getEnd()))
      return this.getEnd();
    else return null;
  }
  
  // Returns if the edge can connect to this one
  public boolean isConnecting(Edge<N> edge)
  {
    return this.getConnecting(edge) != null;
  }
  
  // Compare the distances of two edges
  // Note: this class has a natural ordering that is inconsistent with equals
  @Override public int compareTo(Edge<N> edge)
  {
    return Double.compare(this.getWeight(),edge.getWeight());
  }
  
  // Returns if an object is equal to this one
  @Override public boolean equals(Object o)
  {
    if (o == null || this.getClass() != o.getClass())
      return false;
    
    Edge edge = (Edge)o;
    if (!Objects.equals(this.getStart(),edge.getStart()))
      return false;
    else if (!Objects.equals(this.getEnd(),edge.getEnd()))
      return false;
    else if (this.getWeight() == Double.NaN || edge.getWeight() == Double.NaN || Double.compare(this.getWeight(),edge.getWeight()) != 0)
      return false;
    else
      return true;
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append(this.getStart()).append(" <");
    if (this.getWeight() != Double.NaN)
      sb.append(String.format(Locale.US,"%.2f",this.getWeight()));
    sb.append("> ").append(this.getEnd());
    
    return sb.toString();
  }
}
