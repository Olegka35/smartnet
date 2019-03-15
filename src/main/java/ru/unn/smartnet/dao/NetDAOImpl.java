package ru.unn.smartnet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.unn.smartnet.dao.mappers.*;
import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;
import ru.unn.smartnet.model.Element;
import ru.unn.smartnet.model.Net;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class NetDAOImpl implements NetDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Net> getNetList() {
        String SQL = "select * from nets";
        List<Net> nets = jdbcTemplate.query(SQL, new NetInfoMapper());
        return nets;
    }

    @Override
    public Net getNet(Integer id) {
        String SQL_GET_NET_INFO = "SELECT * FROM nets WHERE id = ?";
        String SQL_GET_ELEMENTS = "SELECT * FROM elements WHERE net_id = ?";
        String SQL_GET_CONNECTIONS = "SELECT * FROM connections WHERE net_id = ?";
        String SQL_GET_ATTRIBUTES = "SELECT * FROM attributes WHERE id IN (SELECT attr_id FROM element_params UNION SELECT attr_id FROM connections_params WHERE net_id = ?)";
        String SQL_GET_ELEMENT_PARAMS = "SELECT * FROM element_params WHERE net_id = ?";
        String SQL_GET_CONNECTIONS_PARAMS = "SELECT * FROM connections_params WHERE net_id = ?";

        Net net = jdbcTemplate.queryForObject(SQL_GET_NET_INFO, new Object[]{id}, new NetInfoMapper());
        List<Element> elements = jdbcTemplate.query(SQL_GET_ELEMENTS, new Object[]{id}, new ElementsMapper(id));
        List<Map<String, Object>> connections = jdbcTemplate.queryForList(SQL_GET_CONNECTIONS, id);
        List<Map<String, Object>> attributes = jdbcTemplate.queryForList(SQL_GET_ATTRIBUTES, id);
        List<Map<String, Object>> elementParams = jdbcTemplate.queryForList(SQL_GET_ELEMENT_PARAMS, id);
        List<Map<String, Object>> connectionParams = jdbcTemplate.queryForList(SQL_GET_CONNECTIONS_PARAMS, id);

        for(Map<String, Object> map: elementParams) {
            Element element = getElementByID(elements, (Integer) map.get("element_id"));
            if(element == null) continue;
            Integer attr_id = (Integer) map.get("attr_id");
            Map<String, Object> attr = getAttributeByID(attributes, attr_id);

            element.addParam(new NetParam(attr_id, attr.getOrDefault("name", "").toString(), map.get("value"),
                    PARAM_TYPE.getParamTypeFromIndex((Integer)attr.getOrDefault("type", 1))));
        }
        for(Map<String, Object> map: connectionParams) {
            Map<String, Object> connection = getConnectionByElements(connections, (Integer)map.get("from"), (Integer)map.get("to"));
            if(connection == null) continue;
            Integer attr_id = (Integer) map.get("attr_id");
            Map<String, Object> attr = getAttributeByID(attributes, attr_id);

            if(!connection.containsKey("params")) connection.put("params", new ArrayList<NetParam>());
            ((ArrayList<NetParam>)connection.get("params")).add(new NetParam(attr_id, attr.getOrDefault("name", "").toString(), map.get("value"),
                    PARAM_TYPE.getParamTypeFromIndex((Integer)attr.getOrDefault("type", 1))));
        }
        Graph<Element> graph = new Graph<>();
        for(Element element: elements) graph.addVertice(element);
        for(Map<String, Object> connection: connections) {
            Element fromElement = getElementByID(elements, (Integer)connection.get("from_element"));
            Element toElement = getElementByID(elements, (Integer)connection.get("to_element"));
            ArrayList<NetParam> params = (ArrayList<NetParam>)connection.get("params");

            if(!((Boolean) connection.get("reversed")))
                graph.addEdge(fromElement, toElement, params);
            else
                graph.addDoubleEdge(fromElement, toElement, params);
        }
        net.setGraph(graph);
        return net;
    }

    private static Map<String, Object> getAttributeByID(List<Map<String, Object>> attributes, Integer id) {
        for(Map<String, Object> map: attributes) {
            if(map.getOrDefault("id", -1) == id)
                return map;
        }
        return null;
    }

    private static Element getElementByID(List<Element> elements, Integer id) {
        for(Element element: elements) {
            if(element.getId().equals(id))
                return element;
        }
        return null;
    }

    private static Map<String, Object> getConnectionByElements(List<Map<String, Object>> connections, Integer from, Integer to) {
        for(Map<String, Object> connection: connections) {
            if(((Integer) connection.get("from_element")).equals(from) && ((Integer) connection.get("to_element")).equals(to))
                return connection;
        }
        return null;
    }
}


/*
Создание:

INSERT INTO nets ("UserID", type, date, name) VALUES (1, 1, NOW(), 'Города России');

INSERT INTO elements (net_id, element_id) VALUES (1, 1);
INSERT INTO elements (net_id, element_id) VALUES (1, 2);
INSERT INTO elements (net_id, element_id) VALUES (1, 3);
INSERT INTO elements (net_id, element_id) VALUES (1, 4);
INSERT INTO elements (net_id, element_id) VALUES (1, 5);
INSERT INTO elements (net_id, element_id) VALUES (1, 6);
INSERT INTO elements (net_id, element_id) VALUES (1, 7);
INSERT INTO elements (net_id, element_id) VALUES (1, 8);
INSERT INTO elements (net_id, element_id) VALUES (1, 9);
INSERT INTO elements (net_id, element_id) VALUES (1, 10);

INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 3, 1, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 2, 3, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 4, 1, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 4, 2, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 4, 3, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 5, 2, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 5, 4, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 3, 6, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 6, 8, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 8, 7, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 6, 7, false);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 9, 7, true);
INSERT INTO connections (net_id, from_element, to_element, reversed) VALUES (1, 1, 10, true);

INSERT INTO attributes (type, name) VALUES (1, 'Название');
INSERT INTO attributes (type, name) VALUES (2, 'Расстояние');

INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 1, 1, 'Москва');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 2, 1, 'Санкт-Петербург');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 3, 1, 'Нижний Новгород');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 4, 1, 'Вологда');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 5, 1, 'Мурманск');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 6, 1, 'Казань');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 7, 1, 'Екатеринбург');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 8, 1, 'Пермь');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 9, 1, 'Новосибирск');
INSERT INTO element_params (net_id, element_id, attr_id, value) VALUES (1, 10, 1, 'Сочи');

INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 3, 1, 2, 400);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 2, 3, 2, 640);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 4, 1, 2, 410);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 4, 2, 2, 550);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 4, 3, 2, 405);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 5, 2, 2, 1020);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 5, 4, 2, 1150);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 3, 6, 2, 320);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 6, 8, 2, 500);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 8, 7, 2, 290);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 6, 7, 2, 720);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 9, 7, 2, 1410);
INSERT INTO connections_params (net_id, "from", "to", attr_id, value) VALUES (1, 1, 10, 2, 1370);



 */