package com.dengsn.hours.graph.node;

public interface Node
{
  // Return the identifier associated with this node
  public String getId();
  
  // Return the name associated with this node
  public String getName();
  
  // Returns the latitude of this station
  public double getLatitude();

  // Returns the longitude of this station
  public double getLongitude();
}
