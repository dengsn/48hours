package com.dengsn.hours;

public class AbstractTrain
{
  // Variables
  private String id;
  private Route type;
  private String name;
  private String direction;

  // Getters and setters
  public String getId()
  {
    return this.id;
  }
  public AbstractTrain useId(String id)
  {
    this.id = id;
    return this;
  }
  public Route getType()
  {
    return this.type;
  }
  public AbstractTrain useType(Route type)
  {
    this.type = type;
    return this;
  }
  public String getName()
  {
    return this.name;
  }
  public AbstractTrain useName(String name)
  {
    this.name = name;
    return this;
  }
  public String getDirection()
  {
    return this.direction;
  }
  public AbstractTrain useDirection(String direction)
  {
    this.direction = direction;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return this.type.toString() + (this.getName().equals("0") ? "" : " " + this.getName()) + " naar " + this.getDirection();
  }
}
