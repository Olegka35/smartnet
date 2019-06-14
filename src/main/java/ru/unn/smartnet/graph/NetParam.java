package ru.unn.smartnet.graph;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetParam {
    private Integer id;
    private String name;
    private Object value;
    private PARAM_TYPE type;

    @Override
    public String toString() {
        return "{ '" + name + "' = " + value + " }";
    }
}
