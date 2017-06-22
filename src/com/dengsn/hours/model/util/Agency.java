package com.dengsn.hours.model.util;

import com.dengsn.hours.model.Identity;

public final class Agency implements Identity
{
  // Variables
  private String id;
  private String name;

  // Getters and setters
  @Override public String getId()
  {
    return this.id;
  }
  @Override public Agency useId(String id)
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
