package com.dengsn.hours.node;

import com.dengsn.hours.util.deserializers.Call;
import java.time.LocalTime;

public class Stop implements Node
{
  // Variables
  private Station station;
  private LocalTime arrivalTime;
  private LocalTime departureTime;
  private String platform;
  private Call.Type pickupType;
  private Call.Type dropoffType;

  @Override public String getId()
  {
    return this.station.getId();
  }

  @Override public String getName()
  {
    return this.station.getName();
  }
}
