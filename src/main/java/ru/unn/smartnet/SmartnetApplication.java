package ru.unn.smartnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;
import ru.unn.smartnet.model.Element;
import ru.unn.smartnet.model.Net;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class SmartnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartnetApplication.class, args);

		Graph<Element> testGraph = new Graph<Element>();
		Element moscow = new Element(501, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Москва", PARAM_TYPE.STRING_TYPE)); }});
		Element spb = new Element(502, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Санкт-Петербург", PARAM_TYPE.STRING_TYPE)); }});
		Element nn = new Element(503, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Нижний Новгород", PARAM_TYPE.STRING_TYPE)); }});
		Element vologda = new Element(504, 11111, new ArrayList<NetParam>() {{ add(new NetParam(1, "Название", "Вологда", PARAM_TYPE.STRING_TYPE)); }});

		testGraph.addVertice(moscow);
		testGraph.addVertice(spb);
		testGraph.addVertice(nn);
		testGraph.addVertice(vologda);

		testGraph.addDoubleEdge(nn, moscow, new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 400, PARAM_TYPE.INTEGER_TYPE)); }});
		testGraph.addDoubleEdge(spb, moscow, new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 640, PARAM_TYPE.INTEGER_TYPE)); }});
		testGraph.addDoubleEdge(vologda, moscow, new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 410, PARAM_TYPE.INTEGER_TYPE)); }});
		testGraph.addDoubleEdge(vologda, spb, new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 550, PARAM_TYPE.INTEGER_TYPE)); }});
		testGraph.addDoubleEdge(vologda, nn, new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 405, PARAM_TYPE.INTEGER_TYPE)); }});

		Net net = new Net();
		net.setId(11111);
		net.setName("Города России");
		net.setDate(new Date());
		net.setGraph(testGraph);
		net.setType(1);
		System.out.println(net);
	}

}

