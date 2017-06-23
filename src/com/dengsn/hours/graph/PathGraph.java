package com.dengsn.hours.graph;

import com.dengsn.hours.graph.edge.Connection;
import com.dengsn.hours.graph.edge.Path;
import com.dengsn.hours.graph.node.Node;
import java.util.List;
import java.util.Set;

public class PathGraph<N extends Node, E extends Connection<N>> extends Graph<N,Path<N,E>>
{
  // Constructor
  public PathGraph(Set<N> nodes, List<Path<N,E>> edges)
  {
    super(nodes,edges);
  }
  public PathGraph(Graph<N,Path<N,E>> source)
  {
    super(source);
  }
}
