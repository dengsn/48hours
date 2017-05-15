package com.dengsn.hours.csv;

public interface CSVDeserializer<T>
{
  public T deserialize(CSVRecord record);
}
