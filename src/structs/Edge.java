package structs;

public class Edge {
    private final int targetVertexId;
    private final double weight;
    private final String name;

    public Edge(int targetVertexId, double weight, String name)
    {
        this.targetVertexId = targetVertexId;
        this.weight = weight;
        this.name = name;
    }
    public int getTargetVertexId() { return targetVertexId; }
    public double getWeight() { return weight; }
    public String getName() { return name; }
}
