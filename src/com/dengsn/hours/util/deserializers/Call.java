package com.dengsn.hours.util.deserializers;

import com.dengsn.hours.node.Station;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Comparator;

public class Call implements Comparable<Call>
{
  // Pickup and dropoff type enum
  public static enum Type
  {
    // Values
    REGULAR, NONE, PHONE, DRIVER;
  
    // Convert from integer
    public static Type of(int type)
    {
      try
      {
        return Type.values()[type];
      }
      catch (ArrayIndexOutOfBoundsException ex)
      {
        return Type.REGULAR;
      }
    }
  }
  
  // Constants
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.LENIENT);
  
  // Variables
  private Train train;
  private Station station;
  private int sequence;
  private LocalTime arrivalTime;
  private LocalTime departureTime;
  private String platform;
  private Type pickupType;
  private Type dropoffType;

  // Getters and setters
  public Train getTrain()
  {
    return train;
  }
  public Call useTrain(Train train)
  {
    this.train = train;
    return this;
  }
  public Station getStation()
  {
    return station;
  }
  public Call useStation(Station stop)
  {
    this.station = stop;
    return this;
  }
  public int getSequence()
  {
    return sequence;
  }
  public Call useSequence(int sequence)
  {
    this.sequence = sequence;
    return this;
  }
  public LocalTime getArrivalTime()
  {
    return arrivalTime;
  }
  public Call useArrivalTime(LocalTime arrivalTime)
  {
    this.arrivalTime = arrivalTime;
    return this;
  }
  public LocalTime getDepartureTime()
  {
    return departureTime;
  }
  public Call useDepartureTime(LocalTime departureTime)
  {
    this.departureTime = departureTime;
    return this;
  }
  public String getPlatform()
  {
    return platform;
  }
  public Call usePlatform(String platform)
  {
    this.platform = platform;
    return this;
  }
  public Type getPickupType()
  {
    return pickupType;
  }
  public Call usePickupType(Type pickupType)
  {
    this.pickupType = pickupType;
    return this;
  }
  public Type getDropoffType()
  {
    return dropoffType;
  }
  public Call useDropoffType(Type dropoffType)
  {
    this.dropoffType = dropoffType;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    
    if (!this.arrivalTime.equals(this.departureTime))
      sb.append(this.arrivalTime.format(FORMATTER)).append("A  ");
    else
      sb.append(this.departureTime.format(FORMATTER)).append("   ");
    
    if (this.platform != null)
      sb.append("[").append(this.platform).append("] ");
    
    sb.append(this.getStation());
    return sb.toString();
  }
  
  // Compare this train stop to another
  @Override public int compareTo(Call other)
  {
    return Comparator
      .comparing(Call::getArrivalTime)
      .thenComparing(Call::getSequence)
      .compare(this,other);
  }
}
