package com.dengsn.hours.model.journey;

import com.dengsn.hours.model.util.Agency;
import com.dengsn.hours.model.Identity;

public final class TrainType implements Identity
{  
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
  private String name;
  private Type type;

  // Getters and setters
  @Override public String getId()
  {
    return this.id;
  }
  @Override public TrainType useId(String id)
  {
    this.id = id;
    return this;
  }
  public Agency getAgency()
  {
    return this.agency;
  }
  public TrainType useAgency(Agency agency)
  {
    this.agency = agency;
    return this;
  }
  public String getName()
  {
    return this.name;
  }
  public TrainType useName(String name)
  {
    this.name = name;
    return this;
  }
  public Type getType()
  {
    return this.type;
  }
  public TrainType useType(Type type)
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
