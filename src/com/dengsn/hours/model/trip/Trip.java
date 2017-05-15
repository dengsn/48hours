package com.dengsn.hours.model.trip;

import com.dengsn.hours.model.Identity;
import com.dengsn.hours.model.route.Route;
import com.dengsn.hours.model.stoptime.StopTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Trip implements Identity, Comparable<Trip>
{
  // Variables
  private String identifier;
  private Route route;
  private String service;
  private String name;
  private String headsign;
  private int direction;
  private List<StopTime> stopTimes = new LinkedList<>();

  // Getters and setters
  @Override public String getIdentifier()
  {
    return identifier;
  }
  @Override public Trip useIdentifier(String identifier)
  {
    this.identifier = identifier;
    return this;
  }
  public Route getRoute()
  {
    return route;
  }
  public Trip useRoute(Route route)
  {
    this.route = route;
    return this;
  }
  public String getService()
  {
    return service;
  }
  public Trip useService(String service)
  {
    this.service = service;
    return this;
  }
  public String getName()
  {
    return name;
  }
  public Trip useName(String name)
  {
    this.name = name;
    return this;
  }
  public String getHeadsign()
  {
    return headsign;
  }
  public Trip useHeadsign(String headsign)
  {
    this.headsign = headsign;
    return this;
  }
  public int getDirection()
  {
    return direction;
  }
  public Trip useDirection(int direction)
  {
    this.direction = direction;
    return this;
  }
  public List<StopTime> getStopTimes()
  {
    return stopTimes;
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder(this.getRoute().toString())
      .append(" ").append(this.getName())
      .append(" naar ").append(this.getHeadsign());
    
    this.stopTimes.stream()
    //  .filter(s -> s.getDropoffType() == StopTimeType.REGULAR)
      .map(StopTime::toString)
      .forEach(s -> sb.append("\n  ").append(s));
    
    return sb.toString();
  }

  // Compares this agency to another
  @Override public int compareTo(Trip other)
  {
    return Comparator.comparing(Trip::getRoute).thenComparing(Trip::getName).compare(this,other);
  }
}
