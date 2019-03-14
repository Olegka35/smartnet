package ru.unn.smartnet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.unn.smartnet.model.Net;
import org.springframework.jdbc.core.JdbcTemplate;

public class NetDAOImpl implements NetDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Net getNet(Integer id) {
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