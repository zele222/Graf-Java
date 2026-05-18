package algo;

import structs.*;

import java.util.ArrayList;
import java.util.List;

public class TutteAlgorithm {
    private boolean hasFrame;
    private int iteration;
    private AdjacencyList graph;

    public TutteAlgorithm(AdjacencyList graph)
    {
        this.graph = graph;
        this.hasFrame = false;
        this.iteration = 0;
    }

    private List<Vertex> getGuaranteedFrame() {
        List<Vertex> foundFrame = new ArrayList<>();

        return foundFrame;
    }

    public void nextStep()
    {
        if(!hasFrame)
        {
            return;
        }
        //funkcja z maxiter
        this.iteration++;
    }
}
