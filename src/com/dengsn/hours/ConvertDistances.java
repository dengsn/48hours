package com.dengsn.hours;

import com.dengsn.hours.model.Station;
import com.dengsn.hours.csv.CSVIterator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConvertDistances
{
  public static void main(String[] args) throws FileNotFoundException, IOException
  {
    // Read stations
    List<Station> stations = new CSVIterator(new FileReader("data/stations.txt")).stream()
      .map(record -> new Station()
        .useId(record.get(0))
        .useName(record.get(1))
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
      throw new NoSuchElementException(station);
    };
      
    // Read connections
    PrintStream out = new PrintStream(new FileOutputStream("data/connections.txt"));
    new CSVIterator(new FileReader("data/connections_long.txt")).stream()
      .forEach(record -> {
        Station start = getStation.apply(record.get(0));
        Station end = getStation.apply(record.get(1));
        out.printf(Locale.ENGLISH,"%s,%s,%.1f%n",start.getId(),end.getId(),record.getDouble(2));
      });
  }
}
