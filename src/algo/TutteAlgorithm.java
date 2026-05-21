package algo;

import structs.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TutteAlgorithm {
    private boolean hasFrame;
    private int iteration;
    private final AdjacencyList graph;

    public TutteAlgorithm(AdjacencyList graph)
    {
        this.graph = graph;
        this.hasFrame = false;
        this.iteration = 0;
    }

    public void initialize()
    {
        List<Vertex> frame = getGuaranteedFrame();
        if (frame == null || frame.isEmpty())
        {
            throw new IllegalArgumentException("Nie znaleziono poprawnej ramy w grafie.");
        }

        if (!isFullyConnected())
        {
            throw new IllegalArgumentException("Graf nie jest spójny");
        }

        if (!isPotentiallyPlanar())
        {
            throw new IllegalArgumentException("Graf ma za dużo krawędzi");
        }

        tutteFormOuter(frame);
        this.hasFrame = true;

    }

    private boolean isFullyConnected()
    {
        if(graph.getAdjacencyList().isEmpty()) return false;
        Set<Integer> visitedIds = new HashSet<>();
        Vertex start = graph.getAdjacencyList().iterator().next();
        simpleDfs(start, visitedIds);
        return visitedIds.size() == graph.getAdjacencyList().size();
    }

    private void simpleDfs(Vertex current, Set<Integer> visitedIds)
    {
        visitedIds.add(current.getId());
        for (Edge e : current.getEdges())
        {
            Vertex neighbor = graph.getVertex(e.getTargetVertexId());
            if (neighbor != null && !visitedIds.contains(neighbor.getId()))
            {
                simpleDfs(neighbor, visitedIds);
            }
        }
    }

    private boolean isPotentiallyPlanar()
    {
        int vCount = graph.getAdjacencyList().size();
        if (vCount < 3) return true;
        int eCount = 0;

        for (Vertex v : graph.getAdjacencyList()) {
            eCount += v.getEdges().size();
        }
        eCount = eCount / 2;
        return eCount <= (3 * vCount - 6);
    }

    private List<Vertex> getGuaranteedFrame()
    {
        for(Vertex v : graph.getAdjacencyList())
        {
            List<Vertex> path = new ArrayList<>();
            Set<Vertex> visited = new HashSet<>();

            List<Vertex> frame = dfsFindFrame(v, v, visited, path);
            if (frame != null)
            {
                return frame;
            }
        }
        return null;
    }

    private List<Vertex> dfsFindFrame(Vertex startVertex, Vertex currentVertex, Set<Vertex> visited, List<Vertex> path)
    {
        path.add(currentVertex);
        visited.add(currentVertex);

        for (Edge E : currentVertex.getEdges())
        {
            Vertex neighbor = graph.getVertex(E.getTargetVertexId());
            if (neighbor.getId() == startVertex.getId() && path.size() >= 3)
            {
                List<Vertex> pretendent = new ArrayList<>(path);

                while (true) {
                    List<Vertex> newCycle = splitIfShortcut(pretendent);
                    if (newCycle == pretendent)
                    {
                        break;
                    }
                    pretendent = newCycle;
                }

                if(isConnectedWithoutCycle(pretendent))
                {
                    visited.remove(currentVertex);
                    path.remove(path.size() - 1);
                    return pretendent;
                }

            }else if (!visited.contains(neighbor))
            {
                List<Vertex> found = dfsFindFrame(startVertex, neighbor, visited, path);

                if (found != null) {
                    visited.remove(currentVertex);
                    path.remove(path.size() - 1);
                    return found;
                }
            }
        }
        visited.remove(currentVertex);
        path.remove(path.size() - 1);
        return null;
    }

    private List<Vertex> splitIfShortcut(List<Vertex> cycle)
    {
        for(int i = 0; i < cycle.size(); i++)
        {
            Vertex U = cycle.get(i);
            for(Edge E : U.getEdges())
            {
                int targetId = E.getTargetVertexId();
                int VIndex = -1;
                for (int j = 0; j < cycle.size(); j++) {
                    if (cycle.get(j).getId() == targetId) {
                        VIndex = j;
                        break;
                    }
                }
                if(VIndex != -1)
                {
                    int distance = Math.abs(i - VIndex);
                    if(distance > 1 && distance < cycle.size() - 1 && i < VIndex)
                    {
                        List<Vertex> right = new ArrayList<>(cycle.subList(i, VIndex + 1));
                        List<Vertex> left = new ArrayList<>(cycle.subList(0, i + 1));
                        left.addAll(cycle.subList(VIndex, cycle.size()));

                        if(isConnectedWithoutCycle(left))
                        {
                            return left;
                        } else if (isConnectedWithoutCycle(right))
                        {
                            return right;
                        } else
                        {
                            return cycle;
                        }
                    }
                }
            }
        }
        return cycle;
    }

    private boolean isConnectedWithoutCycle(List<Vertex> cycle)
    {
        if(cycle.size() == graph.getAdjacencyList().size())
        {
            return true;
        }
        if(cycle.size() > graph.getAdjacencyList().size())
        {
            return false;
        }

        HashSet<Vertex> frame = new HashSet<>(cycle);   //Przyspieszenie wyszukiwania contains do O(1)
        Set<Vertex> visited = new HashSet<>();

        Vertex startVertex = null;
        for(Vertex V : graph.getAdjacencyList())
        {
            if (!cycle.contains(V))
            {
                startVertex = V;
                break;
            }
        }
        if (startVertex != null)
        {
            dfsWithoutCycle(startVertex, frame, visited);
        }
        return visited.size() == (graph.getAdjacencyList().size() - cycle.size());
    }

    private void dfsWithoutCycle(Vertex current, Set<Vertex> cycle, Set<Vertex> visited)
    {
        visited.add(current);

        for (Edge E : current.getEdges())
        {
            Vertex neighbor = graph.getVertex(E.getTargetVertexId());
            if (neighbor != null && !cycle.contains(neighbor) && !visited.contains(neighbor))
            {
                dfsWithoutCycle(neighbor, cycle, visited);
            }
        }
    }

    private void tutteFormOuter(List<Vertex> frame)
    {
        int n = frame.size();
        double angle = 2.0 * Math.PI/n;

        for(int i = 0; i < frame.size(); i++)
        {
            Vertex currentVertex = frame.get(i);
            currentVertex.setX(Math.cos(i * angle));
            currentVertex.setY(Math.sin(i * angle));
            currentVertex.setOuter(true);
        }
    }

    public void nextStep()
    {
        if (!hasFrame) {
            return;
        }

        for(Vertex currentVertex : graph.getAdjacencyList())
        {
            if (currentVertex.getIsOuter())
            {
                continue;
            }

            double suma_x = 0;
            double suma_y = 0;
            double suma_wag = 0;

                for(Edge E : currentVertex.getEdges())
                {
                    Vertex neighbor = graph.getVertex(E.getTargetVertexId());
                    if(neighbor != null)
                    {
                        double weight = E.getWeight() <= 0 ? 1.0 : 1.0/ E.getWeight();
                        suma_x += weight * neighbor.getX();
                        suma_y += weight * neighbor.getY();
                        suma_wag += weight;
                    }
                }
                if(suma_wag > 0)
                {
                    currentVertex.setX(suma_x / suma_wag);
                    currentVertex.setY(suma_y / suma_wag);
                }
        }
        this.iteration++;
    }

    public int getIteration()
    {
        return iteration;
    }

    public AdjacencyList getGraph() {
        return this.graph;
    }

    public void reset()
    {
        this.iteration = 0;
        this.hasFrame = false;

        for (Vertex currentVertex : graph.getAdjacencyList())
        {
            currentVertex.setX(0.0);
            currentVertex.setY(0.0);
            currentVertex.setOuter(false);
        }
        initialize();
    }
}
