package structs;

import java.util.Collection;
import java.util.HashMap;

public class AdjacencyList {
    private HashMap<Integer, Vertex> listaSasiedztwa;

    public AdjacencyList()
    {
        this.listaSasiedztwa = new HashMap<>();
    }

    public void addVertex(int Id)
    {
        if(!listaSasiedztwa.containsKey(Id))
        {
            listaSasiedztwa.put(Id, new Vertex(Id));
        }
    }

    public void addEdge(int fromId, int toId,double weight, String name)
    {
        addVertex(fromId);
        addVertex(toId);

        Vertex startVertex = listaSasiedztwa.get(fromId);
        startVertex.addEdge(new Edge(toId, weight, name));
    }


    public Vertex getVertex(int Id)
    {
        return listaSasiedztwa.get(Id);
    }

    public Collection<Vertex> getAdjacencyList()
    {
        return listaSasiedztwa.values();
    }


}
