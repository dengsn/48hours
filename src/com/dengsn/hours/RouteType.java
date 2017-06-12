package com.dengsn.hours;

public enum RouteType
{
  // Values
  TRAM, SUBWAY, RAIL, BUS, FERRY, CABLE_CAR, GONDOLA, FUNICULAR, UNKNOWN;
  
  // Convert from integer
  public static RouteType of(int type)
  {
    try
    {
      return RouteType.values()[type];
    }
    catch (ArrayIndexOutOfBoundsException ex)
    {
      return RouteType.UNKNOWN;
    }
  }
}
