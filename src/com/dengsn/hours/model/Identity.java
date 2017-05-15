package com.dengsn.hours.model;

public interface Identity
{
  // Return the identifier
  public String getIdentifier();
  
  // Use the specified identifier
  public Identity useIdentifier(String identifier);
}
