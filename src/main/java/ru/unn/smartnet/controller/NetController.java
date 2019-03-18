package ru.unn.smartnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.graph.PARAM_TYPE;
import ru.unn.smartnet.model.AddNetObject;
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

    @RequestMapping(value = "/nets", method = RequestMethod.GET)
    public ResponseEntity<List<Net>> getNetList() {
        return new ResponseEntity<List<Net>>(netService.getNetList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/net/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map> getNet(@PathVariable("id") Integer netID) {
        return new ResponseEntity<Map>(netService.getNet(netID).convertToMap(), HttpStatus.OK);
    }

    @RequestMapping(value = "/net2/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map> getNet2(@PathVariable("id") Integer netID) {
        return new ResponseEntity<Map>(netService.getNet2(1).convertToMap(), HttpStatus.OK);
    }

    @RequestMapping(value = "/dijkstra/{id}", method = RequestMethod.GET)
    public ResponseEntity<List> getShortestPathDijkstra(@PathVariable("id") Integer netID, @RequestParam("from") Integer from, @RequestParam("to") Integer to) {
        Net net = netService.getNet(netID);
        Dijkstra dijkstra = new Dijkstra(net, new NetParam(2, "Расстояние", 400, PARAM_TYPE.INTEGER_TYPE));
        List<Element> path = dijkstra.getShortestPath(from, to);
        if(path == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List>(path, HttpStatus.OK);
    }

    @RequestMapping(value = "/net/", method = RequestMethod.POST)
    public ResponseEntity<Object> addNet(@RequestBody AddNetObject net) {
        if(!net.validate())
            return new ResponseEntity<Object>("Incorrect net data", HttpStatus.BAD_REQUEST);
        netService.createNet(net);
        return new ResponseEntity<Object>("Added", HttpStatus.OK);
    }

    @RequestMapping(value = "/net/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteNet(@PathVariable("id") Integer netID) {
        netService.deleteNet(netID);
        return new ResponseEntity<Object>("Net has been deleted", HttpStatus.OK);
    }
}
