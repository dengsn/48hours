package com.dengsn.hours.model.route;

import com.dengsn.hours.GTFS;
import com.dengsn.hours.GTFSDeserializer;
import com.dengsn.hours.csv.CSVRecord;
import java.util.NoSuchElementException;

public class RouteDeserializer extends GTFSDeserializer<Route>
{
  // Constructor
  public RouteDeserializer(GTFS database)
  {
    super(database);
  }
  
  // Deserialize
  @Override public Route deserialize(CSVRecord record)
  {
    try
    {
      return new Route()
        .useIdentifier(record.get("route_id").get())
        .useAgency(record.get("agency_id").map(this.database::getAgency).orElse(null))
        .useShortName(record.get("route_short_name").get())
        .useLongName(record.get("route_long_name").get())
        .useType(record.getInt("route_type").map(RouteType::of).orElse(RouteType.UNKNOWN));
    }
    catch (NoSuchElementException ex)
    {
      throw new RuntimeException("Missing required field",ex);
    }
  }
}
