package LexParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Analyser {
    private char[] inputChars;

    public Analyser(char[] chars) {
        this.inputChars = chars;
    }

    public List<Token> analyse() {
        List<Token> tokenQueue = new ArrayList<>();
        int pointer = 0;
        char ch;

        while ((ch = inputChars[pointer++]) != '#') {
            String current = "";
            if (Lex.isA(ch)) {
                tokenQueue.add(new Token(TokenType.A, ch + ""));
            }else if (Lex.isB(ch)){
                tokenQueue.add(new Token(TokenType.B, ch + ""));
            } else if (Character.isLetter(ch)) {
                current += ch;
                while ((ch = inputChars[pointer]) != '#') {
                    if (Character.isLetter(ch)) {
                        current += ch;
                        pointer++;
                    } else {
                        //RESERVEDWORD
                        if (Lex.isReservedWord(current)) {
                            tokenQueue.add(new Token(TokenType.parseType(current), current));
                        } else {
                            System.out.println("INVALID INPUT");
                        }
                        break;
                    }
                }
            } else if (Lex.isSemicolon(ch)) {
                tokenQueue.add(new Token(TokenType.SEMICOLON, ch + ""));
            } else {
                if (ch != ' ' && ch != '\n') {
                    System.out.println("INVALID INPUT");
                }
            }
            continue;
        }
        tokenQueue.add(new Token(TokenType.DOLLAR,"$"));
        return tokenQueue;
    }
}
