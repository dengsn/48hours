package com.dengsn.hours.model.journey;

import com.dengsn.hours.model.Identity;
import com.dengsn.hours.model.util.Service;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.LinkedList;

public class Train extends LinkedList<Call> implements Identity
{
  // Constants
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.LENIENT);
  
  // Variables
  private String id;
  private Service service;
  private TrainType type;
  private String name;
  private String direction;

  // Getters and setters
  @Override public String getId()
  {
    return this.id;
  }
  @Override public Train useId(String id)
  {
    this.id = id;
    return this;
  }
  public Service getService()
  {
    return this.service;
  }
  public Train useService(Service service)
  {
    this.service = service;
    return this;
  }
  public TrainType getType()
  {
    return this.type;
  }
  public Train useType(TrainType type)
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
  
  // Convert to string
  @Override public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append(this.type.toString());
    if (!this.name.equals("0"))
      sb.append(" ").append(this.name);
    sb.append(" naar ").append(this.direction);
    return sb.toString();
  }
}
