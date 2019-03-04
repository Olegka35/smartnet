package ru.unn.smartnet.service;

import ru.unn.smartnet.model.Net;

import java.util.List;

public interface NetService {
    Net getNet(Integer id);
    List<Net> getNetList();
    void createNet(Net net);
    void updateNet(Net net);
    void deleteNet(Integer netID);
}