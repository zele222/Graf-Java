package util;

import structs.AdjacencyList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GraphParser {
    public static AdjacencyList turnToGraph(File file)
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
}
