package com.dengsn.hours.model.journey;

import com.dengsn.hours.graph.Graph;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JourneyGraph extends Graph<JourneyStation,Journey>
{
  // Constructor
  public JourneyGraph(Collection<Journey> journeys)
  {
    for (Journey journey : journeys)
    {
      this.getEdges().add(journey);
      this.getNodes().add(journey.getStart());
      this.getNodes().add(journey.getEnd());
    }
  }
  
  // Returns all direct edges from a node
  @Override public LinkedList<Journey> getDirectEdges(JourneyStation journey, List<Journey> ignore)
  {
    return this.getEdges().stream()
      .filter(e -> e.getStart().getStation().equals(journey.getStation()))
      .filter(e -> e.getStart().getTime().isAfter(journey.getTime().plusMinutes(1)) && e.getStart().getTime().isBefore(journey.getTime().plusMinutes(30)))
      .filter(e -> !ignore.stream().anyMatch(j -> j.getStart().getStation().equals(e.getStart().getStation()) && j.getEnd().getStation().equals(e.getEnd().getStation())))
      .collect(Collectors.toCollection(LinkedList::new));
  }
  
  // Returns all direct edges from a node without ignore list
  @Override public LinkedList<Journey> getDirectEdges(JourneyStation node)
  {
    return this.getDirectEdges(node,new LinkedList<>());
  }
  
  // Returns all neighbors of a node
  @Override public Set<JourneyStation> getNeighbors(JourneyStation node)
  {
    return this.getDirectEdges(node).stream()
      .map(e -> (JourneyStation)e.getOpposite(node))
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}
