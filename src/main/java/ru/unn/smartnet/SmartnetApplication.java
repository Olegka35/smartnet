package ru.unn.smartnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;
import ru.unn.smartnet.model.Element;
import ru.unn.smartnet.model.Net;
import ru.unn.smartnet.model.algorithms.BranchAndBounds;
import ru.unn.smartnet.model.algorithms.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SmartnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartnetApplication.class, args);

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

		Dijkstra dijkstra = new Dijkstra(net, new NetParam(2, "Расстояние", 400, PARAM_TYPE.INTEGER_TYPE));
		System.out.println(dijkstra.getShortestPath(spb, nn));

		List<Element> list = new ArrayList<>(Arrays.asList(moscow, perm, sochi));
		BranchAndBounds branchAndBounds = new BranchAndBounds(net, new NetParam(2, "Расстояние", 400, PARAM_TYPE.INTEGER_TYPE), list);
		branchAndBounds.start();
	}

}

