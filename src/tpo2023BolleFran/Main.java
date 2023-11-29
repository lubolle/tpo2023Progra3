package tpo2023BolleFran;
import java.io.*;

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

		} catch (IOException e) {
			e.printStackTrace();
		}

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
					//(cost de transporte al centro + costo del centro al puerto)*prod anual cliente
					//matCostos[i][j] = graph.getNode(7+j).getShortestPath(node.getnodeNumber());
					matCostos[i][j] = (graph.getNode(7+j).getShortestPath(node.getnodeNumber())+node.getCostToSend())*graph.getNode(7+j).getAnnualProductionVolume();

				}
			}
			i++;
		}

		//	    System.out.println("Hola");
		int resultado []=lcbb.leasCostBB(matCostos,matCostos.length);
		int costoFinal [] = costoFinalCliente.costoFinal(matCostos,resultado);
		for(int z = 0 ; z<costoFinal.length ; z++){
			System.out.println(costoFinal[z]);
		}
		//        

	}
}

