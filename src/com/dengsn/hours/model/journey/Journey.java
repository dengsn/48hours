package com.dengsn.hours.model.journey;

import com.dengsn.hours.graph.edge.Edge;
import com.dengsn.hours.graph.edge.Path;
import java.time.temporal.ChronoUnit;

public class Journey extends Path<JourneyStation,Edge<JourneyStation>>
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
  
  // Convert to string
  @Override public String toString()
  {
    return super.toString() + ": " + this.train.toString();
  }
}
