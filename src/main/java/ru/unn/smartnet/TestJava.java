package ru.unn.smartnet;

import java.util.*;

public class TestJava {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        for(int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == 'A') text = replace(text, i, 'T');
            else if(text.charAt(i) == 'T') text = replace(text, i, 'A');
            else if(text.charAt(i) == 'C') text = replace(text, i, 'G');
            else if(text.charAt(i) == 'G') text = replace(text, i, 'C');
        }
        StringBuilder reverse = new StringBuilder();
        for(int i = text.length() - 1; i >= 0; i--)
            reverse = reverse.append(text.charAt(i));

        System.out.println(reverse);
    }

    private static String replace(String str, int index, char newChar){
        if(str == null) return str;
        if(index < 0 || index >= str.length()) return str;
        char[] chars = str.toCharArray();
        chars[index] = newChar;
        return String.valueOf(chars);
    }
}
