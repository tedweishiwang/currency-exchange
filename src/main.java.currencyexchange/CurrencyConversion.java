package main.java.currencyexchange;

import java.util.Map;

public class CurrencyConversion {

    public static void main(String[] args) {
        Map tickers = Map.of(
            "BTC-USD", Map.of("ask", 1000.0, "bid", 990.0),
            "BTC-EUR", Map.of("ask", 1200.0, "bid", 1150.0),
            "ETH-USD", Map.of("ask", 200.0, "bid", 180.0),
            "ETH-EUR", Map.of("ask", 220.0, "bid", 210.0),
            "BTC-ETH", Map.of("ask", 5.6, "bid", 5.5)
        );

        ConversionGraph conversionGraph = new ConversionGraph();
        conversionGraph.buildGraph(tickers);
        conversionGraph.printGraph();
        System.out.println(conversionGraph.maxConversion(new String[]{"USD", "EUR"}));
    }
}

