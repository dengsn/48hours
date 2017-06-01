package com.dengsn.hours.edge;

import com.dengsn.hours.node.Node;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Path<N extends Node, E extends Edge<N>> extends Edge<N> implements Iterable<E>
{
  // Variables
  private final LinkedList<E> edges = new LinkedList<>();
  
  // Constructor
  public Path(E edge)
  {
    this.edges.add(edge);
  }
  public Path(Path<N,E> source)
  {
    this.edges.addAll(source.edges);
  }
  
  // Management
  @Override public N getStart()
  {
    return this.edges.getFirst().getStart();
  }
  @Override public N getEnd()
  {
    return this.edges.getLast().getEnd();
  }
  @Override public double getDistance()
  {
    return this.edges.stream()
      .collect(Collectors.summingDouble(Edge::getDistance));
  }
  
  // Returns all the nodes in this path
  @Override public List<N> getNodes()
  {
    List<N> list = new LinkedList<>();
    list.add(this.getStart());
    for (E connection : this)
      list.add(connection.getEnd());
    return list;
  }
  
  // Returns if this path contains the specified node
  @Override public boolean hasNode(N node) 
  {
    return this.edges.stream()
      .anyMatch(c -> c.hasNode(node));
  }
  
  // Mirror this path
  @Override public Path<N,E> mirror()
  {
    Path<N,E> path = new Path(this.edges.getLast().mirror());
    for (int i = this.edges.size() - 2; i >= 0; i --)
      path.add((E)this.edges.get(i).mirror());    
    return path;
  }
  
  // Mirror this path with the node as start
  @Override public Path<N,E> mirrorTo(Node node)
  {
    if (this.getStart().equals(node))
      return this;
    else if (this.getEnd().equals(node))
      return this.mirror();
    else throw new IllegalStateException(this + " does not contain " + node);
  }
  
  // Returns all the connections in this path
  public List<E> getEdges()
  {
    return this.edges;
  }
  
  // Returns the count of connections in this path
  public int getSize()
  {
    return this.edges.size();
  }
  
  // Add a connection to this path
  public Path<N,E> add(E edge)
  {
    // Check if the given connection is null
    if (edge == null)
      return this;
    
    // Check if this connection connects
    if (!this.isConnecting(edge))
      throw new IllegalStateException(edge + " does not connect to " + this);
    
    // Add the connection
    N node = this.getConnecting(edge);
    if (this.getStart().equals(node))
      this.edges.addFirst((E)edge.mirrorTo(this.getOpposite(this.getStart())));
    else if (this.getEnd().equals(node))
      this.edges.addLast((E)edge.mirrorTo(this.getEnd()));
      
    // Return the path
    return this;
  }
  
  // Add an adjacent path to this path
  public Path<N,E> combine(Path<N,E> path)
  {
    // Check if the given path is null
    if (path == null)
      return this;
    
    // Check if this connection connects
    if (!this.isConnecting(path))
      throw new IllegalStateException(path + " does not connect to " + this);
    
    // Add the connection
    N node = this.getConnecting(path);
    if (this.getStart().equals(node))
      (path.mirrorTo(this.getOpposite(this.getStart()))).forEach(this::add);
    else if (this.getEnd().equals(node))
      (path.mirrorTo(this.getEnd())).forEach(this::add);
      
    // Return the path
    return this;
  }
  
  // Returns the iterator
  @Override public Iterator<E> iterator()
  {
    return this.edges.iterator();
  }
  
  // Get the first part of a pat until the given node
  public Path<N,E> cut(int fromIndex, int toIndex)
  {
    if (fromIndex < 0)
      throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
    if (toIndex > this.getSize())
      throw new IndexOutOfBoundsException("toIndex = " + toIndex);
    if (fromIndex >= toIndex)
      throw new IllegalArgumentException("fromIndex is higher than or equal to toIndex");
    
    Path<N,E> path = new Path<>(this.edges.get(fromIndex));
    for (int i = fromIndex + 1; i < toIndex; i ++)
      path.add(this.edges.get(i));
    return path;
  }
  
  // Returns a string containing a chain of the connections
  public String toChainString()
  {
    StringBuilder sb = new StringBuilder(this.getStart().toString());
    for (E t : this)
      sb.append(" < ").append(t.getDistance()).append(" > ").append(t.getEnd());
    sb.append(" (total ").append(String.format("%.2f",this.getDistance())).append(")");
    return sb.toString();
  }
  
  // Returns a list of paths referencing intercity nodes
  /*public List<Path<N,E>> filtered(Node.Type type)
  {
    LinkedList<Path<N,E>> list = new LinkedList<>();
    for (E connection : this)
    {
      if (list.isEmpty() || list.getLast().getEnd().getType().compareTo(type) <= 0)
        list.add(new Path(connection));
      else
        list.getLast().add(connection);
    }
    return list;
  }*/
  
  // Returns if the object is equal to this one
  @Override public boolean equals(Object o)
  {
    if (o == null || this.getClass() != o.getClass())
      return false;
    
    Path path = (Path)o;
    if (!Objects.equals(this.edges,path.edges))
      return false;
    else return true;
  }
  @Override public int hashCode()
  {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.edges);
    return hash;
  }
  
  // Intercities
  /*public List<Path> toIntercities()
  {
    Stack<Path> stack = new Stack<>();
    for (DirectConnection c : this)
    {
      if (stack.isEmpty() || stack.peek().getEnd().isIntercityStation())
        stack.add(new Path());
      stack.peek().add(c);
    }    
    while (!stack.isEmpty() && stack.peek().isEmpty())
      stack.pop();
    return stack;
  }
  
  // Cut
  public Path cut(Station source, Station target)
  {
    Queue<Connection> q = new LinkedList<>(this);
    Path p = new Path();
    boolean inside = false;
    while (!q.isEmpty())
    {
      Connection c = q.poll();
      System.out.println("    " + c);
      if (c.getStart().equals(source))
        inside = true;
      
      if (inside)
        p.add(c);
      
      if (c.getEnd().equals(target))
        break;
    }
    return p;
  }
  public Path cut(ConnectionUtils c)
  {
    return this.cut(c.getStart(),c.getEnd());
  }*/
}
