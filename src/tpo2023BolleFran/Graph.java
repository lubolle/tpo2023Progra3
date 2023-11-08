package tpo2023BolleFran;
import java.util.*;
import java.util.List;

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
}

class Node {
    private List<Edge> edges;
    private int costToSend;  // Nuevo atributo para el costo de envío
    private int annualMaintenanceCost;  // Nuevo atributo para el costo anual de mantenimiento
    private int clientNumber;  // Nuevo atributo para el número de cliente
    private int annualProductionVolume;  // Nuevo atributo para el volumen de producción anual

    public Node() {
        edges = new ArrayList<>();
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

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setAnnualProductionVolume(int annualProductionVolume) {
        this.annualProductionVolume = annualProductionVolume;
    }

    public int getAnnualProductionVolume() {
        return annualProductionVolume;
    }
}
