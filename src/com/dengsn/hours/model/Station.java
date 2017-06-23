package com.dengsn.hours.model;

import com.dengsn.hours.graph.node.Node;
import com.dengsn.hours.model.journey.JourneyStation;
import java.time.LocalDateTime;
import java.util.Objects;

public class Station implements Node, Identity
{
  // Variables
  private String id;
  private String name;
  private double latitude;
  private double longitude;
  
  // Get and set fields
  @Override public String getId()
  {
    return this.id;
  }
  @Override public Station useId(String id)
  {
    this.id = id;
    return this;
  }
  @Override public String getName()
  {
    return this.name;
  }
  public Station useName(String name)
  {
    this.name = name;
    return this;
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
  
  // Create a journey station from this station
  public JourneyStation atTime(LocalDateTime time)
  {
    return new JourneyStation(this,time,null);
  }
}
