/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.modeloLisp;

/**
 *
 * @author Gerax
 */
public class CreateList {
    
    public static List<Object> parse(String input) {
        List<Object> result = new ArrayList<>();
        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (c == '(') {
                int end = findMatchingParen(input, i);
                result.add(parse(input.substring(i + 1, end)));
                i = end + 1;
            } else if (c == ')') {
                throw new IllegalArgumentException("Unexpected ')'");
            } else if (Character.isWhitespace(c)) {
                i++;
            } else {
                int end = findEndOfAtom(input, i);
                result.add(parseAtom(input.substring(i, end)));
                i = end;
            }
        }
        return result;
    }

    private static Object parseAtom(String atom) {
        if (isInteger(atom)) {
            return Integer.parseInt(atom);
        } else if (isDouble(atom)) {
            return Double.parseDouble(atom);
        } else {
            return atom;
        }
    }

    private static int findMatchingParen(String input, int start) {
        int depth = 0;
        for (int i = start; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Unmatched '('");
    }

    private static int findEndOfAtom(String input, int start) {
        int i = start;
        while (i < input.length() && !Character.isWhitespace(input.charAt(i)) && input.charAt(i) != ')') {
            i++;
        }
        return i;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
