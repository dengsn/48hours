package com.dengsn.hours.csv;

import java.util.Arrays;

public class CSVRecord
{
  // Variables
  private final String[] tokens;
  
  // Constructor
  CSVRecord(String[] tokens)
  {
    this.tokens = tokens;
  }
  
  // Return a field
  public String get(int index)
  {
    return this.tokens[index];
  }
  
  // Return a casted field
  public int getInt(int index)
  {
    return Integer.parseInt(this.get(index));
  }
  public long getLong(int index)
  {
    return Long.parseLong(this.get(index));
  }
  public float getFloat(int index)
  {
    return Float.parseFloat(this.get(index));
  }
  public double getDouble(int index)
  {
    return Double.parseDouble(this.get(index));
  }
  
  // Convert to string
  @Override public String toString()
  {
    return Arrays.toString(this.tokens);
  }
}
