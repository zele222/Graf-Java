package view;

import structs.AdjacencyList;
import structs.Edge;
import structs.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphPanel extends JPanel {

    AdjacencyList graph;
    private double zoom = 150.0;
    private double offsetX = 0.0;
    private double offsetY = 0.0;
    private int lastMouseX;
    private int lastMouseY;

    public GraphPanel() {
        initialize();
    }

    private void initialize()
    {
        setBackground(new Color(43, 45, 48));
        this.addMouseWheelListener(e -> {

            double oldZoom = zoom;

            double scaleFactor = Math.pow(1.1, -e.getPreciseWheelRotation());
            zoom *= scaleFactor;

            if (zoom < 10) zoom = 10;
            if (zoom > 2.9809518375513878E7) zoom = 2.9809518375513878E7;
            System.out.println(zoom);

            double actualScaleFactor = zoom / oldZoom;
            double mouseX = e.getX() - (getWidth() / 2.0) - offsetX;
            double mouseY = e.getY() - (getHeight() / 2.0) - offsetY;
            offsetX -= mouseX * (actualScaleFactor - 1.0);
            offsetY -= mouseY * (actualScaleFactor - 1.0);

            repaint();
        });

        MouseAdapter mouseAdapter = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                int deltaX = e.getX() - lastMouseX;
                int deltaY = e.getY() - lastMouseY;

                offsetX += deltaX;
                offsetY += deltaY;

                lastMouseX = e.getX();
                lastMouseY = e.getY();

                repaint();
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    public void takeGraph(AdjacencyList graph)
    {
        this.graph = graph;
        repaint();
    }

    public void resetView()
    {
        this.zoom = 150.0;
        this.offsetX = 0.0;
        this.offsetY = 0.0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2d.setStroke(new BasicStroke(3));

        int radius = 3;

        g2d.translate(((double) getWidth() / 2) + offsetX, ((double) getHeight() / 2) + offsetY);

        if (graph == null)
        {
            return;
        }


        for(Vertex V : graph.getAdjacencyList()) {
            for (Edge E : V.getEdges()) {
                Vertex neighbor = graph.getVertex(E.getTargetVertexId());
                if (neighbor.getId() < V.getId()) {
                    int v1X = (int) (neighbor.getX() * zoom);
                    int v2X = (int) (V.getX() * zoom);
                    int v1Y = (int) (neighbor.getY() * zoom);
                    int v2Y = (int) (V.getY() * zoom);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.drawLine(v1X, v1Y, v2X, v2Y);
                    int midX = (v1X + v2X) / 2;
                    int midY = (v1Y + v2Y) / 2;

                    g2d.setColor(Color.GREEN);
                    g2d.drawString(E.getName(), midX, midY + 5);

                    String weight = String.valueOf(E.getWeight());
                    g2d.setColor(Color.blue);
                    g2d.drawString(weight, midX, midY - 5);
                }
            }
        }
        for(Vertex V : graph.getAdjacencyList())
        {
            int x = (int)(V.getX() * zoom);
            int y = (int)(V.getY() * zoom);

            if (V.getIsOuter())
            {
                g2d.setColor(Color.RED);
            } else
            {
                g2d.setColor(Color.CYAN);
            }

            g2d.fillOval((x - radius), y - radius, radius *2, radius *2);
            String vId = String.valueOf(V.getId());
            g2d.setColor(Color.orange);
            g2d.drawString(vId, x+5, y+ 5);
        }
    }
}
