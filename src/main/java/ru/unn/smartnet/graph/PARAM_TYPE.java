package ru.unn.smartnet.graph;

public enum PARAM_TYPE {
    STRING_TYPE,
    INTEGER_TYPE,
    DOUBLE_TYPE;

    public static PARAM_TYPE getParamTypeFromIndex(int index) {
        switch (index) {
            case 1:
                return PARAM_TYPE.STRING_TYPE;
            case 2:
                return PARAM_TYPE.INTEGER_TYPE;
            case 3:
                return PARAM_TYPE.DOUBLE_TYPE;

            default:
                throw new RuntimeException("Unknown index:" + index);
        }
    }

    public static Integer getParamIDByName(String attrName) {
        switch (attrName) {
            case "STRING_TYPE":
                return 1;
            case "INTEGER_TYPE":
                return 2;
            case "DOUBLE_TYPE":
                return 3;
        }
        return -1;
    }
}
