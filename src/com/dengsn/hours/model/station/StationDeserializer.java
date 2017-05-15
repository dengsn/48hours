package com.dengsn.hours.model.station;

import com.dengsn.hours.GTFS;
import com.dengsn.hours.GTFSDeserializer;
import com.dengsn.hours.csv.CSVRecord;
import java.util.NoSuchElementException;

public class StationDeserializer extends GTFSDeserializer<Station>
{
  // Constructor
  public StationDeserializer(GTFS database)
  {
    super(database);
  }
  
  // Deserialize
  @Override public Station deserialize(CSVRecord record)
  {
    try
    {
      return new Station()
        .useIdentifier(record.get("stop_id").get())
        .useName(record.get("stop_name").get())
        .useCode(record.get("stop_code").orElse(null))
        .useType(record.getInt("location_type").map(StationType::of).orElse(StationType.STOP))
        .useLatitude(record.getDouble("stop_lat").get())
        .useLongitude(record.getDouble("stop_lon").get());
    }
    catch (NoSuchElementException ex)
    {
      throw new RuntimeException("Missing required field",ex);
    }
  }
}
