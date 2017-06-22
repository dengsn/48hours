package com.dengsn.hours;

import com.dengsn.hours.model.Identity;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Dict<T extends Identity> extends AbstractCollection<T>
{
  // Variables
  private final Map<String,T> map;
  
  // Constructor
  public Dict()
  {
    this.map = new HashMap<>();
  }
  public Dict(Map<String,T> source)
  {
    this.map = source;
  }
  
  // Returns an oject by id
  public T get(String id)
  {
    if (this.map.containsKey(id))
      return this.map.get(id);
    else
      throw new NoSuchElementException(id);
  }
  
  // Returns if the dict contains an id
  public boolean hasObject(String id)
  {
    return this.map.containsKey(id);
  }
  
  // Add an object
  @Override public boolean add(T t)
  {
    return !Objects.equals(this.map.put(t.getId(),t),null);
  }
  
  // Add multiple objects
  @Override public boolean addAll(Collection<? extends T> collection)
  {
    boolean result = false;
    for (T t : collection)
      result = result || this.add(t);
    return result;
  }
  
  // Combine with another dics
  private Dict<T> combine(Dict<T> other)
  {
    this.addAll(other);
    return this;
  }

  // Returns an iterator for the dict
  @Override public Iterator<T> iterator()
  {
    return this.map.values().iterator();
  }

  // Returns the size of this dict
  @Override public int size()
  {
    return this.map.size();
  }
  
  // Return a list of the elements
  public List<T> asList()
  {
    return this.map.values().stream()
      .collect(Collectors.toList());
  }
  
  // Return a set of the elements
  public Set<T> asSet()
  {
    return this.map.values().stream()
      .collect(Collectors.toSet());
  }
  
  // Returns a collector to put this identity in a map
  public static <T extends Identity> Collector<T,?,Dict<T>> collector()
  {
    return Collector.of(
      Dict<T>::new,
      (Dict<T> dict, T t) -> dict.add(t),
      Dict<T>::combine);
  }
}
