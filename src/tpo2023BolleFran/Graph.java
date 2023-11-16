package tpo2023BolleFran;
import java.util.*;

public class Graph {
    private Map<Integer, Node> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addEdge(int source, int destination, int cost) {
        nodes.computeIfAbsent(source, k -> new Node()).addEdge(destination, cost);
    }

    public List<Edge> getNeighbors(int node) {
        Node n = nodes.get(node);
        if (n != null) {
            return n.getEdges();
        }
        return Collections.emptyList();
    }

    public Node getNode(int node) {
        return nodes.get(node);
    }


    public Collection<Node> getNodes() {
        return nodes.values();
    }

}

class NodeDistance {
//	Se ha introducido una nueva clase NodeDistance que se utiliza para almacenar la información del nodo y su distancia. Esto se utiliza en la cola de prioridad durante la ejecución del algoritmo de Dijkstra.
    private int node;
    private int distance;

    public NodeDistance(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    public int getNode() {
        return node;
    }

    public int getDistance() {
        return distance;
    }
    
    
}

class Node {
    private List<Edge> edges;
    private int costToSend;  // Atributo para el costo de envío
    private int annualMaintenanceCost;  // Atributo para el costo anual de mantenimiento
    private int nodeNumber;  // Atributo para el número de cliente o numero de centro
    private int annualProductionVolume;  // Atributo para el volumen de producción anual
    
    //Atributos para el camino minimo
    private int[] shortestPaths;//Un array para almacenar la longitud del camino mínimo desde este nodo hasta cada otro nodo.
    private int[] totalCosts;//Un array para almacenar el costo total del camino mínimo desde este nodo hasta cada otro nodo.

    public Node() {
        edges = new ArrayList<>();
        initializeShortestPaths(0); // Inicializar el array shortestPaths con longitud 0

    }

    public void addEdge(int destination, int cost) {
        edges.add(new Edge(destination, cost));
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setCostToSend(int costToSend) {
        this.costToSend = costToSend;
    }

    public int getCostToSend() {
        return costToSend;
    }

    public void setAnnualMaintenanceCost(int annualMaintenanceCost) {
        this.annualMaintenanceCost = annualMaintenanceCost;
    }

    public int getAnnualMaintenanceCost() {
        return annualMaintenanceCost;
    }

    public void setnodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public int getnodeNumber() {
        return nodeNumber;
    }

    public void setAnnualProductionVolume(int annualProductionVolume) {
        this.annualProductionVolume = annualProductionVolume;
    }

    public int getAnnualProductionVolume() {
        return annualProductionVolume;
    }
    
    //Metodos para caminos cortos
    public void initializeShortestPaths(int numNodes) {
        shortestPaths = new int[numNodes];//Ojo aca por que estoy inicializando 57 espacios y solo quiero la distancia a los 7 centros de distribucion
        totalCosts = new int[numNodes];
        for (int i = 0; i < numNodes; i++) {
            shortestPaths[i] = Integer.MAX_VALUE;
            totalCosts[i] = 0;
        }
    }

}
