package ru.unn.smartnet.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.unn.smartnet.model.Net;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NetInfoMapper implements RowMapper<Net> {
    @Override
    public Net mapRow(ResultSet resultSet, int i) throws SQLException {
        Net net = new Net();
        net.setId(resultSet.getInt("id"));
        net.setUserID(resultSet.getInt("user_id"));
        net.setType(resultSet.getInt("type"));
        net.setDate(resultSet.getDate("date"));
        net.setName(resultSet.getString("name"));
        return net;
    }
}
