package com.dengsn.hours.csv;

public class CSVException extends RuntimeException
{
  public CSVException(String message) 
  { 
    super(message); 
  }
  public CSVException(String message, Throwable cause) 
  { 
    super(message,cause); 
  }
}
