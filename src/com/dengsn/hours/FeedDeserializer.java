package com.dengsn.hours;

import com.dengsn.hours.util.csv.CSVDeserializer;

public abstract class FeedDeserializer<T> implements CSVDeserializer<T>
{
  // Variables
  private final Feed feed;
  
  // Constructor
  public FeedDeserializer(Feed feed)
  {
    this.feed = feed;
  }
  
  // Return the feed instance
  public Feed feed()
  {
    return this.feed;
  }
}
