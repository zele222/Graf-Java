package view;
import algo.TutteAlgorithm;

import javax.swing.*;
import java.awt.*;


public class MainWindow{

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
        ControlPanel controlPanel = new ControlPanel();
        window.add(controlPanel.getPanel(), BorderLayout.SOUTH);

        TopMenuBar menuBar = new TopMenuBar(graph -> {
            System.out.println("Graf wczytany");
            algorithm = new TutteAlgorithm(graph);
        });

        window.setJMenuBar(menuBar.getMenuBar());

        GraphPanel graphPanel = new GraphPanel();
        window.add(graphPanel, BorderLayout.CENTER);
    }

    private void show()
    {
        window.setVisible(true);
    }


}
