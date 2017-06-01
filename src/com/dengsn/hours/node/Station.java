package com.dengsn.hours.node;

import java.util.Comparator;
import java.util.Objects;

public class Station implements Node, Comparable<Station>
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
    return id;
  }
  @Override public String getName()
  {
    return name;
  }
  public double getLatitude()
  {
    return latitude;
  }
  public Station useLatitude(double latitude)
  {
    this.latitude = latitude;
    return this;
  }
  public double getLongitude()
  {
    return longitude;
  }
  public Station useLongitude(double longitude)
  {
    this.longitude = longitude;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return this.name + " <" + this.id + ">";
  }
  
  // Return if this station is equal to another station
  @Override public boolean equals(Object o)
  {
    if (o == null || this.getClass() != o.getClass())
      return false;

    Station station = (Station) o;
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

  // Compares this agency to another
  @Override public int compareTo(Station other)
  {
    return Comparator.comparing(Station::getName).compare(this,other);
  }
}
