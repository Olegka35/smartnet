package ru.unn.smartnet.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class AddNetObject {
    private String name;
    private Integer type;
    private List<Map<String, Object>> params;
    private List<Map<String, Object>> elements;
    private List<Map<String, Object>> connections;

    public List<Map<String, Object>> getElementParams() {
        List<Map<String, Object>> list = new ArrayList<>();
        for(Map<String, Object> map: elements) {
            List<Map<String, Object>> params = (List<Map<String, Object>>)map.get("params");
            for(Map<String, Object> param: params) {
                param.put("element_id", (Integer)map.get("id"));
                list.add(param);
            }
        }
        return list;
    }

    public List<Map<String, Object>> getConnectionParams() {
        List<Map<String, Object>> list = new ArrayList<>();
        for(Map<String, Object> map: connections) {
            List<Map<String, Object>> params = (List<Map<String, Object>>)map.get("params");
            for(Map<String, Object> param: params) {
                param.put("from", (Integer)map.get("from"));
                param.put("to", (Integer)map.get("to"));
                list.add(param);
            }
        }
        return list;
    }

    public boolean validate() {
        try {
            if(params.isEmpty()) return false;
            if(elements.isEmpty()) return false;
            if(connections.isEmpty()) return false;
            if(name == null || type == null) return false;
            Set<Integer> paramsID = new HashSet<>();
            Set<Integer> elementsID = new HashSet<>();
            Set<Integer> elementParamsID = new HashSet<>();
            Set<Integer> connectionParamsID = new HashSet<>();
            for(Map<String, Object> map: params) {
                if(!map.containsKey("id") || !map.containsKey("name") || !map.containsKey("type")) return false;
                Integer id = (Integer) map.get("id");
                if(paramsID.contains(id)) return false;
                paramsID.add(id);
            }
            for(Map<String, Object> map: elements) {
                if(!map.containsKey("id") || !map.containsKey("params")) return false;
                Integer id = (Integer) map.get("id");
                if(elementsID.contains(id)) return false;
                elementsID.add(id);
                List<Map<String, Object>> params = (ArrayList<Map<String, Object>>)map.get("params");

                Set<Integer> thisElementParamsIDs = new HashSet<>();
                for(Map<String, Object> param: params) {
                    if(!param.containsKey("id") || !param.containsKey("value")) return false;
                    Integer paramID = (Integer) param.get("id");
                    if(!paramsID.contains(paramID)) return false;
                    thisElementParamsIDs.add(paramID);
                }
                if(elementParamsID.isEmpty()) elementParamsID.addAll(thisElementParamsIDs);
                else if(!elementParamsID.equals(thisElementParamsIDs)) return false;
            }
            for(Map<String, Object> map: connections) {
                if(!map.containsKey("from") || !map.containsKey("to") || !map.containsKey("params")) return false;
                Integer from = (Integer) map.get("from");
                Integer to = (Integer) map.get("to");
                if(!elementsID.contains(from)) return false;
                if(!elementsID.contains(to)) return false;
                List<Map<String, Object>> params = (ArrayList<Map<String, Object>>)map.get("params");

                Set<Integer> thisConnectionParamsIDs = new HashSet<>();
                for(Map<String, Object> param: params) {
                    if(!param.containsKey("id") || !param.containsKey("value")) return false;
                    Integer paramID = (Integer) param.get("id");
                    if(!paramsID.contains(paramID)) return false;
                    thisConnectionParamsIDs.add(paramID);
                }
                if(connectionParamsID.isEmpty()) connectionParamsID.addAll(thisConnectionParamsIDs);
                else if(!connectionParamsID.equals(thisConnectionParamsIDs)) return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "AddNetObject{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", params=" + params +
                ", elements=" + elements +
                ", connections=" + connections +
                '}';
    }
}
