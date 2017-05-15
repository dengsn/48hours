package com.dengsn.hours.model.route;

import com.dengsn.hours.model.Identity;
import com.dengsn.hours.model.agency.Agency;
import java.util.Comparator;

public final class Route implements Identity, Comparable<Route>
{
  // Variables
  private String identifier;
  private Agency agency;
  private String shortName;
  private String longName;
  private RouteType type;

  // Getters and setters
  @Override public String getIdentifier()
  {
    return identifier;
  }
  @Override public Route useIdentifier(String identifier)
  {
    this.identifier = identifier;
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
  public RouteType getType()
  {
    return type;
  }
  public Route useType(RouteType type)
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
