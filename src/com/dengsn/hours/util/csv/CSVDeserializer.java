package com.dengsn.hours.util.csv;

@FunctionalInterface
public interface CSVDeserializer<T>
{
  public T deserialize(CSVRecord record) throws CSVException;
}
