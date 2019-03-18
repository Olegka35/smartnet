package ru.unn.smartnet.dao;

import ru.unn.smartnet.model.AddNetObject;
import ru.unn.smartnet.model.Net;

import java.util.List;

public interface NetDAO {
    List<Net> getNetList();
    Net getNet(Integer id);
    void addNet(AddNetObject net);
    void deleteNet(Integer id);
}
