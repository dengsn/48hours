package com.dengsn.hours.edge;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.dengsn.hours.node.Node;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Path<N extends Node, E extends Edge<N>> extends Edge<N> implements Iterable<E>
{
  // Variables
  private final LinkedList<E> edges = new LinkedList<>();
  
  // Constructor
  public Path() 
  {
    // Start with an empty path
  }
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
    if (this.isEmpty()) 
      throw new NoSuchElementException("The path is empty");
    
    return this.edges.getFirst().getStart();
  }
  @Override public N getEnd()
  {
    if (this.isEmpty()) 
      throw new NoSuchElementException("The path is empty");
    
    return this.edges.getLast().getEnd();
  }
  @Override public double getWeight()
  {
    if (this.isEmpty()) 
      throw new NoSuchElementException("The path is empty");
    
    return this.edges.stream()
      .collect(Collectors.summingDouble(Edge::getWeight));
  }
  
  // Returns all the nodes in this path
  @Override public List<N> getNodes()
  {
    if (this.isEmpty()) 
      throw new NoSuchElementException("The path is empty");
    
    List<N> list = new LinkedList<>();
    list.add(this.getStart());
    for (E connection : this)
      list.add(connection.getEnd());
    return list;
  }
  
  // Returns if this path contains the specified node
  @Override public boolean hasNode(N node) 
  {
    if (this.isEmpty()) 
      throw new NoSuchElementException("The path is empty");
    
    return this.edges.stream()
      .anyMatch(c -> c.hasNode(node));
  }
  
  // Returns all the connections in this path
  public List<E> getEdges()
  {
    return this.edges;
  }
  
  // Returns the count of connections in this path
  public int size()
  {
    return this.edges.size();
  }
  
  // Returns if this path is empty
  public boolean isEmpty()
  {
    return this.edges.isEmpty();
  }
  
  // Add a connection to this path
  public Path<N,E> add(E edge)
  {
    // Check if the given connection is null
    Objects.requireNonNull(edge);
    
    // If the path is empty, then just add
    if (this.isEmpty())
      this.edges.add(edge);
    
    // Check if this connection connects
    else if (!this.isConnecting(edge))
      throw new IllegalStateException(edge + " does not connect to " + this);
    
    // Add the connection
    else
    {
      N node = this.getConnecting(edge);
      if (this.getStart().equals(node))
        this.edges.addFirst(edge);
      else if (this.getEnd().equals(node))
        this.edges.addLast(edge);
    }
      
    // Return the path
    return this;
  }
  
  // Add an adjacent path to this path
  public Path<N,E> combine(Path<N,E> path)
  {
    // Check if the given path is null
    Objects.requireNonNull(path);
    
    // If the path is empty, then just add
    if (this.isEmpty())
      this.edges.addAll(path.edges);
    
    // Check if this connection connects
    else if (!this.isConnecting(path))
      throw new IllegalStateException(path + " does not connect to " + this);
    
    // Add the connection
    else
    {
      N node = this.getConnecting(path);
      if (this.getStart().equals(node))
        path.forEach(this::add);
      else if (this.getEnd().equals(node))
        path.forEach(this::add);
    }
      
    // Return the path
    return this;
  }
  
  // Returns the iterator
  @Override public Iterator<E> iterator()
  {
    return this.edges.iterator();
  }
  
  // Returns to stream
  public Stream<E> stream()
  {
    return this.edges.stream();
  }
  
  // Get the first part of a pat until the given node
  public Path<N,E> cut(int fromIndex, int toIndex)
  {
    if (fromIndex < 0)
      throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
    if (toIndex > this.size())
      throw new IndexOutOfBoundsException("toIndex = " + toIndex);
    if (fromIndex >= toIndex)
      throw new IllegalArgumentException("fromIndex is higher than or equal to toIndex");
    
    Path<N,E> path = new Path<>(this.edges.get(fromIndex));
    for (int i = fromIndex + 1; i < toIndex; i ++)
      path.add(this.edges.get(i));
    return path;
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
    else 
      return true;
  }
  
  // Returns the hash code for this path
  @Override public int hashCode()
  {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.edges);
    return hash;
  }
}
