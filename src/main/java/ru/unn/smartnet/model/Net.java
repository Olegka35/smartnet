package ru.unn.smartnet.model;

import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;

import java.util.*;

public class Net {
    private Integer id;
    private String name;
    private Graph<Element> graph;
    private Integer userID;
    private Date date;
    private Integer type;

    public Net() {
    }

    public Net(Integer id, String name, Graph<Element> graph, Integer userID, Date date, Integer type) {
        this.id = id;
        this.name = name;
        this.graph = graph;
        this.userID = userID;
        this.date = date;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Graph<Element> getGraph() {
        return graph;
    }

    public void setGraph(Graph<Element> graph) {
        this.graph = graph;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Element getElement(Integer id) {
        Set<Element> elements = graph.getAllVertices();
        for(Element element: elements) {
            if(element.getId().equals(id))
                return element;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Net{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", graph=" + graph +
                ", userID=" + userID +
                ", date=" + date +
                ", type=" + type +
                '}';
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
        Map<String, Object> graph = new HashMap<>();

        List<Map<String, Object>> connections = new ArrayList<>();
        List<Map<String, Object>> vertices = new ArrayList<>();
        List<Map<String, Object>> params = new ArrayList<>();
        Set<Integer> paramsIDs = new HashSet<>();

        Set<Element> elements = this.graph.getAllVertices();
        for(Element element: elements) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", element.getId());
            List<NetParam> elementParams = element.getParams();
            map.put("params", convertParamsToList(elementParams, params, paramsIDs));
            vertices.add(map);

            List<Element> connectedElements = this.graph.getAdjacentVertices(element);
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
