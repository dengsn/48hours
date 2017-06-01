package com.dengsn.hours.util.deserializers;

import com.dengsn.hours.Feed;
import com.dengsn.hours.FeedDeserializer;
import com.dengsn.hours.util.csv.CSVException;
import com.dengsn.hours.util.csv.CSVRecord;
import java.util.NoSuchElementException;

public class RouteDeserializer extends FeedDeserializer<Route>
{
  // Constructor
  public RouteDeserializer(Feed feed)
  {
    super(feed);
  }
  
  // Deserialize
  @Override public Route deserialize(CSVRecord record) throws CSVException
  {
    try
    {
      return new Route()
        .useId(record.get("route_id").get())
        .useAgency(record.get("agency_id").map(this.feed()::getAgency).orElse(null))
        .useShortName(record.get("route_short_name").get())
        .useLongName(record.get("route_long_name").get())
        .useType(record.getInt("route_type").map(Route.Type::of).get());
    }
    catch (NoSuchElementException ex)
    {
      throw new CSVException("Missing required field",ex);
    }
  }
}
