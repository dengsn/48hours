package com.dengsn.hours.util.deserializers;

import com.dengsn.hours.Feed;
import com.dengsn.hours.FeedDeserializer;
import com.dengsn.hours.util.csv.CSVException;
import com.dengsn.hours.util.csv.CSVRecord;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallDeserializer extends FeedDeserializer<Call>
{
  // Constants
  private static final Pattern PLATFORM_PATTERN = Pattern.compile("([a-z]+)\\|(\\d+[abc]?(?:-\\d+[abc]?)?)");
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("H:mm:ss").withResolverStyle(ResolverStyle.LENIENT);
  
  // Constructor
  public CallDeserializer(Feed feed)
  {
    super(feed);
  }
  
  // Deserialize
  @Override public Call deserialize(CSVRecord record) throws CSVException
  {
    try
    {
      String id = record.get("stop_id").get();
      Matcher m = PLATFORM_PATTERN.matcher(id);
      if (m.matches())
        id = m.group(1);
          
      return new Call()
        .useTrain(record.get("trip_id").map(this.feed()::getTrain).get())
        .useStation(this.feed().getStation(id))
        .useSequence(record.getInt("stop_sequence").get())
        .useArrivalTime(record.get("arrival_time").map(date -> LocalTime.parse(date,FORMATTER)).get())
        .useDepartureTime(record.get("departure_time").map(date -> LocalTime.parse(date,FORMATTER)).get())
        .usePlatform(m.group(2))
        .usePickupType(record.getInt("pickup_type").map(Call.Type::of).get())
        .useDropoffType(record.getInt("drop_off_type").map(Call.Type::of).get());
    }
    catch (NoSuchElementException ex)
    {
      throw new CSVException("Missing required field",ex);
    }
  }
}
