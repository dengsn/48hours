package com.dengsn.hours.model.stoptime;

public enum StopTimeType
{
  // Values
  REGULAR, NONE, PHONE, DRIVER;
  
  // Convert from integer
  public static StopTimeType of(int stopTimeType)
  {
    try
    {
      return StopTimeType.values()[stopTimeType];
    }
    catch (ArrayIndexOutOfBoundsException ex)
    {
      return null;
    }
  }
}
