package ru.unn.smartnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.unn.smartnet.graph.NetParam;

import java.util.ArrayList;

@Getter
@Setter
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

    public void addParam(NetParam param) {
        this.params.add(param);
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
