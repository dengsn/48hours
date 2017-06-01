package com.dengsn.hours.util.deserializers;

import com.dengsn.hours.Feed;
import com.dengsn.hours.FeedDeserializer;
import com.dengsn.hours.edge.Connection;
import com.dengsn.hours.util.csv.CSVException;
import com.dengsn.hours.util.csv.CSVRecord;
import java.util.NoSuchElementException;

public class ConnectionDeserializer extends FeedDeserializer<Connection>
{
  // Constructor
  public ConnectionDeserializer(Feed feed)
  {
    super(feed);
  }
  
  // Deserialize
  @Override public Connection deserialize(CSVRecord record) throws CSVException
  {
    try
    {
      return new Connection(
        record.get("start").map(this.feed()::getStation).get(),
        record.get("end").map(this.feed()::getStation).get(),
        record.getDouble("distance").get()
      );
    }
    catch (NoSuchElementException ex)
    {
      throw new CSVException("Missing required field");
    }
  }
}
