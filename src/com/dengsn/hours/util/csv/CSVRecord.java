package com.dengsn.hours.util.csv;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CSVRecord
{
  // Variables
  private final List<String> tokens;
  private final List<String> headers;
  
  // Constructor
  CSVRecord(String[] tokens, String[] headers)
  {
    this.tokens = Arrays.asList(tokens);
    this.headers = Arrays.asList(headers);
  }
  
  // Return if a field is present
  public boolean contains(String name)
  {
    return this.headers.contains(name);
  }
  
  // Return the index for a field
  public int indexOf(String name)
  {
    return this.headers.indexOf(name);
  }
  
  // Return a field
  public Optional<String> get(String name)
  {
    if (this.contains(name))
      return Optional.of(this.tokens.get(this.indexOf(name)));
    else
      return Optional.empty();
  }
  
  // Return a casted field
  public Optional<Integer> getInt(String name)
  {
    return this.get(name).map(Integer::parseInt);
  }
  public Optional<Long> getLong(String name)
  {
    return this.get(name).map(Long::parseLong);
  }
  public Optional<Float> getFloat(String name)
  {
    return this.get(name).map(Float::parseFloat);
  }
  public Optional<Double> getDouble(String name)
  {
    return this.get(name).map(Double::parseDouble);
  }
  
  // Convert tot string
  @Override public String toString()
  {
    return this.headers.toString() + " " + this.tokens.toString();
  }
}
