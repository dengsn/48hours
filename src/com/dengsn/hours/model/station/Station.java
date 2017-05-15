package com.dengsn.hours.model.station;

import com.dengsn.hours.model.Identity;
import java.util.Comparator;

public final class Station implements Identity, Comparable<Station>
{
  // Variables
  private String identifier;
  private String name;
  private String code;
  private StationType type;
  private double latitude;
  private double longitude;
  private Station parent;
  
  // Get and set fields
  @Override public String getIdentifier()
  {
    return identifier;
  }
  @Override public Station useIdentifier(String identifier)
  {
    this.identifier = identifier;
    return this;
  }
  public String getName()
  {
    return name;
  }
  public Station useName(String name)
  {
    this.name = name;
    return this;
  }
  public String getCode()
  {
    return this.code;
  }
  public Station useCode(String code)
  {
    this.code = code;
    return this;
  }
  public StationType getType()
  {
    return this.type;
  }
  public Station useType(StationType type)
  {
    this.type = type;
    return this;
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
  public Station getParent()
  {
    return this.parent;
  }
  public Station useParent(Station parent)
  {
    this.parent = parent;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return this.name + " <" + this.identifier + ">" + (this.parent != null ? " -> " + this.parent.toString() : "");
  }

  // Compares this agency to another
  @Override public int compareTo(Station other)
  {
    return Comparator.comparing(Station::getName).compare(this,other);
  }
}
