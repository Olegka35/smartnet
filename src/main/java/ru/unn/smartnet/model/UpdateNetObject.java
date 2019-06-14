package ru.unn.smartnet.model;

import lombok.*;
import ru.unn.smartnet.graph.NetParam;

import java.util.*;


@Data
public class UpdateNetObject {
    private Integer id;
    private String name;
    private Integer type;
    private UpdateElement elements;
    private UpdateConnection connections;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UpdateElement {
        private List<Element> add;
        private List<Integer> remove;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UpdateConnection {
        private List<Connection> add;
        private List<Connection> remove;
    }

    public boolean validate(Net net) {
        Set<Element> currentElements = net.findAllVertices();
        Set<Integer> elementIDs = new HashSet<>();
        for (Element element : currentElements)
            elementIDs.add(element.getId());

        if(this.elements != null) {
            if(this.elements.remove != null) {
                for (Integer elementID : this.elements.remove)
                    elementIDs.remove(elementID);
            }

            // Если узел с данным ID уже есть в сети - false
            if(this.elements.add != null) {
                for (Element element : this.elements.add) {
                    if (elementIDs.contains(element.getId()))
                        return false;
                }
            }
        }

        if(this.connections != null) {
            if(this.connections.remove != null) {
                for (Connection connection : this.connections.remove) {
                    // Если в сети нет узлов - false
                    if (!elementIDs.contains(connection.getFrom()) || !elementIDs.contains(connection.getTo()))
                        return false;

                    Element fromElement = net.getElement(connection.getFrom()),
                            toElement = net.getElement(connection.getTo());

                    // Если в сети нет удаляемого соединения - false
                    if (!net.findAdjacentVertices(fromElement).contains(toElement))
                        return false;

                    net.removeConnection(fromElement, toElement, connection.getReverse());
                }
            }

            if(this.connections.add != null) {
                for (Connection connection : this.connections.add) {
                    // Если в сети нет узлов - faslse
                    if (!elementIDs.contains(connection.getFrom()) || !elementIDs.contains(connection.getTo()))
                        return false;

                    Element fromElement = net.getElement(connection.getFrom()),
                            toElement = net.getElement(connection.getTo());

                    // Если соединение уже есть - false
                    if (net.findAdjacentVertices(fromElement).contains(toElement))
                        return false;

                    net.addConnection(fromElement, toElement, connection.getReverse());
                }
            }
        }
        return true;
    }

    public List<Element> getAddVertices() {
        if(this.elements == null || this.elements.add == null) return new ArrayList<Element>();
        return this.elements.add;
    }

    public List<Connection> getAddConnections() {
        if(this.connections == null || this.connections.add == null) return new ArrayList<Connection>();
        return this.connections.add;
    }

    public List<Integer> getRemoveVerticesIDs() {
        if(this.elements == null || this.elements.remove == null) return new ArrayList<Integer>();
        return this.elements.remove;
    }

    public List<Connection> getRemoveConnections() {
        if(this.connections == null || this.connections.remove == null) return new ArrayList<Connection>();
        return this.connections.remove;
    }

    public List<Map<String, Object>> getElementParams() {
        List<Map<String, Object>> list = new ArrayList<>();
        if(elements == null || elements.add == null) return list;
        for(Element element: elements.add) {
            List<NetParam> params = element.getParams();
            for(NetParam param: params) {
                Map<String, Object> map = new HashMap<>();
                map.put("element_id", element.getId());
                map.put("id", param.getId());
                map.put("value", param.getValue());
                list.add(map);
            }
        }
        return list;
    }

    public List<Map<String, Object>> getConnectionParams() {
        List<Map<String, Object>> list = new ArrayList<>();
        if(connections == null || connections.add == null) return list;
        for(Connection connection: connections.add) {
            List<Map<String, Object>> params = connection.getParams();
            for(Map<String, Object> param: params) {
                param.put("from", connection.getFrom());
                param.put("to", connection.getTo());
                list.add(param);
            }
        }
        return list;
    }
}
