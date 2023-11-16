package tpo2023BolleFran;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        //Lee el archvio que tiene los nodos y los costos de las aristas
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/lucasbollella/eclipse-workspace/tpo2023BolleFran/src/tpo2023BolleFran/fuentes/rutas.txt"));
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
            BufferedReader clientCenterReader = new BufferedReader(new FileReader("/Users/lucasbollella/eclipse-workspace/tpo2023BolleFran/src/tpo2023BolleFran/fuentes/clientesYCentros.txt"));
            while ((line = clientCenterReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {//corresponde a centro de distribucion
                    int cDistribucion = Integer.parseInt(parts[0]);
                    int costToSend = Integer.parseInt(parts[1]);
                    int annualMaintenanceCost = Integer.parseInt(parts[2]);
                    Node node = graph.getNode(cDistribucion);//busco el centro de distribucion
                    if (node != null) {
                    	node.setnodeNumber(cDistribucion);
                        node.setCostToSend(costToSend);//asigno el costo para enviar al puerto
                        node.setAnnualMaintenanceCost(annualMaintenanceCost);//asigno costo de mantenimiento
                    }
                } else if (parts.length == 2) {//Corresponde a informacion de productores
                    int client = Integer.parseInt(parts[0]);
                    int annualProductionVolume = Integer.parseInt(parts[1]);
                    Node node = graph.getNode(client);
                    if (node != null) {
                        node.setnodeNumber(client);
                        node.setAnnualProductionVolume(annualProductionVolume);
                    }
                }
            }
            clientCenterReader.close();

//            // Ejemplo: obtener los vecinos del nodo 1 y su información adicional
//            List<Edge> neighborsOfNode1 = graph.getNeighbors(0);
//            System.out.print("Vecinos");
//            System.out.println();
//            for (Edge edge : neighborsOfNode1) {
//                System.out.println("Nodo: " + edge.getDestination() + ", Costo: " + edge.getCost());
//            }
//            Node node1 = graph.getNode(1);
//            if (node1 != null) {
//                System.out.println("Costo de Envío: " + node1.getCostToSend());
//                System.out.println("Costo Anual de Mantenimiento: " + node1.getAnnualMaintenanceCost());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//	        // Ciclo for para recorrer todos los nodos del grafo
//	        for (Node node : graph.getNodes()) {
//	            int nodeId = node.getnodeNumber();
//	
//	            // Imprimir información del nodo
//	            System.out.println("Nodo " + nodeId + ":");
//	            System.out.println("Vecinos:");
//	
//	            // Imprimir información de los vecinos del nodo
//	            List<Edge> neighbors = graph.getNeighbors(nodeId);
//	            for (Edge neighbor : neighbors) {
//	                int neighborId = neighbor.getDestination();
//	                int cost = neighbor.getCost();
//	                System.out.println("  Vecino: " + neighborId + ", Costo: " + cost);
//	            }
//	            if(nodeId<=7) {
//	            	// Imprimir información adicional del nodo
//	                System.out.println("  Costo de Envío al puerto: " + node.getCostToSend());
//	                System.out.println("  Costo Anual de Mantenimiento: " + node.getAnnualMaintenanceCost());
//	                System.out.println();  // Separador entre nodos
//	            }
//	            else {
//	            	System.out.println("  Volumen de Producción Anual: " + node.getAnnualProductionVolume());
//	                System.out.println();  // Separador entre nodos
//	            }
//	        }
        Dijkstra dijk = new Dijkstra();
        dijk.calculateShortestPaths(graph, 3);

    }
}

