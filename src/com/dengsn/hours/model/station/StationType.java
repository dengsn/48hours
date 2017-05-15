package com.dengsn.hours.model.station;

public enum StationType
{
  // Values
  STOP, STATION;
  
  // Convert from integer
  public static StationType of(int stopType)
  {
    try
    {
      return StationType.values()[stopType];
    }
    catch (ArrayIndexOutOfBoundsException ex)
    {
      return null;
    }
  }
}
