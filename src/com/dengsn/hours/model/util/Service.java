package com.dengsn.hours.model.util;

import com.dengsn.hours.model.Identity;
import java.time.LocalDate;
import java.util.LinkedList;

public class Service extends LinkedList<LocalDate> implements Identity
{
  // Variables
  private String id;
  
  // Getters and setters
  @Override public String getId()
  {
    return this.id;
  }
  @Override public Service useId(String id)
  {
    this.id = id;
    return this;
  }
  
  // Return if a date is valid for a service identifier
  public boolean isValid(LocalDate date)
  {
    return this.stream().anyMatch(d -> d.isEqual(date));
  }
}
