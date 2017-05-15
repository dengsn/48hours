package com.dengsn.hours.model.trip;

import com.dengsn.hours.GTFS;
import com.dengsn.hours.GTFSDeserializer;
import com.dengsn.hours.csv.CSVRecord;
import java.util.NoSuchElementException;

public class TripDeserializer extends GTFSDeserializer<Trip>
{
  // Constructor
  public TripDeserializer(GTFS database)
  {
    super(database);
  }
  
  // Deserialize
  @Override public Trip deserialize(CSVRecord record)
  {
    try
    {
      return new Trip()
        .useIdentifier(record.get("trip_id").get())
        .useRoute(record.get("route_id").map(this.database::getRoute).get())
        .useService(record.get("service_id").get())
        .useName(record.get("trip_short_name").orElse(""))
        .useHeadsign(record.get("trip_headsign").orElse(""))
        .useDirection(record.getInt("direction_id").orElse(0));
    }
    catch (NoSuchElementException ex)
    {
      throw new RuntimeException("Missing required field",ex);
    }
  }
}
