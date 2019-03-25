package ru.unn.smartnet.model.algorithms;

import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;
import ru.unn.smartnet.model.Element;
import ru.unn.smartnet.model.Net;

import java.util.*;

public class Dijkstra {
    private Net net;
    private NetParam param;
    public List<Element> elements;
    private Map<Element, Double> valueMap;
    private Set<Element> fixedElements;

    public Dijkstra(Net net, NetParam param) {
        this.net = net;
        this.param = param;
        valueMap = new HashMap<>();
    }

    public Map<String, Object> getShortestPath(Integer id1, Integer id2) {
        Element e1 = net.getElement(id1);
        Element e2 = net.getElement(id2);
        if(e1 == null || e2 == null) return null;
        return getShortestPath(e1, e2);
    }

    public Map<String, Object> getShortestPath(Element e1, Element e2) {
        if (this.param.getType() == PARAM_TYPE.STRING_TYPE) return null;
        valueMap.clear();
        Map<Element, Element> previousElementMap = new HashMap<>();
        fixedElements = new HashSet<>();
        elements = new ArrayList<>();
        Graph<Element> graph = net.getGraph();

        Element currentElement = e1;
        valueMap.put(e1, 0.0);
        previousElementMap.put(e1, null);
        while (!currentElement.equals(e2)) {
            List<Element> connectedElements = graph.getAdjacentVertices(currentElement);
            for (Element element : connectedElements) {
                if (fixedElements.contains(element)) continue;
                Double currValue = valueMap.get(element);

                if (currValue == null) {
                    valueMap.put(element, valueMap.get(currentElement) + Double.valueOf(graph.getParameter(currentElement, element, param).toString()));
                    previousElementMap.put(element, currentElement);
                } else {
                    Double newValue = valueMap.get(currentElement) + Double.valueOf(graph.getParameter(currentElement, element, param).toString());
                    if (newValue < currValue) {
                        valueMap.put(element, newValue);
                        previousElementMap.put(element, currentElement);
                    }
                }
            }
            fixedElements.add(currentElement);
            currentElement = findNextElement();
            if(currentElement == null) return null;
        }
        for(Element e = e2; e != null; e = previousElementMap.get(e)) {
            elements.add(e);
            if(e.equals(e1)) break;
        }
        Collections.reverse(elements);

        Map<String, Object> result = new HashMap<>();
        result.put("path", elements);
        result.put("min_distance", valueMap.get(e2));
        return result;
    }

    private Element findNextElement() {
        Double minVal = Double.MAX_VALUE;
        Element minElement = null;
        for(Element element: valueMap.keySet()) {
            if (fixedElements.contains(element)) continue;
            if(valueMap.get(element) < minVal) {
                minVal = valueMap.get(element);
                minElement = element;
            }
        }
        return minElement;
    }
}
