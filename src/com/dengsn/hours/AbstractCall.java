package com.dengsn.hours;

import com.dengsn.hours.node.Station;
import java.time.LocalTime;

public class AbstractCall
{
  // Variables
  private AbstractTrain train;
  private LocalTime arrival;
  private LocalTime departure;
  private Station station;
  private String platform;
  private int sequence;

  // Getters and useters
  public AbstractTrain getTrain()
  {
    return this.train;
  }
  public AbstractCall useTrain(AbstractTrain train)
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
}
