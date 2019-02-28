package ru.unn.smartnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.unn.smartnet.graph.NetParam;

import java.util.ArrayList;

public class Element {
    private Integer id;

    @JsonIgnore
    private Integer netID;

    private ArrayList<NetParam> params;

    public Element(Integer id, Integer netID, ArrayList<NetParam> params) {
        this.id = id;
        this.netID = netID;
        this.params = params;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNetID() {
        return netID;
    }

    public void setNetID(Integer netID) {
        this.netID = netID;
    }

    public ArrayList<NetParam> getParams() {
        return params;
    }

    public void setParams(ArrayList<NetParam> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", netID=" + netID +
                ", params=" + params +
                '}';
    }
}
