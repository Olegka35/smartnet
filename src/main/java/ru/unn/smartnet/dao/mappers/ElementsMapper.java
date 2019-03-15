package ru.unn.smartnet.dao.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.model.Element;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ElementsMapper implements RowMapper<Element> {
    private Integer netID;

    public ElementsMapper(Integer netID) {
        this.netID = netID;
    }

    @Override
    public Element mapRow(ResultSet resultSet, int i) throws SQLException {
        Element element = new Element(resultSet.getInt("element_id"), netID, new ArrayList<NetParam>());
        return element;
    }
}


    /*@Override
    public List<Element> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Element> list = new ArrayList<>();
        while(resultSet.next()) {
            Element element = new Element(resultSet.getInt("element_id"), id, new ArrayList<NetParam>());
            list.add(element);
        }
        return list;
    }*/