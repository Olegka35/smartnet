package ru.unn.smartnet.graph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NetParam {
    private Integer id;
    private String name;
    private Object value;
    private PARAM_TYPE type;

    public NetParam(Integer id, String name, Object value, PARAM_TYPE type) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return "{ '" + name + "' = " + value + " }";
    }
}
