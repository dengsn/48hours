package com.dengsn.hours;

public final class Agency
{
  // Variables
  private String id;
  private String name;

  // Getters and setters
  public String getId()
  {
    return this.id;
  }
  public Agency useId(String id)
  {
    this.id = id;
    return this;
  }
  public String getName()
  {
    return this.name;
  }
  public Agency useName(String name)
  {
    this.name = name;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return this.getName();
  }
}
