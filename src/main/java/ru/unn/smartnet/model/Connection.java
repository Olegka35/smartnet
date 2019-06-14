package ru.unn.smartnet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Connection {
    private Integer from;
    private Integer to;
    private List<Map<String, Object>> params;
    private Boolean reverse = false;
}
