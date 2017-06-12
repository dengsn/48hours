package com.dengsn.hours;

public final class Route
{  
  // Variables
  private String id;
  private Agency agency;
  private String name;
  private RouteType type;

  // Getters and setters
  public String getId()
  {
    return this.id;
  }
  public Route useId(String id)
  {
    this.id = id;
    return this;
  }
  public Agency getAgency()
  {
    return this.agency;
  }
  public Route useAgency(Agency agency)
  {
    this.agency = agency;
    return this;
  }
  public String getName()
  {
    return this.name;
  }
  public Route useName(String name)
  {
    this.name = name;
    return this;
  }
  public RouteType getType()
  {
    return this.type;
  }
  public Route useType(RouteType type)
  {
    this.type = type;
    return this;
  }

  // Convert to string
  @Override public String toString()
  {
    return this.agency.getName() + " " + this.getName();
  }
}
