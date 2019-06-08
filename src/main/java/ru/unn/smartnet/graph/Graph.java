package ru.unn.smartnet.graph;

import ru.unn.smartnet.model.Net;

import java.io.Serializable;
import java.util.*;

public class Graph<V> implements Serializable {
    private Map<V, List<Node<V>>> adjacencyList;
    private Set<V> vertices;
    private static final int DEFAULT_WEIGHT = Integer.MAX_VALUE;

    public Graph() {
        adjacencyList = new HashMap<>();
        vertices = new HashSet<>();
    }

    private static class Node<V> implements Serializable {
        private V name;
        private List<NetParam> params;

        public Node(V name, ArrayList<NetParam> params) {
            this.name = name;
            this.params = params;
        }

        public V getName() {
            return name;
        }

        public void setName(V name) {
            this.name = name;
        }

        public List<NetParam> getParams() {
            return params;
        }

        public void setParams(List<NetParam> params) {
            this.params = params;
        }
    }

    public Map<V, List<Node<V>>> getGraph() {
        return this.adjacencyList;
    }

    public boolean isEmpty() {
        return this.vertices.isEmpty();
    }

    private void addEdge(V src, Node<V> destNode) {
        List<Node<V>> adjacentVertices = adjacencyList.get(src);
        if(adjacentVertices == null || adjacentVertices.isEmpty()) {
            adjacentVertices = new ArrayList<Node<V>>();
            adjacentVertices.add(destNode);
        } else {
            adjacentVertices.add(destNode);
        }
        adjacencyList.put(src, adjacentVertices);
    }

    public void addEdge(V src, V dest, ArrayList<NetParam> params) {
        Objects.requireNonNull(src);
        Objects.requireNonNull(dest);

        this.addEdge(src, new Node<>(dest, params));

        this.vertices.add(src);
        this.vertices.add(dest);
    }

    public void addEdge(V src, V dest) {
        Objects.requireNonNull(src);
        Objects.requireNonNull(dest);

        this.addEdge(src, new Node<>(dest, new ArrayList<NetParam>()));

        this.vertices.add(src);
        this.vertices.add(dest);
    }

    public void addDoubleEdge(V src, V dest, ArrayList<NetParam> params) {
        addEdge(src, dest, params);
        addEdge(dest, src, params);
    }

    public void addDoubleEdge(V src, V dest) {
        addEdge(src, dest);
        addEdge(dest, src);
    }

    public void removeEdge(V src, V dest) {
        Objects.requireNonNull(src);
        Objects.requireNonNull(dest);

        List<Node<V>> adjacentVertices = adjacencyList.get(src);
        Node<V> removedNode = null;
        for(Node<V> node: adjacentVertices)
            if(node.name == dest) {
                removedNode = node;
                break;
            }

        if(removedNode != null)
            adjacentVertices.remove(removedNode);
    }

    public void removeDoubleEdge(V src, V dest) {
        removeEdge(src, dest);
        removeEdge(dest, src);
    }

    public void addVertice(V vert) {
        Objects.requireNonNull(vert);
        this.vertices.add(vert);
    }

    public boolean hasRelationship(V source, V destination) {
        if (source == null && destination == null)
            return true;
        if (source != null && destination == null)
            return true;
        if (source == null && destination != null)
            return false;

        List<Node<V>> nodes = null;

        if (adjacencyList.containsKey(source)) {
            nodes = adjacencyList.get(source);
            if (nodes != null && !nodes.isEmpty()) {
                for (Node<V> neighbors : nodes) {
                    if (neighbors.getName().equals(destination))
                        return true;
                }
            }
        }
        return false;
    }

    public List<NetParam> getParams(V src, V dest) {
        List<NetParam> params = null;
        if (this.hasRelationship(src, dest)) {
            List<Node<V>> adjacentNodes = this.adjacencyList.get(src);
            for (Node<V> node : adjacentNodes) {
                if (node.getName().equals(dest)) {
                    params = node.getParams();
                    break;
                }
            }
        }
        return params;
    }

    public Object getParameter(V src, V dest, NetParam requestParam) {
        return getParameter(src, dest, requestParam.getId());
    }

    public Object getParameter(V src, V dest, Integer requestParamID) {
        List<NetParam> paramList = getParams(src, dest);
        for(NetParam netParam: paramList) {
            if(netParam.getId().equals(requestParamID)) {
                return netParam.getValue();
            }
        }
        return null;
    }

    public Set<V> getAdjacentVertices(V vertex) {
        List<Node<V>> adjacentNodes = this.adjacencyList.get(vertex);
        Set<V> neighborVertex = new HashSet<>();

        if ((adjacentNodes != null) && !adjacentNodes.isEmpty()) {
            for (Node<V> v : adjacentNodes) {
                neighborVertex.add(v.getName());
            }
        }
        return neighborVertex;
    }

    public Set<V> getAllVertices() {
        return Collections.unmodifiableSet(this.vertices);
    }

    public boolean removeVertex(V vertex) {
        Objects.requireNonNull(vertex);

        if(!this.vertices.contains(vertex))
            return false;
        Iterator<Map.Entry<V, List<Node<V>>>> itr = this.adjacencyList.entrySet()
                .iterator();

        while (itr.hasNext()) {
            Map.Entry<V, List<Node<V>>> e = itr.next();
            List<Node<V>> vs = e.getValue();
            if (vertex.equals(e.getKey())) {
                itr.remove();
            }

            Iterator<Node<V>> listIterator = vs.iterator();
            while (listIterator.hasNext()) {
                Node<V> ver = listIterator.next();
                if (vertex.equals(ver.getName())) {
                    listIterator.remove();
                }

            }
        }

        Iterator<V> itrVertices = this.vertices.iterator();
        while (itrVertices.hasNext()) {
            if (vertex.equals(itrVertices.next())) {
                itrVertices.remove();
                break;
            }
        }
        return true;
    }

    public String toString() {
        int idx = 1;
        StringBuilder sb = new StringBuilder();
        //sb.append("Set of Edges :\n");
        for(V v: this.vertices) {
            sb.append(idx++ + ". " + v + '\n');
            List<Node<V>> neighbours = this.adjacencyList.get(v);
            if(neighbours == null) continue;
            for (Node<V> node : neighbours) {
                sb.append(v + " -- " + node.getParams() + " ---> "
                        + node.getName() + "\n");
            }
        }
        return sb.toString();
    }
}
