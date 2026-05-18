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
        }
        catch (IOException e)
        {
                System.err.println("Wystąpił błąd podczas czytania pliku: " + e.getMessage());
                return null;
        }
        catch (ArrayIndexOutOfBoundsException | NumberFormatException e)
        {
        System.err.println("Plik źle sformatowany: " + e.getMessage());
        return null;
        }

        return adjList;
    }
}
