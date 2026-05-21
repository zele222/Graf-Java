package util;

import structs.AdjacencyList;
import structs.Vertex;

import java.io.*;
import java.util.Locale;

public class GraphParser {
    public static AdjacencyList loadGraph(File file)
    {
        AdjacencyList adjList = new AdjacencyList();

        System.out.println(file.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" ");
                adjList.addEdge(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), parts[0]);
                adjList.addEdge(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Double.parseDouble(parts[3]), parts[0]);
            }
        } catch (IOException e)
        {
            throw new RuntimeException("Wystąpił błąd podczas czytania pliku: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e)
        {
            throw new IllegalArgumentException("Plik jest źle sformatowany. Poprawnie: [nazwa] [v1] [v2] [waga].");
        }

        return adjList;
    }

    public static void saveCoords(AdjacencyList graph, String filename)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename)))
        {
            for (Vertex V : graph.getAdjacencyList())
            {
                writer.printf(Locale.US, "%d %.6f %.6f\n", V.getId(), V.getX(), V.getY());
            }
            System.out.println("Pomyślnie zapisano współrzędne do pliku.");

        } catch (IOException e)
        {
            throw new RuntimeException("Błąd zapisywania koordynatów: " + e.getMessage());
        }
    }

}
