package tpo2023BolleFran;
import java.util.*;

public class Dijkstra {
    public static void calculateShortestPaths(Graph graph, int source) {    	
        Map<Integer, Integer> distances = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::getDistance));

        // Inicializa las distancias a infinito, excepto para el nodo fuente
        for (Node node : graph.getNodes()) {
            distances.put(node.getnodeNumber(), Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        pq.add(new NodeDistance(source, 0));

        while (!pq.isEmpty()) {
            NodeDistance nd = pq.poll();
            int current = nd.getNode();
            int distance = nd.getDistance();

            if (distance > distances.get(current)) {
                continue;  // Ignora nodos ya procesados con distancias más cortas
            }
            
            Node currentNode = graph.getNode(current);
            if(currentNode.getnodeNumber()>=0 && currentNode.getnodeNumber()<=7)//Es solo para guardar la distancia a los centros
            	currentNode.setShortestPath(source, distance);
            
            for (Edge neighbor : graph.getNeighbors(current)) {
                int neighborNode = neighbor.getDestination();
                int newDistance = distance + neighbor.getCost();

                if (newDistance < distances.get(neighborNode)) {
                    distances.put(neighborNode, newDistance);
                    pq.add(new NodeDistance(neighborNode, newDistance));
                }
            }
        }

        // Aquí, distances contiene las distancias mínimas desde el nodo fuente hacia todos los demás nodos
        // Descomentar esta linea si se quiere ver los caminos cortos de un nodo especifico.
        System.out.println("Distancias mínimas desde el nodo " + source + ":");
        for (Map.Entry<Integer, Integer> entry : distances.entrySet()) {
            System.out.println("Nodo " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
