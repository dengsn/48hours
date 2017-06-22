package com.dengsn.hours.model;

public interface Identity
{
  // Return the identifier
  public String getId();
  
  // Set the identifier
  public Identity useId(String id);
}
