package com.dengsn.hours.edge;

import com.dengsn.hours.node.Station;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Locale;

public class Journey extends Edge<Station>
{
  // Variables
  private final Edge<Station> connection;
  private final LocalTime departure;
  private final LocalTime arrival;
  
  // Constructor
  public Journey(Edge<Station> connection, LocalTime departure, LocalTime arrival)
  {
    this.connection = connection;
    this.departure = departure;
    this.arrival = arrival;
  }
  
  // Management
  @Override public Station getStart()
  {
    return this.connection.getStart();
  }
  @Override public Station getEnd()
  {
    return this.connection.getEnd();
  }
  public LocalTime getDeparture()
  {
    return this.departure;
  }
  public LocalTime getArrival()
  {
    return this.arrival;
  }
  
  // Return the duration of this travel
  public long getDuration()
  {
    return this.getDeparture().until(this.getArrival(),ChronoUnit.MINUTES);
  }
  
  // Return the duration from a given moment
  public long getDuration(Temporal since)
  {
    return since.until(this.getDeparture(),ChronoUnit.MINUTES) + this.getDuration();
  }
  
  // Return the relative duration at this travel
  public double getRelative()
  {
    return this.getDuration() / this.connection.getWeight();
  }
  
  // Return the relative duration from a given moment
  public double getRelative(Temporal since)
  {
    return this.getDuration(since) / this.connection.getWeight();
  }

  // Returns the average speed on this connection
  @Override public double getWeight()
  {
    return this.connection.getWeight() / this.getDuration() * 60.0;
  }

  // Returns all nodes in this connection
  @Override public List<Station> getNodes()
  {
    return this.connection.getNodes();
  }
  
  // Returns if this connection contains the node
  @Override public boolean hasNode(Station node)
  {
    return this.connection.hasNode(node);
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append(this.getStart()).append(" ").append(this.getDeparture()).append(" <");
    if (this.getWeight() != Double.NaN)
      sb.append(String.format(Locale.US,"%.2f",this.getWeight()));
    sb.append("> ").append(this.getEnd()).append(" ").append(this.getArrival());
    
    return sb.toString();
  }
}
