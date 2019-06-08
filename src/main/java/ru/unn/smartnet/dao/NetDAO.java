package ru.unn.smartnet.dao;

import ru.unn.smartnet.model.AddNetObject;
import ru.unn.smartnet.model.Net;
import ru.unn.smartnet.model.UpdateNetObject;

import java.util.List;

public interface NetDAO {
    List<Net> getNetList();
    Net getNet(Integer id);
    void addNet(AddNetObject net);
    void deleteNet(Integer id);
    void setNetName(Integer id, String newName);
    void setNetType(Integer id, Integer newType);
    void updateNetData(Integer id, UpdateNetObject data);
}
