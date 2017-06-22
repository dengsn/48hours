package com.dengsn.hours.model.journey;

import com.dengsn.hours.graph.node.Node;
import com.dengsn.hours.model.Station;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;

public class JourneyStation implements Node, Comparable<JourneyStation>
{
  // Constants
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
  
  // Variables
  private final Station station;
  private final LocalDateTime time;
  private final String platform;
  
  // Constructor
  public JourneyStation(Station station, LocalDateTime time, String platform)
  {
    this.station = station;
    this.time = time;
    this.platform = platform;
  }
  
  // Return this station
  public Station getStation()
  {
    return this.station;
  }
  
  // Return the time
  public LocalDateTime getTime()
  {
    return this.time;
  }
  
  // Return the platform
  public String getPlatform()
  {
    return this.platform;
  }

  // Node methods
  @Override public String getId()
  {
    return this.station.getId();
  }
  @Override public String getName()
  {
    return this.station.getName();
  }
  @Override public double getLatitude()
  {
    return this.station.getLatitude();
  }
  @Override public double getLongitude()
  {
    return this.station.getLongitude();
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder(); 
    sb.append(this.getTime().format(FORMATTER)).append(" ").append(this.station.toString());
    if (this.platform != null)
      sb.append(" (spoor ").append(this.platform).append(")");
    return sb.toString();
  }
  
  // Return if this station is equal to another station
  @Override public boolean equals(Object o)
  {
    if (o == null || this.getClass() != o.getClass())
      return false;

    JourneyStation j = (JourneyStation)o;
    if (!Objects.equals(this.station,j.station))
      return false;
    else
      return true;
  }

  // Return the hash code
  @Override public int hashCode()
  {
    int hash = 7;
    hash = 11 * hash + Objects.hashCode(this.station);
    hash = 11 * hash + Objects.hashCode(this.time);
    return hash;
  }
  
  // Compare this journey to another
  @Override public int compareTo(JourneyStation other)
  {
    return Comparator.comparing(JourneyStation::getTime).compare(this,other);
  }
}
