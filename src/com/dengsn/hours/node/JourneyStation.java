package com.dengsn.hours.node;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class JourneyStation implements Node
{
  // Constants
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.LENIENT);
  
  // Variables
  private Station station;
  private String platform;
  private LocalTime time;
  private boolean halting;
  
  // Getters and setters
  public Station getStation()
  {
    return this.station;
  }
  public JourneyStation useStation(Station station)
  {
    this.station = station;
    return this;
  }
  public String getPlatform()
  {
    return this.platform;
  }
  public JourneyStation usePlatform(String platform)
  {
    this.platform = platform;
    return this;
  }
  public LocalTime getTime()
  {
    return this.time;
  }
  public JourneyStation useTime(LocalTime time)
  {
    this.time = time;
    return this;
  }
  public boolean isHalting()
  {
    return this.halting;
  }
  public JourneyStation useHalting(boolean halting)
  {
    this.halting = halting;
    return this;
  }
  
  // Return the id of this platform
  @Override public String getId()
  {
    return String.format("%s|%s",this.station.getId(),this.getPlatform());
  }

  // Return the name of this platform
  @Override public String getName()
  {
    return String.format("%s, spoor %s",this.station.getName(),this.getPlatform());
  }
  
  // Return the latitude of this platform
  @Override public double getLatitude()
  {
    return this.station.getLatitude();
  }
  
  // Return the longitude of this platform
  @Override public double getLongitude()
  {
    return this.station.getLongitude();
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(this.time.format(TIME_FORMATTER)).append("  ");
    
    if (this.platform != null)
      sb.append("[").append(this.platform).append("] ");
    
    sb.append(this.getStation());
    return sb.toString();
  }
}
