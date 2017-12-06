package lr1;

import LexParser.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Table {

    private static final String[] productions = {"#->S","S->iSeS;","S->iS;","S->a","S->b"};
    private static final String gotoSequence = "S";
    private static final String[][] actionTable = {{"s1",null,null,"s2","s3",null},{"s5",null,null,"s6","s7",null},{null,null,null,null,null,"r3"},{null,null,null,null,null,"r4"},{null,null,null,null,null,"a"},{"s5",null,null,"s6","s7",null},{null,"r3","r3",null,null,null},{null,"r4","r4",null,null,null},{null,"s10","s11",null,null,null},{null,"s12","s13",null,null,null},{"s14",null,null,"s15","s16",null},{null,null,null,null,null,"r2"},{"s14",null,null,"s15","s16",null},{null,"r2","r2",null,null,null},{"s5",null,null,"s6","s7",null},{null,null,"r3",null,null,null},{null,null,"r4",null,null,null},{null,null,"s20",null,null,null},{null,null,"s21",null,null,null},{null,"s22","s23",null,null,null},{null,null,null,null,null,"r1"},{null,"r1","r1",null,null,null},{"s14",null,null,"s15","s16",null},{null,null,"r2",null,null,null},{null,null,"s25",null,null,null},{null,null,"r1",null,null,null}};
    private static final int[] gotoTable = {4,8,-1,-1,-1,9,-1,-1,-1,-1,17,-1,18,-1,19,-1,-1,-1,-1,-1,-1,-1,24,-1,-1,-1};

    private static final Map<TokenType, Integer> tokenPositionMap = new HashMap<TokenType, Integer>() {
        {

put(TokenType.IF, 0);
            put(TokenType.ELSE, 1);
            put(TokenType.SEMICOLON, 2);
            put(TokenType.A, 3);
            put(TokenType.B, 4);
            put(TokenType.DOLLAR, 5);        }
    };

    public static Action getAction(int state, TokenType tokenType) {
        ActionType actionType;
        String action = actionTable[state][tokenPositionMap.get(tokenType)];
        if (action == null) {
            return null;
        }
        actionType = ActionType.getType(action.substring(0, 1));
        if (actionType == ActionType.ACCEPT)
            return new Action(actionType, 0);
        else
            return new Action(actionType, Integer.parseInt(action.substring(1)));
    }

    public static int getGOTO(int state, String s) {
        return gotoTable[state];
    }

    public static String getProduction(int p) {
        return productions[p];
    }

    public static boolean belongToGOTOs(String s) {
        if (gotoSequence.contains(s)) {
            return true;
        }
        return false;
    }
}
