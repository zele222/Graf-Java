package view;

import structs.AdjacencyList;
import util.GraphParser;
import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;



public class TopMenuBar {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu viewMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JCheckBoxMenuItem toggleValMenuItem;
    private JCheckBoxMenuItem toggleEdgeNameMenuItem;
    private JCheckBoxMenuItem toggleVertexNameMenuItem;


    private final Consumer<AdjacencyList> graphLoaded;
    private final Consumer<String> fileSaved;
    private final BiConsumer<String, Boolean> viewChanged;


    public TopMenuBar(Consumer<AdjacencyList> graphLoaded, Consumer<String> fileSaved, BiConsumer<String, Boolean> viewChanged)
    {
        this.graphLoaded = graphLoaded;
        this.fileSaved = fileSaved;
        this.viewChanged = viewChanged;
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
                    AdjacencyList graph = GraphParser.loadGraph(fc.getSelectedFile());
                    if (graphLoaded != null)
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
        fileMenu.add(openMenuItem);

        saveMenuItem = new JMenuItem("Zapisz");
        saveMenuItem.addActionListener(e->{
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                try{
                    String filepath = fc.getSelectedFile().getAbsolutePath();
                    if(fileSaved != null)
                    {
                        fileSaved.accept(filepath);
                    }
                }catch(Exception exc)
                {
                    JOptionPane.showMessageDialog(menuBar, exc.getMessage(), "Błąd zapisania pliku", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        fileMenu.add(saveMenuItem);

        return fileMenu;
    }

    private JMenu createViewMenu()
    {
        viewMenu = new JMenu("Widok");
        viewMenu.setFont(viewMenu.getFont().deriveFont(Font.BOLD));

        toggleValMenuItem = new JCheckBoxMenuItem("Pokaż wagi", false);
        toggleValMenuItem.addActionListener(e -> {
            if(viewChanged!=null)
            {
                viewChanged.accept("weights", toggleValMenuItem.isSelected());
            }
        });
        viewMenu.add(toggleValMenuItem);

        toggleEdgeNameMenuItem = new JCheckBoxMenuItem("Pokaż nazwy krawędzi", false);
        toggleEdgeNameMenuItem.addActionListener(e -> {
            if (viewChanged != null) {
                viewChanged.accept("edgeNames", toggleEdgeNameMenuItem.isSelected());
            }
        });
        viewMenu.add(toggleEdgeNameMenuItem);

        toggleVertexNameMenuItem = new JCheckBoxMenuItem("Pokaż nazwy węzłów", false);
        toggleVertexNameMenuItem.addActionListener(e -> {
            if (viewChanged != null) {
                viewChanged.accept("vertexNames", toggleVertexNameMenuItem.isSelected());
            }
        });
        viewMenu.add(toggleVertexNameMenuItem);

        return viewMenu;
    }

    public JMenuBar getMenuBar()
    {
        return menuBar;
    }

}
