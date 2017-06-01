package com.dengsn.hours.util.deserializers;

import com.dengsn.hours.Feed;
import com.dengsn.hours.FeedDeserializer;
import com.dengsn.hours.util.csv.CSVException;
import com.dengsn.hours.util.csv.CSVRecord;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class TrainDeserializer extends FeedDeserializer<Train>
{
  // Constants
  private static final Pattern PLATFORM_PATTERN = Pattern.compile("([a-z]+)\\|(\\d+[abc]?(?:-\\d+[abc]?)?)");
  
  // Constructor
  public TrainDeserializer(Feed feed)
  {
    super(feed);
  }
  
  // Deserialize
  @Override public Train deserialize(CSVRecord record) throws CSVException
  {
    try
    {
      return new Train()
        .useId(record.get("trip_id").get())
        .useRoute(record.get("route_id").map(this.feed()::getRoute).get())
        .useName(record.get("trip_short_name").orElse(""));
    }
    catch (NoSuchElementException ex)
    {
      throw new CSVException("Missing required field",ex);
    }
  }
}
