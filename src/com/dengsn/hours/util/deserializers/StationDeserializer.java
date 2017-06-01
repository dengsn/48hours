package com.dengsn.hours.util.deserializers;

import com.dengsn.hours.Feed;
import com.dengsn.hours.FeedDeserializer;
import com.dengsn.hours.node.Station;
import com.dengsn.hours.util.csv.CSVException;
import com.dengsn.hours.util.csv.CSVRecord;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StationDeserializer extends FeedDeserializer<Station>
{
  // Constants
  private static final Pattern PLATFORM_PATTERN = Pattern.compile("([a-z]+)\\|(\\d+[abc]?(?:-\\d+[abc]?)?)");
  
  // Constructor
  public StationDeserializer(Feed feed)
  {
    super(feed);
  }
  
  // Deserialize
  @Override public Station deserialize(CSVRecord record) throws CSVException
  {
    try
    {
      Matcher m = PLATFORM_PATTERN.matcher(record.get("stop_id").get());
      if (m.matches())
        return null;
          
      return new Station(record.get("stop_id").get(),record.get("stop_name").get())
        .useLatitude(record.getDouble("stop_lat").get())
        .useLongitude(record.getDouble("stop_lon").get());
    }
    catch (NoSuchElementException ex)
    {
      throw new CSVException("Missing required field",ex);
    }
  }
}
