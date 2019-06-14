package ru.unn.smartnet.model;

import lombok.*;
import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Net {
    private Integer id;
    private String name;
    private Graph<Element> graph;
    private Integer userID;
    private Date date;
    private Integer type;

    public Set<Element> findAllVertices() {
        if(graph == null) return null;
        return graph.getAllVertices();
    }

    public Set<Element> findAdjacentVertices(Element element) {
        if(graph == null) return null;
        return this.graph.getAdjacentVertices(element);
    }

    public Element getElement(Integer id) {
        Set<Element> elements = findAllVertices();
        for(Element element: elements) {
            if(element.getId().equals(id))
                return element;
        }
        return null;
    }

    public void addConnection(Element el1, Element el2) {
        addConnection(el1, el2, false);
    }

    public void addConnection(Element el1, Element el2, boolean reverse) {
        if(reverse)
            this.graph.addDoubleEdge(el1, el2);
        else
            this.graph.addEdge(el1, el2);
    }

    public void removeConnection(Element el1, Element el2) {
        removeConnection(el1, el2, false);
    }

    public void removeConnection(Element el1, Element el2, boolean reverse) {
        if(reverse)
            this.graph.removeDoubleEdge(el1, el2);
        else
            this.graph.removeEdge(el1, el2);
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("name", name);
        result.put("userID", userID);
        result.put("graph", convertGraphToMap());
        result.put("date", date);
        result.put("type", type);
        return result;
    }

    private Map<String, Object> convertGraphToMap() {
        if(this.graph == null) return null;
        Map<String, Object> graph = new HashMap<>();

        List<Map<String, Object>> connections = new ArrayList<>();
        List<Map<String, Object>> vertices = new ArrayList<>();
        List<Map<String, Object>> params = new ArrayList<>();
        Set<Integer> paramsIDs = new HashSet<>();

        Set<Element> elements = findAllVertices();
        for(Element element: elements) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", element.getId());
            List<NetParam> elementParams = element.getParams();
            map.put("params", convertParamsToList(elementParams, params, paramsIDs));
            vertices.add(map);

            Set<Element> connectedElements = findAdjacentVertices(element);
            for(Element e2: connectedElements) {
                Map<String, Object> connection = new HashMap<>();
                connection.put("from", element.getId());
                connection.put("to", e2.getId());

                List<NetParam> paramList = this.graph.getParams(element, e2);
                connection.put("params", convertParamsToList(paramList, params, paramsIDs));

                if(this.graph.hasRelationship(e2, element) && this.graph.getParams(e2, element).equals(paramList))
                    connection.put("reverse", true);
                else
                    connection.put("reverse", false);
                connections.add(connection);
            }
        }

        graph.put("params", params);
        graph.put("vertices", vertices);
        graph.put("connections", connections);
        return graph;
    }

    private List<Map<String, Object>> convertParamsToList(List<NetParam> netParamList, List<Map<String, Object>> params, Set<Integer> usedIDs) {
        List<Map<String, Object>> result = new ArrayList<>();
        for(NetParam param: netParamList) {
            Integer paramID = param.getId();

            if(!usedIDs.contains(paramID)) {
                Map<String, Object> map = new HashMap<String, Object>() {{
                    put("id", paramID);
                    put("name", param.getName());
                    put("type", param.getType());
                }};
                params.add(map);
            }
            usedIDs.add(paramID);

            Map<String, Object> map = new HashMap<String, Object>() {{
                put("id", paramID);
                put("value", param.getValue());
            }};
            result.add(map);
        }
        return result;
    }

}
