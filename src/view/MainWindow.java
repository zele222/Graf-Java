package view;
import algo.TutteAlgorithm;
import structs.AdjacencyList;
import structs.Vertex;

import javax.swing.*;
import java.awt.*;

import static util.GraphParser.saveCoords;


public class MainWindow
{

    private JFrame window;
    private TutteAlgorithm algorithm;

    public MainWindow()
    {
        initialize();
        addElements();
        show();
    }

    private void initialize()
    {
        window = new JFrame("Tutte's Algorithm Visualisation");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(500, 400);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
    }

    private void addElements()
    {
        GraphPanel graphPanel = new GraphPanel();
        window.add(graphPanel, BorderLayout.CENTER);

        ControlPanel controlPanel = new ControlPanel(
        () -> //nextStep
        {
            if (algorithm != null)
            {
                algorithm.nextStep();
                graphPanel.repaint();
            }
        },
        () -> //skipAll
        {
            if (algorithm != null)
            {
                for (int i = 0; i < 10000 - algorithm.getIteration(); i++)
                {
                    algorithm.nextStep();
                }
                graphPanel.repaint();
            }
        },
        () -> //reset
        {
            if (algorithm != null)
            {
                algorithm.reset();
                graphPanel.resetView();
            }
        }
        );

        window.add(controlPanel.getPanel(), BorderLayout.SOUTH);

        TopMenuBar menuBar = new TopMenuBar(graph -> {
            System.out.println("Graf wczytany");
            algorithm = new TutteAlgorithm(graph);
            try
            {
                algorithm.initialize();
                System.out.println("sukces");
                graphPanel.takeGraph(graph);
                graphPanel.resetView();
            } catch(IllegalArgumentException e)
            {
                JOptionPane.showMessageDialog(window, e.getMessage(), "Błąd budowania grafu", JOptionPane.ERROR_MESSAGE);
                algorithm = null;
            }
        }, filePath -> {
            if (algorithm != null && algorithm.getGraph() != null)
            {
                saveCoords(algorithm.getGraph(), filePath);
            }
            else {
                JOptionPane.showMessageDialog(window, "Nie ma żadnego grafu do zapisania", "Błąd", JOptionPane.WARNING_MESSAGE);
            }});

        window.setJMenuBar(menuBar.getMenuBar());
    }

    private void show()
    {
        window.setVisible(true);
    }


}
