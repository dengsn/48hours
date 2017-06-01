package com.dengsn.hours.util.deserializers;

import java.util.LinkedList;
import java.util.List;

public class Train
{
  // Variables
  private String id;
  private Route route;
  private String name;
  private final LinkedList<Call> calls = new LinkedList<>();

  // Getters and setters
  public String getId()
  {
    return id;
  }
  public Train useId(String id)
  {
    this.id = id;
    return this;
  }
  public Route getRoute()
  {
    return route;
  }
  public Train useRoute(Route route)
  {
    this.route = route;
    return this;
  }
  public String getName()
  {
    return name;
  }
  public Train useName(String name)
  {
    this.name = name;
    return this;
  }
  public List<Call> getCalls()
  {
    return calls;
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder(this.getRoute().toString())
      .append(" ").append(this.getName());
    
    this.calls.stream()
      .filter(s -> s.getDropoffType() == Call.Type.REGULAR || s.getPickupType() == Call.Type.REGULAR)
      .map(Call::toString)
      .forEach(s -> sb.append("\n  ").append(s));
    
    return sb.toString();
  }
}
