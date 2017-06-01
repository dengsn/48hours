package com.dengsn.hours.util.deserializers;

import java.util.Comparator;

public final class Route implements Comparable<Route>
{
  // Route type
  public static enum Type
  {
   // Values
    TRAM, SUBWAY, RAIL, BUS, FERRY, CABLE_CAR, GONDOLA, FUNICULAR, UNKNOWN;
  
    // Convert from integer
    public static Type of(int type)
    {
      try
      {
        return Type.values()[type];
      }
      catch (ArrayIndexOutOfBoundsException ex)
      {
        return Type.UNKNOWN;
      }
    }
  }
  
  // Variables
  private String id;
  private Agency agency;
  private String shortName;
  private String longName;
  private Type type;

  // Getters and setters
  public String getId()
  {
    return id;
  }
  public Route useId(String id)
  {
    this.id = id;
    return this;
  }
  public Agency getAgency()
  {
    return agency;
  }
  public Route useAgency(Agency agency)
  {
    this.agency = agency;
    return this;
  }
  public String getShortName()
  {
    return shortName;
  }
  public Route useShortName(String shortName)
  {
    this.shortName = shortName;
    return this;
  }
  public String getLongName()
  {
    return longName;
  }
  public Route useLongName(String longName)
  {
    this.longName = longName;
    return this;
  }
  public Type getType()
  {
    return type;
  }
  public Route useType(Type type)
  {
    this.type = type;
    return this;
  }

  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    if (this.agency != null)
      sb.append(this.agency.getName()).append(" ");
    sb.append(this.getLongName());
    return sb.toString();
  }

  // Compares this agency to another
  @Override public int compareTo(Route other)
  {
    return Comparator.comparing(Route::getLongName).compare(this,other);
  }
}
