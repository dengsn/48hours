package com.dengsn.hours.model.stoptime;

import com.dengsn.hours.model.station.Station;
import com.dengsn.hours.model.trip.Trip;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class StopTime
{
  // Constants
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.LENIENT);
  
  // Variables
  private Trip trip;
  private Station stop;
  private int sequence;
  private LocalTime arrivalTime;
  private LocalTime departureTime;
  private StopTimeType pickupType;
  private StopTimeType dropoffType;

  // Getters and setters
  public Trip getTrip()
  {
    return trip;
  }
  public StopTime useTrip(Trip trip)
  {
    this.trip = trip;
    return this;
  }
  public Station getStop()
  {
    return stop;
  }
  public StopTime useStop(Station stop)
  {
    this.stop = stop;
    return this;
  }
  public int getSequence()
  {
    return sequence;
  }
  public StopTime useSequence(int sequence)
  {
    this.sequence = sequence;
    return this;
  }
  public LocalTime getArrivalTime()
  {
    return arrivalTime;
  }
  public StopTime useArrivalTime(LocalTime arrivalTime)
  {
    this.arrivalTime = arrivalTime;
    return this;
  }
  public LocalTime getDepartureTime()
  {
    return departureTime;
  }
  public StopTime useDepartureTime(LocalTime departureTime)
  {
    this.departureTime = departureTime;
    return this;
  }
  public StopTimeType getPickupType()
  {
    return pickupType;
  }
  public StopTime usePickupType(StopTimeType pickupType)
  {
    this.pickupType = pickupType;
    return this;
  }
  public StopTimeType getDropoffType()
  {
    return dropoffType;
  }
  public StopTime useDropoffType(StopTimeType dropoffType)
  {
    this.dropoffType = dropoffType;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    return this.getArrivalTime().format(FORMATTER) + "A " + this.getDepartureTime().format(FORMATTER) + " " + this.getStop().getName();
  }
}
