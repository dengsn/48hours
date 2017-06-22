package com.dengsn.hours.model.journey;

import com.dengsn.hours.model.journey.Train;
import com.dengsn.hours.model.Station;
import java.time.LocalDateTime;

public class Call
{
  // Variables
  private Train train;
  private LocalDateTime arrival;
  private LocalDateTime departure;
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
  public LocalDateTime getArrival()
  {
    return this.arrival;
  }
  public Call useArrival(LocalDateTime arrival)
  {
    this.arrival = arrival;
    return this;
  }
  public LocalDateTime getDeparture()
  {
    return this.departure;
  }
  public Call useDeparture(LocalDateTime departure)
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
    return "[" + this.getPlatform() + "] " + this.getStation().toString();
  }
}
