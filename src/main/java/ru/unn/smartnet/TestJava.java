package ru.unn.smartnet;

import java.util.*;

public class TestJava {

    private static Map<String, Integer> table = new HashMap<>();
    static {
        table.put("G", 57);
        table.put("A", 71);
        table.put("S", 87);
        table.put("P", 97);
        table.put("V", 99);
        table.put("T", 101);
        table.put("C", 103);
        table.put("I", 113);
        table.put("L", 113);
        table.put("N", 114);
        table.put("D", 115);
        table.put("K", 128);
        table.put("Q", 128);
        table.put("E", 129);
        table.put("M", 131);
        table.put("H", 137);
        table.put("F", 147);
        table.put("R", 156);
        table.put("Y", 163);
        table.put("W", 186);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Integer mass = sc.nextInt();
        Integer num = 0;


        while(true) {

        }

        System.out.println(num);
    }


    private static String replace(String str, int index, char newChar){
        if(str == null) return str;
        if(index < 0 || index >= str.length()) return str;
        char[] chars = str.toCharArray();
        chars[index] = newChar;
        return String.valueOf(chars);
    }
}
