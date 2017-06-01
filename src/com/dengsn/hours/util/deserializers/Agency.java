package com.dengsn.hours.util.deserializers;

import java.util.Comparator;

public final class Agency implements Comparable<Agency>
{
  // Variables
  private String id;
  private String name;

  // Getters and setters
  public String getId()
  {
    return id;
  }
  public Agency useId(String id)
  {
    this.id = id;
    return this;
  }
  public String getName()
  {
    return name;
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

  // Compares this agency to another
  @Override public int compareTo(Agency other)
  {
    return Comparator.comparing(Agency::getName).compare(this,other);
  }
}
