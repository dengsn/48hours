package com.dengsn.hours.model.journey;

import com.dengsn.hours.graph.edge.Connection;
import com.dengsn.hours.graph.edge.Path;
import com.dengsn.hours.model.Station;
import java.time.temporal.ChronoUnit;

public class Journey extends Path<JourneyStation,Connection<JourneyStation>>
{
  // Variables
  private final Train train;
  
  // Constructor
  public Journey(Train train)
  {
    this.train = train;
  }
  
  // Return the train used in this journey
  public Train getTrain()
  {
    return this.train;
  }

  // Return the duration of this journey
  public long getDuration()
  {
    return this.getStart().getTime().until(this.getEnd().getTime(),ChronoUnit.MINUTES);
  }
  
  // Convert to connection
  public Path<Station,Connection<Station>> toStationPath()
  {
    Path<Station,Connection<Station>> path = new Path<>();
    this.stream()
      .map(e -> new Connection<>(e.getStart().getStation(),e.getEnd().getStation(),e.getWeight()))
      .forEach(path::add);
    return path;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return super.toString() + ": " + this.train.toString();
  }
}
