package com.dengsn.hours.util.deserializers;

import com.dengsn.hours.Feed;
import com.dengsn.hours.FeedDeserializer;
import com.dengsn.hours.util.csv.CSVException;
import com.dengsn.hours.util.csv.CSVRecord;
import java.util.NoSuchElementException;

public class AgencyDeserializer extends FeedDeserializer<Agency>
{
  // Constructor
  public AgencyDeserializer(Feed feed)
  {
    super(feed);
  }
  
  // Deserialize
  @Override public Agency deserialize(CSVRecord record) throws CSVException
  {
    try
    {
      return new Agency()
        .useId(record.get("agency_id").get())
        .useName(record.get("agency_name").get());
    }
    catch (NoSuchElementException ex)
    {
      throw new CSVException("Missing required field",ex);
    }
  }
}
