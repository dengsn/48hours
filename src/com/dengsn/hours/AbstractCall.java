package com.dengsn.hours;

import com.dengsn.hours.edge.Train;
import com.dengsn.hours.node.Station;
import java.time.LocalTime;

public class AbstractCall
{
  // Variables
  private Train train;
  private LocalTime arrival;
  private LocalTime departure;
  private Station station;
  private String platform;
  private int sequence;
  private boolean halting;

  // Getters and useters
  public Train getTrain()
  {
    return this.train;
  }
  public AbstractCall useTrain(Train train)
  {
    this.train = train;
    return this;
  }
  public LocalTime getArrival()
  {
    return this.arrival;
  }
  public AbstractCall useArrival(LocalTime arrival)
  {
    this.arrival = arrival;
    return this;
  }
  public LocalTime getDeparture()
  {
    return this.departure;
  }
  public AbstractCall useDeparture(LocalTime departure)
  {
    this.departure = departure;
    return this;
  }
  public Station getStation()
  {
    return this.station;
  }
  public AbstractCall useStation(Station station)
  {
    this.station = station;
    return this;
  }
  public String getPlatform()
  {
    return this.platform;
  }
  public AbstractCall usePlatform(String platform)
  {
    this.platform = platform;
    return this;
  }
  public int getSequence()
  {
    return this.sequence;
  }
  public AbstractCall useSequence(int sequence)
  {
    this.sequence = sequence;
    return this;
  }
  public boolean isHalting()
  {
    return this.halting;
  }
  public AbstractCall useHalting(boolean halting)
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
