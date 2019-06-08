package ru.unn.smartnet.service;

import ru.unn.smartnet.model.AddNetObject;
import ru.unn.smartnet.model.Net;
import ru.unn.smartnet.model.UpdateNetObject;

import java.util.List;

public interface NetService {
    Net getNet(Integer id);
    Net getNet2(Integer id);
    List<Net> getNetList();
    void createNet(AddNetObject net);
    void updateNet(UpdateNetObject updates);
    void deleteNet(Integer netID);
}
