package com.dengsn.hours.graph;

import com.dengsn.hours.graph.edge.Connection;
import com.dengsn.hours.graph.node.Node;
import java.util.List;
import java.util.Set;

public class ConnectionGraph<N extends Node> extends Graph<N,Connection<N>>
{
  // Constructor
  public ConnectionGraph(Set<N> nodes, List<Connection<N>> edges)
  {
    super(nodes,edges);
  }
  public ConnectionGraph(Graph<N,Connection<N>> source)
  {
    super(source);
  }
}
