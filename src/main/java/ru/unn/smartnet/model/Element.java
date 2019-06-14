package ru.unn.smartnet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.unn.smartnet.graph.NetParam;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element {
    private Integer id;

    @JsonIgnore
    private Integer netID;

    private ArrayList<NetParam> params;

    public void addParam(NetParam param) {
        this.params.add(param);
    }
}
