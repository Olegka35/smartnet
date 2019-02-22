package ru.unn.smartnet.model;

import ru.unn.smartnet.graph.Graph;

import java.util.Date;

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
}
