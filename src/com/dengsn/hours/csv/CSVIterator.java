package com.dengsn.hours.csv;

import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CSVIterator implements Iterator<CSVRecord>
{
  // Variables
  private final Scanner scanner;
  private final char delimiter;
  private final char enclosure;
  
  private final String[] headers;
  
  private int position = 0;
  
  // Constructors
  public CSVIterator(Reader reader, char delimiter, char enclosure)
  {
    this.scanner = new Scanner(reader).useDelimiter("\\r?\\n");
    this.delimiter = delimiter;
    this.enclosure = enclosure;
 
    this.headers = this.parse(this.scanner.next());
  }
  public CSVIterator(Reader reader, char delimiter)
  {
    this(reader,delimiter,'"');
  }
  public CSVIterator(Reader reader)
  {
    this(reader,',','"');
  }
  
  // Internal parsing function
  private String[] parse(String line)
  {
    // Check if the line is null or empty
    if (line == null)
      throw new NullPointerException("line cannot be null");
    if (line.isEmpty())
      return new String[0];
    
    // List for storing the tokens
    List<String> tokens = new LinkedList<>();
    StringBuilder token = new StringBuilder();
    boolean enclosed = false;
    
    // Iterate over the string
    for (int i = 0; i < line.length(); i ++)
    {
      char ch = line.charAt(i);
      
      // Check if we're in the enclosure
      if (enclosed)
      {
        // If we are at the end of the enclosure
        if (ch == this.enclosure)
          enclosed = false;
        
        // Otherwise just add if not a newline
        else if (ch != '\r' && ch != '\n')
          token.append(ch);
      }
      else
      {
        // If we are at the begin of an enclosure
        if (ch == this.enclosure)
          enclosed = true;
        
        // If we are at a delimiter
        else if (ch == this.delimiter)
        {
          tokens.add(token.toString());
          token.delete(0,token.length());
        }
        
        // Otherwise just add if not a newline
        else if (ch != '\r' && ch != '\n')
          token.append(ch);
      }
    }
    
    // Add the last token and return the tokens
    tokens.add(token.toString());
    return tokens.toArray(new String[tokens.size()]);
  }
  
  // Returns if there are more records to read
  @Override public boolean hasNext()
  {
    return this.scanner.hasNext();
  }

  // Returns the next record
  @Override public CSVRecord next()
  {
    this.position ++;
    if (this.position % 100000 == 0)
      System.out.printf("  %dk records processed%n",this.position / 1000);
    
    return new CSVRecord(this.parse(this.scanner.next()),this.headers);
  }
  
  // Return a stream for this iterator
  public Stream<CSVRecord> stream()
  {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this,Spliterator.ORDERED | Spliterator.IMMUTABLE),false);
  }
}
