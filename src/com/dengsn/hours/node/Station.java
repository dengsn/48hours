package com.dengsn.hours.node;

import java.util.Objects;

public class Station implements Node
{
  // Variables
  private final String id;
  private final String name;
  private double latitude;
  private double longitude;
  
  // Constructor
  public Station(String id, String name)
  {
    this.id = id;
    this.name = name;
  }
  
  // Get and set fields
  @Override public String getId()
  {
    return this.id;
  }
  @Override public String getName()
  {
    return this.name;
  }
  @Override public double getLatitude()
  {
    return this.latitude;
  }
  public Station useLatitude(double latitude)
  {
    this.latitude = latitude;
    return this;
  }
  @Override public double getLongitude()
  {
    return this.longitude;
  }
  public Station useLongitude(double longitude)
  {
    this.longitude = longitude;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return this.getName();
  }
  
  // Return if this station is equal to another station
  @Override public boolean equals(Object o)
  {
    if (o == null || this.getClass() != o.getClass())
      return false;

    Station station = (Station)o;
    if (!Objects.equals(this.id,station.id))
      return false;
    else if (!Objects.equals(this.name,station.name))
      return false;
    else if (Double.compare(this.latitude,station.latitude) != 0)
      return false;
    else if (Double.compare(this.longitude,station.longitude) != 0)
      return false;
    else
      return true;
  }

  // Return the has code for this station
  @Override public int hashCode()
  {
    int hash = 3;
    hash = 19 * hash + Objects.hashCode(this.id);
    hash = 19 * hash + Objects.hashCode(this.name);
    hash = 19 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
    hash = 19 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
    return hash;
  }
}
