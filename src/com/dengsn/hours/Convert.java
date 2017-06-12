package com.dengsn.hours;

import com.dengsn.hours.node.Station;
import com.dengsn.hours.util.csv.CSVIterator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Convert
{
  public static void main(String[] args) throws FileNotFoundException, IOException
  {
    System.setOut(new PrintStream(new FileOutputWtream("data/connections.txt")));
    
    // Read stations
    List<Station> stations = new CSVIterator(new FileReader("data/stations.txt")).stream()
      .map(record -> new Station(record.get(0),record.get(1))
        .useLatitude(record.getDouble(2))
        .useLongitude(record.getDouble(3)))
      .collect(Collectors.toList());
    
    Function<String,Station> getStation = station -> {
      for (Station s : stations)
      {
        if (s.getId().equalsIgnoreCase(station))
          return s;
        if (s.getName().equalsIgnoreCase(station))
          return s;
      }
      System.err.println(station);
      return null;
    };
      
    // Read connections
    new CSVIterator(new FileReader("data/connections_long.txt")).stream()
      .forEach(record -> System.out.printf(Locale.ENGLISH,"%s,%s,%.1f%n",getStation.apply(record.get(0)).getId(),getStation.apply(record.get(1)).getId(),record.getDouble(2)));
  }
}
