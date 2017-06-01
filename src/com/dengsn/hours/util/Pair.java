package com.dengsn.hours.util;

import java.util.Objects;

public class Pair<A,B>
{
  // Variables
  private A a;
  private B b;

  // Constructor
  public Pair(A a, B b)
  {
    this.a = a;
    this.b = b;
  }

  // Management
  public final A getA()
  {
    return this.a;
  }
  public final void setA(A a)
  {
    this.a = a;
  }
  public final B getB()
  {
    return this.b;
  }
  public final void setB(B b)
  {
    this.b = b;
  }

  // Object methods
  @Override public boolean equals(Object o)
  {
    if (o == null || !this.getClass().equals(o.getClass()))
      return false;
    
    Pair pair = (Pair)o;
    if (!Objects.equals(this.a,pair.a))
      return false;
    else if (!Objects.equals(this.b,pair.b))
      return false;
    else return true;
  }
  @Override public int hashCode()
  {
    int hash = 3;
    hash = 79 * hash + Objects.hashCode(this.a);
    hash = 79 * hash + Objects.hashCode(this.b);
    return hash;
  }
  
  // Converts to String
  @Override public String toString()
  {
    return "(" + this.a.toString() + ", " + this.b.toString() + ")";
  }
}
