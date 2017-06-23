package com.dengsn.hours.model.journey;

import com.dengsn.hours.model.Station;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;

public class Call
{
  // Variables
  private Train train;
  private TemporalAccessor arrival;
  private TemporalAccessor departure;
  private Station station;
  private String platform;
  private int sequence;
  private boolean halting;

  // Getters and useters
  public Train getTrain()
  {
    return this.train;
  }
  public Call useTrain(Train train)
  {
    this.train = train;
    return this;
  }
  public TemporalAccessor getArrival()
  {
    return this.arrival;
  }
  public Call useArrival(TemporalAccessor arrival)
  {
    this.arrival = arrival;
    return this;
  }
  public TemporalAccessor getDeparture()
  {
    return this.departure;
  }
  public Call useDeparture(TemporalAccessor departure)
  {
    this.departure = departure;
    return this;
  }
  public Station getStation()
  {
    return this.station;
  }
  public Call useStation(Station station)
  {
    this.station = station;
    return this;
  }
  public String getPlatform()
  {
    return this.platform;
  }
  public Call usePlatform(String platform)
  {
    this.platform = platform;
    return this;
  }
  public int getSequence()
  {
    return this.sequence;
  }
  public Call useSequence(int sequence)
  {
    this.sequence = sequence;
    return this;
  }
  public boolean isHalting()
  {
    return this.halting;
  }
  public Call useHalting(boolean halting)
  {
    this.halting = halting;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder(); 
    sb.append(this.getDeparture()).append(" ").append(this.station.toString());
    if (this.platform != null && !this.platform.equals("0"))
      sb.append(" (spoor ").append(this.platform).append(")");
    return sb.toString();
  }
}
