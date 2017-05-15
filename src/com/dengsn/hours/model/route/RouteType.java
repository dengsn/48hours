package com.dengsn.hours.model.route;

public enum RouteType
{
  // Values
  TRAM, SUBWAY, RAIL, BUS, FERRY, CABLE_CAR, GONDOLA, FUNICULAR, UNKNOWN;
  
  // Convert from integer
  public static RouteType of(int routeType)
  {
    try
    {
      return RouteType.values()[routeType];
    }
    catch (ArrayIndexOutOfBoundsException ex)
    {
      return null;
    }
  }
}
