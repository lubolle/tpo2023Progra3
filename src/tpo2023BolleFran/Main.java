package tpo2023BolleFran;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        int cantProducers = 0;
        int cantDistributor = 0;
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
                	cantDistributor ++;
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
                	cantProducers ++;
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
        
//        //Esto es para inicializar el array que guarda los caminos minimos
    	for (Node node : graph.getNodes()) {
            node.initializeShortestPaths(graph.getNodes().size());
            
        }
      
        // Esto es para calcular solamente el camino minimo en los nodos productores 
        int i = 0;
        int matCostos [][] = new int [cantDistributor][cantProducers+1];//Aca voy a guardar los costos de envio y costos fijos
        for (Node node : graph.getNodes()) {//Ciclo que recorre todos los nodos
        	if(node.getAnnualMaintenanceCost()!=0)//nodo productor
        		matCostos[node.getnodeNumber()][0]=node.getAnnualMaintenanceCost();
        	
        	
        	if(node.getnodeNumber() <= 7) {
        		Dijkstra.calculateShortestPaths(graph, i);
                for (int j = 1; j <= cantProducers; j++) {
                    matCostos[i][j] = graph.getNode(7+j).getShortestPath(node.getnodeNumber());
                }
        		}
        	i++;
	      }
        
//        //Primero vamos bien groncho a generar la matriz
        for(i=0; i<=cantDistributor;i++) {
        	for(int j=0; j<=cantProducers;j++) {
        		if(j==0) {

        		}
        	}
        }

    }
}

