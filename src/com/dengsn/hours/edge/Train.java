package com.dengsn.hours.edge;

import com.dengsn.hours.Route;
import com.dengsn.hours.node.Station;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Train extends Path<Station,Journey>
{
  // Constants
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.LENIENT);
  
  // Variables
  private String id;
  private Route type;
  private String name;
  private String direction;
  private String service;

  // Getters and setters
  public String getId()
  {
    return this.id;
  }
  public Train useId(String id)
  {
    this.id = id;
    return this;
  }
  public Route getType()
  {
    return this.type;
  }
  public Train useType(Route type)
  {
    this.type = type;
    return this;
  }
  public String getName()
  {
    return this.name;
  }
  public Train useName(String name)
  {
    this.name = name;
    return this;
  }
  public String getDirection()
  {
    return this.direction;
  }
  public Train useDirection(String direction)
  {
    this.direction = direction;
    return this;
  }
  public String getService()
  {
    return this.service;
  }
  public Train useService(String service)
  {
    this.service = service;
    return this;
  }
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder(this.type.toString());
    if (!this.name.equals("0"))
      sb.append(" ").append(this.name);
    sb.append(" naar ").append(this.direction);
    
    if (this.getEdges().size() > 0)
    {
      Journey first = this.getEdges().get(0);
      sb.append("\n  ").append(FORMATTER.format(first.getDeparture())).append("   ").append(first.getStart());
    
      for (Journey j : this.getEdges())
        sb.append("\n  ").append(FORMATTER.format(j.getArrival())).append("A  ").append(j.getEnd());
    }
    
    return sb.toString();
  }
}
