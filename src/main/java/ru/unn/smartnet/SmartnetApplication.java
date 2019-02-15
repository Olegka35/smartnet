package ru.unn.smartnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.unn.smartnet.graph.Graph;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;

import java.util.ArrayList;

@SpringBootApplication
public class SmartnetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartnetApplication.class, args);

		Graph<String> test = new Graph<String>();
		test.addVertice("Москва");
		test.addVertice("Санкт-Петербург");
		test.addVertice("Нижний Новгород");
		test.addVertice("Вологда");

		test.addDoubleEdge("Нижний Новгород", "Москва", new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 400, PARAM_TYPE.INTEGER_TYPE)); }});
		test.addDoubleEdge("Санкт-Петербург", "Москва", new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 640, PARAM_TYPE.INTEGER_TYPE)); }});
		test.addDoubleEdge("Вологда", "Москва", new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 410, PARAM_TYPE.INTEGER_TYPE)); }});
		test.addDoubleEdge("Вологда", "Санкт-Петербург", new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 550, PARAM_TYPE.INTEGER_TYPE)); }});
		test.addDoubleEdge("Вологда", "Нижний Новгород", new ArrayList<NetParam>() {{ add(new NetParam(1, "Расстояние", 405, PARAM_TYPE.INTEGER_TYPE)); }});
		System.out.println(test);
	}

}

