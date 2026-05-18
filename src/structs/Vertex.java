package structs;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private final int id;
    private double x;
    private double y;
    private boolean isOuter;

    private List<Edge> edges;


    public Vertex(int id)
    {
        this.id = id;
        this.x = 0.0;
        this.y = 0.0;
        this.isOuter = false;
        edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public int getId() { return id; }
    public double getX() { return x; }
    public double getY() { return y; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public boolean getIsOuter() { return isOuter; }
    public void setOuter(boolean czy_zewn) { isOuter = czy_zewn; }
    public List<Edge> getEdges() { return edges; }
}
