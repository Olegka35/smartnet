package ru.unn.smartnet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.unn.smartnet.dao.NetDAO;
import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;
import ru.unn.smartnet.model.AddNetObject;
import ru.unn.smartnet.model.Element;
import ru.unn.smartnet.model.Net;
import ru.unn.smartnet.model.UpdateNetObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NetServiceImpl implements NetService {
    @Autowired
    private NetDAO netDAO;

    @Override
    public Net getNet(Integer id) {
        Net net = netDAO.getNet(id);
        return net;
    }

    @Override
    public Net getNet2(Integer id) {
        Graph<Element> testGraph = new Graph<Element>();
        Element moscow = new Element(501, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Москва", PARAM_TYPE.STRING_TYPE)); }});
        Element spb = new Element(502, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Санкт-Петербург", PARAM_TYPE.STRING_TYPE)); }});
        Element nn = new Element(503, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Нижний Новгород", PARAM_TYPE.STRING_TYPE)); }});
        Element vologda = new Element(504, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Вологда", PARAM_TYPE.STRING_TYPE)); }});
        Element murmansk = new Element(505, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Мурманск", PARAM_TYPE.STRING_TYPE)); }});
        Element kazan = new Element(506, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Казань", PARAM_TYPE.STRING_TYPE)); }});
        Element ekb = new Element(507, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Екатеринбург", PARAM_TYPE.STRING_TYPE)); }});
        Element perm = new Element(508, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Пермь", PARAM_TYPE.STRING_TYPE)); }});
        Element novosib = new Element(509, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Новосибирск", PARAM_TYPE.STRING_TYPE)); }});
        Element sochi = new Element(510, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Сочи", PARAM_TYPE.STRING_TYPE)); }});

        testGraph.addVertice(moscow);
        testGraph.addVertice(spb);
        testGraph.addVertice(nn);
        testGraph.addVertice(vologda);
        testGraph.addVertice(murmansk);
        testGraph.addVertice(kazan);
        testGraph.addVertice(ekb);
        testGraph.addVertice(perm);
        testGraph.addVertice(novosib);
        testGraph.addVertice(sochi);

        testGraph.addDoubleEdge(nn, moscow, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 400, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(spb, moscow, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 640, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(vologda, moscow, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 410, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(vologda, spb, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 550, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(vologda, nn, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 405, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(murmansk, spb, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 1020, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(murmansk, vologda, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 1150, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(nn, kazan, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 320, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(kazan, perm, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 500, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(perm, ekb, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 290, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addEdge(kazan, ekb, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 720, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(novosib, ekb, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 1410, PARAM_TYPE.INTEGER_TYPE)); }});
        testGraph.addDoubleEdge(moscow, sochi, new ArrayList<NetParam>() {{ add(new NetParam(2, "Расстояние", 1370, PARAM_TYPE.INTEGER_TYPE)); }});

        Net net = new Net();
        net.setId(11111);
        net.setName("Города России");
        net.setDate(new Date());
        net.setGraph(testGraph);
        net.setType(1);
        return net;
    }

    @Override
    public List<Net> getNetList() {
        return netDAO.getNetList();
    }

    @Override
    public void createNet(AddNetObject net) {
        netDAO.addNet(net);
    }

    @Override
    public void updateNet(UpdateNetObject updates) {
        Net net = netDAO.getNet(updates.getId());
        if(!updates.validate(net)) {
            // Бросить ошибку
            return;
        }
        Integer netID = net.getId();
        if(updates.getName() != null) {
            netDAO.setNetName(netID, updates.getName());
        }
        if(updates.getType() != null) {
            netDAO.setNetType(netID, updates.getType());
        }
        if(updates.getElements() != null || updates.getConnections() != null) {
            netDAO.updateNetData(netID, updates);
        }
    }

    @Override
    public void deleteNet(Integer netID) {
        netDAO.deleteNet(netID);
    }
}

/*
   elements: {
        add: {
            "id": 1,
			"params": [
				{
					"id": 1,
					"value": "Москва"
				}
			]
        }
        remove: {
            id: []
        }

   }
 */