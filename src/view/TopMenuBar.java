package view;

import structs.AdjacencyList;
import util.GraphParser;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;



public class TopMenuBar {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu viewMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JCheckBoxMenuItem toggleValMenuItem;
    private JCheckBoxMenuItem toggleEdgeNameMenuItem;


    private final Consumer<AdjacencyList> graphLoaded;


    public TopMenuBar(Consumer<AdjacencyList> graphLoaded)
    {
        this.graphLoaded = graphLoaded;
        initialize();
        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu());
    }

    private void initialize()
    {
        menuBar = new JMenuBar();
    }

    private JMenu createFileMenu()
    {
        fileMenu = new JMenu("Plik");
        fileMenu.setFont(fileMenu.getFont().deriveFont(Font.BOLD));

        openMenuItem = new JMenuItem("Wczytaj");
        openMenuItem.addActionListener(e->{
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    AdjacencyList graph = GraphParser.turnToGraph(fc.getSelectedFile());
                    if (graph != null && graphLoaded != null)
                    {
                        graphLoaded.accept(graph);
                    }
                }
                catch(Exception exc)
                {
                    JOptionPane.showMessageDialog(menuBar, exc.getMessage(), "Błąd wczytywania pliku", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        //openMenuItem.setIcon(new ImageIcon("images/load.png"));
        fileMenu.add(openMenuItem);

        saveMenuItem = new JMenuItem("Zapisz");
        saveMenuItem.addActionListener(e->{
            System.out.println("Zapisz Plik");
        });
        fileMenu.add(saveMenuItem);

        return fileMenu;
    }

    private JMenu createViewMenu()
    {
        viewMenu = new JMenu("Widok");
        viewMenu.setFont(viewMenu.getFont().deriveFont(Font.BOLD));

        toggleValMenuItem = new JCheckBoxMenuItem("Pokaż wagi", true);
        toggleValMenuItem.addActionListener(e -> {
            boolean isShowing = toggleValMenuItem.isSelected();
            System.out.println("Wagi widoczne: " + isShowing);

        });
        viewMenu.add(toggleValMenuItem);

        toggleEdgeNameMenuItem = new JCheckBoxMenuItem("Pokaż nazwy krawędzi", false);
        toggleEdgeNameMenuItem.addActionListener(e -> {
            boolean isShowing = toggleEdgeNameMenuItem.isSelected();
            System.out.println("Nazwy krawędzi widoczne: " + isShowing);
        });
        viewMenu.add(toggleEdgeNameMenuItem);

        return viewMenu;
    }

    public JMenuBar getMenuBar()
    {
        return menuBar;
    }

}
