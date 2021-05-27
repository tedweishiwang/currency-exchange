package main.java.currencyexchange;

public class Node {

    double maxConversion;
    Node previousNode;
    String currency;

    public Node(String currency) {
        this.currency = currency;
        maxConversion = -1;
        previousNode = null;
    }
}
