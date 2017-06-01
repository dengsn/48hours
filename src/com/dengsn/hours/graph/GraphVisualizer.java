package com.dengsn.hours.graph;

import com.dengsn.hours.edge.Connection;
import com.dengsn.hours.node.Station;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class GraphVisualizer extends JPanel
{  
  // Variables
  private final Graph<Station,Connection> graph;
  
  // Constructor
  public GraphVisualizer(Graph<Station,Connection> graph)
  {
    super.setDoubleBuffered(true);
    
    this.graph = graph;
  }
  
  // Paint
  @Override public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    Graphics2D graphics2D = (Graphics2D)g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
    
    double minX = Double.NaN, minY = Double.NaN, maxX = Double.NaN, maxY = Double.NaN;
    for (Station n : this.graph.getNodes())
    {
      if (this.graph.getDirectEdges(n).isEmpty())
        continue;
      
      double lat = n.getLatitude();
      double lon = n.getLongitude();
      
      if (Double.isNaN(minX) || lon < minX)
        minX = lon;
      if (Double.isNaN(minY) || lat < minY)
        minY = lat;
      if (Double.isNaN(maxX) || lon > maxX)
        maxX = lon;
      if (Double.isNaN(maxY) || lat > maxY)
        maxY = lat;
    }
    
    Dimension size = this.getSize();
    double factorX = (size.width - 48) / (maxX - minX);
    double factorY = (size.height - 48) / (maxY - minY);
    
    // Draw the edges
    for (Connection e : this.graph.getEdges())
    {
      int x1 = (int)((e.getStart().getLongitude() - minX) * factorX) + 24;
      int y1 = size.height - ((int)((e.getStart().getLatitude() - minY) * factorY) + 24);
      int x2 = (int)((e.getEnd().getLongitude() - minX) * factorX) + 24;
      int y2 = size.height - ((int)((e.getEnd().getLatitude() - minY) * factorY) + 24);
      
      g.setColor(Color.BLACK);
      g.drawLine(x1,y1,x2,y2);
    }
    
    // Draw the nodes
    for (Station n : this.graph.getNodes())
    {
      int x = (int)((n.getLongitude() - minX) * factorX) + 24;
      int y = size.height - ((int)((n.getLatitude() - minY) * factorY) + 24);
      
      g.setColor(Color.RED);
      g.fillOval(x-2,y-2,5,5);
    }
  }
}
