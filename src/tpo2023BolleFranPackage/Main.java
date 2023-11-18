package tpo2023BolleFranPackage;
import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
//Lee el archivo que tiene los nodos y los costos de las aristas
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\franc\\OneDrive\\Documentos\\UADE\\2DO AÑO\\SEGUNDO CUATRIMESTRE\\PROGRA 3\\WEHBE\\TPO\\tpo2023Progra3\\src\\fuentes\\rutas.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int source = Integer.parseInt(parts[0]);
                    int destination = Integer.parseInt(parts[1]);
                    int cost = Integer.parseInt(parts[2]);
                    graph.addEdge(source, destination, cost);
                }
            }
            reader.close();

            // Leer el archivo 'clientesYCentros' para actualizar los atributos de los nodos (centros y productores)
            BufferedReader clientCenterReader = new BufferedReader(new FileReader("C:\\Users\\franc\\OneDrive\\Documentos\\UADE\\2DO AÑO\\SEGUNDO CUATRIMESTRE\\PROGRA 3\\WEHBE\\TPO\\tpo2023Progra3\\src\\fuentes\\clientesYCentros.txt"));
            while ((line = clientCenterReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {//corresponde a centro de distribucion
                    int cDistribucion = Integer.parseInt(parts[0]);
                    int costToSend = Integer.parseInt(parts[1]);
                    int annualMaintenanceCost = Integer.parseInt(parts[2]);
                    Node node = graph.getNode(cDistribucion);//busco el centro de distribucion
                    if (node != null) {
                        node.setCostToSend(costToSend);//asigno el costo para enviar al puerto
                        node.setAnnualMaintenanceCost(annualMaintenanceCost);//asigno costo de mantenimiento
                    }
                } else if (parts.length == 2) {//Corresponde a informacion de productores
                    int client = Integer.parseInt(parts[0]);
                    int annualProductionVolume = Integer.parseInt(parts[1]);
                    Node node = graph.getNode(client);
                    if (node != null) {
                        node.setClientNumber(client);
                        node.setAnnualProductionVolume(annualProductionVolume);
                    }
                }
            }
            clientCenterReader.close();

            // Ejemplo: obtener los vecinos del nodo 1 y su información adicional
            List<Edge> neighborsOfNode1 = graph.getNeighbors(1);
            System.out.print("Vecinos");
            System.out.println();
            for (Edge edge : neighborsOfNode1) {
                System.out.println("Nodo: " + edge.getDestination() + ", Costo: " + edge.getCost());
            }
            Node node1 = graph.getNode(1);
            if (node1 != null) {
                System.out.println("Costo de Envío: " + node1.getCostToSend());
                System.out.println("Costo Anual de Mantenimiento: " + node1.getAnnualMaintenanceCost());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

