package ru.unn.smartnet.model;

import lombok.Getter;
import lombok.Setter;
import ru.unn.smartnet.graph.NetParam;

import java.util.*;


@Getter
@Setter
public class UpdateNetObject {
    private Integer id;
    private String name;
    private Integer type;
    private UpdateElement elements;
    private UpdateConnection connections;

    private class UpdateElement {
        private List<Element> add;
        private List<Integer> remove;
    }

    private class UpdateConnection {
        private List<Connection> add;
        private List<Connection> remove;
    }

    @Getter
    public class Connection {
        private Integer from;
        private Integer to;
        private List<Map<String, Object>> params;
        private Boolean reverse = false;
    }

    public boolean validate(Net net) {
        Set<Element> currentElements = net.getVertices();
        Set<Integer> elementIDs = new HashSet<>();
        for(Element element: currentElements)
            elementIDs.add(element.getId());
        for(Integer elementID: this.elements.remove)
            elementIDs.remove(elementID);

        // Если узел с данным ID уже есть в сети - false
        for(Element element: this.elements.add) {
            if(elementIDs.contains(element.getId()))
                return false;
        }
        for(Connection connection: this.connections.remove) {
            // Если в сети нет узлов - false
            if(!elementIDs.contains(connection.from) || !elementIDs.contains(connection.to))
                return false;

            Element fromElement = net.getElement(connection.from),
                    toElement = net.getElement(connection.to);

            // Если в сети нет удаляемого соединения - false
            if(!net.getAdjacentVertices(fromElement).contains(toElement))
                return false;

            net.removeConnection(fromElement, toElement, connection.reverse);
        }

        for(Connection connection: this.connections.add) {
            // Если в сети нет узлов - faslse
            if(!elementIDs.contains(connection.from) || !elementIDs.contains(connection.to))
                return false;

            Element fromElement = net.getElement(connection.from),
                    toElement = net.getElement(connection.to);

            // Если соединение уже есть - false
            if(net.getAdjacentVertices(fromElement).contains(toElement))
                return false;

            net.addConnection(fromElement, toElement, connection.reverse);
        }
        return true;
    }

    public List<Element> getAddVertices() {
        return this.elements.add;
    }

    public List<Connection> getAddConnections() {
        return this.connections.add;
    }

    public List<Integer> getRemoveVerticesIDs() {
        return this.elements.remove;
    }

    public List<Connection> getRemoveConnections() {
        return this.connections.remove;
    }

    public List<Map<String, Object>> getElementParams() {
        List<Map<String, Object>> list = new ArrayList<>();
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
        for(Connection connection: connections.add) {
            List<Map<String, Object>> params = connection.params;
            for(Map<String, Object> param: params) {
                param.put("from", connection.from);
                param.put("to", connection.to);
                list.add(param);
            }
        }
        return list;
    }
}
