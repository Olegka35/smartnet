package ru.unn.smartnet.graph;

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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PARAM_TYPE getType() {
        return type;
    }

    public void setType(PARAM_TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{ '" + name + "' = " + value + " }";
    }
}
