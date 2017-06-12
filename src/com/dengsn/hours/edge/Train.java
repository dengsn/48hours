package com.dengsn.hours.edge;

import com.dengsn.hours.node.Station;
import java.util.stream.Collectors;

public class Train extends Path<Station,Journey>
{
  // Variables
  private String id;
  private String name;

  // Constructor
  public Train(Journey edge)
  {
    super(edge);
  }
  
  // Getters and setters
  public String getId()
  {
    return this.id;
  }
  public Train useId(String id)
  {
    this.id = id;
    return this;
  }
  public String getName()
  {
    return this.name;
  }
  public Train useName(String name)
  {
    this.name = name;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return this.getName() + ": " + this.stream()
      .map(Journey::toString)
      .collect(Collectors.joining(", "));
  }
}
