package com.dengsn.hours.model.stoptime;

import com.dengsn.hours.GTFS;
import com.dengsn.hours.GTFSDeserializer;
import com.dengsn.hours.csv.CSVRecord;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.NoSuchElementException;

public class StopTimeDeserializer extends GTFSDeserializer<StopTime>
{
  // Constants
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("H:mm:ss").withResolverStyle(ResolverStyle.LENIENT);
  
  // Constructor
  public StopTimeDeserializer(GTFS database)
  {
    super(database);
  }
  
  // Deserialize
  @Override public StopTime deserialize(CSVRecord record)
  {
    try
    {
      return new StopTime()
        .useTrip(record.get("trip_id").map(this.database::getTrip).get())
        .useStop(record.get("stop_id").map(this.database::getStation).get())
        .useSequence(record.getInt("stop_sequence").get())
        .useArrivalTime(record.get("arrival_time").map(date -> LocalTime.parse(date,FORMATTER)).get())
        .useDepartureTime(record.get("departure_time").map(date -> LocalTime.parse(date,FORMATTER)).get());
        //.usePickupType(record.get("pickup_type").map(StopTimeType::of).orElse(StopTimeType.REGULAR));
        //.useDropoffType(record.get("drop_off_type").map(StopTimeType::of).orElse(StopTimeType.REGULAR));
    }
    catch (NoSuchElementException ex)
    {
      throw new RuntimeException("Missing required field",ex);
    }
  }
}
