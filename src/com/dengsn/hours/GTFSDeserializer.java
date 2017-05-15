package com.dengsn.hours;

import com.dengsn.hours.csv.CSVDeserializer;

public abstract class GTFSDeserializer<T> implements CSVDeserializer<T>
{
  // Varables
  protected final GTFS database;
  
  // Constructor
  public GTFSDeserializer(GTFS database)
  {
    this.database = database;
  }
  
  // Return the database
  public GTFS getDatabase()
  {
    return this.database;
  }
}
