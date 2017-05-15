package com.dengsn.hours.model.agency;

import com.dengsn.hours.GTFS;
import com.dengsn.hours.GTFSDeserializer;
import com.dengsn.hours.csv.CSVRecord;
import java.util.NoSuchElementException;

public class AgencyDeserializer extends GTFSDeserializer<Agency>
{
  // Constructor
  public AgencyDeserializer(GTFS database)
  {
    super(database);
  }
  
  // Deserialize
  @Override public Agency deserialize(CSVRecord record)
  {
    try
    {
      return new Agency()
        .useIdentifier(record.get("agency_id").get())
        .useName(record.get("agency_name").get());
    }
    catch (NoSuchElementException ex)
    {
      throw new RuntimeException("Missing required field",ex);
    }
  }
}
