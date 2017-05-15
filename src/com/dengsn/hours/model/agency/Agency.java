package com.dengsn.hours.model.agency;

import com.dengsn.hours.model.Identity;
import java.util.Comparator;

public final class Agency implements Identity, Comparable<Agency>
{
  // Variables
  private String identifier;
  private String name;

  // Getters and setters
  @Override public String getIdentifier()
  {
    return identifier;
  }
  @Override public Agency useIdentifier(String identifier)
  {
    this.identifier = identifier;
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
