package ru.unn.smartnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;
import ru.unn.smartnet.model.Element;
import ru.unn.smartnet.model.Net;
import ru.unn.smartnet.model.algorithms.Dijkstra;
import ru.unn.smartnet.service.NetService;

import java.util.List;
import java.util.Map;

@RestController
public class NetController {

    @Autowired
    private NetService netService;

    @RequestMapping(value = "/net/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map> getNet(@PathVariable("id") Integer netID) {
        return new ResponseEntity<Map>(netService.getNet(1).convertToMap(), HttpStatus.OK);
    }

    @RequestMapping(value = "/dijkstra/{id}", method = RequestMethod.GET)
    public ResponseEntity<List> getShortestPathDijkstra(@PathVariable("id") Integer netID, @RequestParam("from") Integer from, @RequestParam("to") Integer to) {
        Net net = netService.getNet(1);
        Dijkstra dijkstra = new Dijkstra(net, new NetParam(2, "Расстояние", 400, PARAM_TYPE.INTEGER_TYPE));
        List<Element> path = dijkstra.getShortestPath(from, to);
        if(path == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List>(path, HttpStatus.OK);
    }

}
