package LexParser;

import java.util.Arrays;

public class Lex {
    private static String[] reservedWords = {
            "if", "else"
    };

    public static boolean isReservedWord(String s) {
        return Arrays.asList(reservedWords).contains(s);
    }

    public static boolean isA(char s) {
        return "a".equals("" + s);
    }

    public static boolean isB(char b) {
        return "b".equals("" + b);
    }

    public static boolean isSemicolon(char s) {
        return ";".equals("" + s);
    }
}
