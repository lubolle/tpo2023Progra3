package tpo2023BolleFranPackage;

public class Edge {
    private int destination;
    private int cost;
    
    public Edge(int destination, int cost) {
        this.destination = destination;
        this.cost = cost;
    }
    
    public int getDestination() {
        return destination;
    }
    
    public int getCost() {
        return cost;
    }
}
