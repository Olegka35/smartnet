package ru.unn.smartnet.model.algorithms;

import ru.unn.smartnet.graph.NetParam;
import ru.unn.smartnet.model.Element;
import ru.unn.smartnet.model.Net;

import java.util.*;

import static java.lang.Double.MAX_VALUE;

public class BranchAndBounds {
    private List<SubSet> setList;
    private Set<SubSet> notDivided;

    public BranchAndBounds(Net net, NetParam param, List<Element> elements) {
        double[][] matrix = new double[elements.size()][elements.size()];
        for(int i = 0; i < elements.size(); i++) {
            for(int j = 0; j < elements.size(); j++) {
                if(i == j) matrix[i][j] = Double.MAX_VALUE;
                else matrix[i][j] = (Double)(new Dijkstra(net, param)).getShortestPath(elements.get(i).getId(), elements.get(j).getId()).get("min_distance");
            }
        }
        for(int i = 0; i < elements.size(); ++i) matrix[i][i] = MAX_VALUE;
        int[] rowIDs = new int[elements.size()];
        int[] fieldIDs = new int[elements.size()];
        for(int i = 0; i < elements.size(); ++i) {
            matrix[i][i] = MAX_VALUE;
            fieldIDs[i] = rowIDs[i] = elements.get(i).getId();
        }

        SubSet start = new SubSet(null, elements.size(), matrix, rowIDs, fieldIDs, 1);

        notDivided = new HashSet<>();
        notDivided.add(start);
    }

    public void start() {
        while(true) {
            SubSet set = getMinEvalSet();
            notDivided.remove(set);

            if(set.size > 1) {
                double[][] matrix1 = new double[set.size - 1][set.size - 1];
                double[][] matrix2 = new double[set.size][set.size];
                int[] rowIDs = new int[set.size-1];
                int[] fieldIDs = new int[set.size-1];

                for (int i = 0; i < set.size-1; i++) {
                    int rowShift = 0;
                    if (i == (Integer) set.firstStepResult.get("from")) rowShift = 1;
                    rowIDs[i] = set.rowIDs[i + rowShift];
                    for (int j = 0; j < set.size-1; j++) {
                        int fieldShift = 0;
                        if (j == (Integer) set.firstStepResult.get("to")) fieldShift = 1;
                        if(i == 0) fieldIDs[j] = set.fieldIDs[j + fieldShift];
                        matrix1[i][j] = set.matrix[i + rowShift][j + fieldShift];
                    }
                }
                for (int i = 0; i < set.size; i++) {
                    for (int j = 0; j < set.size; j++) {
                        if(i == (Integer) set.firstStepResult.get("from") && j == (Integer) set.firstStepResult.get("to"))
                            matrix2[i][j] = Double.MAX_VALUE;
                        else
                            matrix2[i][j] = set.matrix[i][j];
                    }
                }
                SubSet set1 = new SubSet(set, set.size - 1, matrix1, rowIDs, fieldIDs, 1);
                SubSet set2 = new SubSet(set, set.size, matrix2, set.rowIDs, set.fieldIDs, 2);
                notDivided.addAll(Arrays.asList(set1, set2));
            }
            else {
                System.out.println(set);
                break;
                // ФИНАЛ
            }
        }
        //return ;
    }

    private class SubSet {
        SubSet parent;
        int size;
        double[][] matrix;
        int[] rowIDs;
        int[] fieldIDs;
        double evaluation;
        Map<String, Object> firstStepResult;

        SubSet(SubSet parent, int size, double[][] matrix, int[] rowIDs, int[] fieldIDs, int type) {
            this.parent = parent;
            this.size = size;
            this.matrix = matrix;
            this.rowIDs = rowIDs;
            this.fieldIDs = fieldIDs;
            action(this, type);
        }
    }

    private void action(SubSet set, int type) {
        set.evaluation = (set.parent == null) ? 0.0 : set.parent.evaluation;
        for(int i = 0; i < set.size; i++) {
            double rowMin = rowMin(set, i);
            set.evaluation += rowMin;
            for(int k = 0; k < set.size; k++)
                set.matrix[i][k] -= rowMin;
        }
        for(int j = 0; j < set.size; j++) {
            double fieldMin = fieldMin(set, j);
            set.evaluation += fieldMin;
            for(int k = 0; k < set.size; k++)
                set.matrix[k][j] -= fieldMin;
        }
        if(type == 2)
            set.evaluation = set.parent.evaluation + (Double)set.parent.firstStepResult.get("fine");
        set.firstStepResult = getMaxFire(set);
    }

    private double rowMin(SubSet set, int row) {
        double minVal = Double.MAX_VALUE;
        double[][] matrix = set.matrix;
        for(int i = 0; i < set.size; i++) {
            minVal = Math.min(minVal, matrix[row][i]);
        }
        return minVal;
    }

    private double fieldMin(SubSet set, int field) {
        double minVal = Double.MAX_VALUE;
        double[][] matrix = set.matrix;
        for(int i = 0; i < set.size; i++) {
            minVal = Math.min(minVal, matrix[i][field]);
        }
        return minVal;
    }

    private Map<String, Object> getMaxFire(SubSet set) {
        Map<String, Object> result = new HashMap<>();
        double maxFine = Double.MIN_VALUE;
        for(int i = 0; i < set.size; i++) {
            for(int j = 0; j < set.size; j++) {
                if(set.matrix[i][j] == 0.0) {
                    double rowMin = Double.MAX_VALUE, fieldMin = Double.MAX_VALUE;
                    for(int k = 0; k < set.size; k++) {
                        if(k != j) rowMin = Math.min(rowMin, set.matrix[i][k]);
                    }
                    for(int l = 0; l < set.size; l++) {
                        if(l != i) fieldMin = Math.min(fieldMin, set.matrix[l][j]);
                    }
                    if(rowMin + fieldMin > maxFine) {
                        result.put("fine", rowMin + fieldMin);
                        result.put("from", i);
                        result.put("to", j);
                    }
                }
            }
        }
        return result;
    }

    private SubSet getMinEvalSet() {
        double minEvaluate = Double.MAX_VALUE;
        SubSet result = null;
        for(SubSet set: notDivided) {
            if(set.evaluation < minEvaluate) {
                minEvaluate = set.evaluation;
                result = set;
            }
        }
        return result;
    }
}
