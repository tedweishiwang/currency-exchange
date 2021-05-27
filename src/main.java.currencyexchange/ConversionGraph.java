package main.java.currencyexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ConversionGraph {
    Map<String, Node> allNodes;
    Map<Node, Map<Node, Double>> graph;

    public ConversionGraph() {
        allNodes = new HashMap<>();
        graph = new HashMap<>();
    }

    public void buildGraph(Map<String, Map<String, Double>> tickers) {
        for (String pair : tickers.keySet()) {
            String firstCurrency = pair.split("-")[0];
            String secondCurrency = pair.split("-")[1];

            Map<String, Double> edgeToChildren = tickers.get(pair);
            Double fromFtoS = edgeToChildren.get("bid");
            Double fromStoF = 1.0 / edgeToChildren.get("ask");

            if (!allNodes.containsKey(firstCurrency)) {
                allNodes.put(firstCurrency, new Node(firstCurrency));
            }

            if (!allNodes.containsKey(secondCurrency)) {
                allNodes.put(secondCurrency, new Node(secondCurrency));
            }

            if (!graph.containsKey(allNodes.get(firstCurrency))) {
                graph.put(allNodes.get(firstCurrency), new HashMap<>());
            }

            graph.get(allNodes.get(firstCurrency)).put(allNodes.get(secondCurrency), fromFtoS);

            if (!graph.containsKey(allNodes.get(secondCurrency))) {
                graph.put(allNodes.get(secondCurrency), new HashMap<>());
            }

            graph.get(allNodes.get(secondCurrency)).put(allNodes.get(firstCurrency), fromStoF);

        }
    }

    public void printGraph() {
        for(Node n : graph.keySet()) {
            System.out.print(n.currency + ": ");
            for (Node child : graph.get(n).keySet()) {
                System.out.print("(" + child.currency + ", " + graph.get(n).get(child) + ")");
            }
            System.out.println();
        }
    }

    public double maxConversion(String[] conversionPair) {
        String start = conversionPair[0];
        String goal = conversionPair[1];

        Set<String> visited = new HashSet<>();

        Queue<Node> q = new LinkedList<>();

        allNodes.get(start).maxConversion = 1;
        q.add(allNodes.get(start));

        while(!q.isEmpty()) {
            Node cur = q.poll();
            visited.add(cur.currency);

            List<Node> children = new ArrayList<>();

            for (Node child : graph.get(cur).keySet()) {
                children.add(child);
            }

            Collections.sort(children, (a, b) -> (int) (graph.get(cur).get(b) - graph.get(cur).get(a)));

            for (Node child : children) {
                if (visited.contains(child.currency)) continue;
                child.maxConversion = Math.max(child.maxConversion, cur.maxConversion * graph.get(cur).get(child));
                q.offer(child);
            }
        }

        return allNodes.get(goal).maxConversion;
    }
}
