package ru.unn.smartnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.unn.smartnet.model.Net;
import ru.unn.smartnet.service.NetService;

import java.util.Map;

@RestController
public class NetController {

    @Autowired
    private NetService netService;

    @RequestMapping(value = "/net/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map> getNet(@PathVariable("id") Integer netID) {
        return new ResponseEntity<Map>(netService.getNet(1).convertToMap(), HttpStatus.OK);
    }

}
