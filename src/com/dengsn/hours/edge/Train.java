package com.dengsn.hours.edge;

import com.dengsn.hours.node.Stop;
import com.dengsn.hours.util.deserializers.Route;

public class Train extends Path<Stop,Edge<Stop>>
{
  // Variables
  private String id;
  private Route route;
  private String name;  

  // Constructor
  public Train(Edge<Stop> edge)
  {
    super(edge);
  }
}
