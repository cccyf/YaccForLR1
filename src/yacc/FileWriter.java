package yacc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriter {
    private static final String tableName= "src/lr1/Table.java";
    private static final String action = "src/lr1/Action.java";
    private static final String actionType = "src/lr1/ActionType.java";
    private static final String lr1Analyser = "src/lr1/LR1Analyser.java";

    private static final String[] nameList = {tableName,action,actionType,lr1Analyser};

    public static void writeAllFiles(String productions,String actionTable,String gotoTable,String tokenPositionMap,String gotoSequence){
        String tableContent = "package lr1;\n" +
                "\n" +
                "import LexParser.TokenType;\n" +
                "\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "public class Table {\n" +
                "\n" +
                "    private static final String[] productions = {"+productions+"};" +
                "\n" +
                "    private static final String gotoSequence = \""+gotoSequence+"\";" +
                "\n" +
                "    private static final String[][] actionTable = {"+actionTable+"};" +
                "\n" +
                "    private static final int[] gotoTable = {"+gotoTable+"};\n" +
                "\n" +
                "    private static final Map<TokenType, Integer> tokenPositionMap = new HashMap<TokenType, Integer>() {\n" +
                "        {\n" +tokenPositionMap+
                "        }\n" +
                "    };\n" +
                "\n" +
                "    public static Action getAction(int state, TokenType tokenType) {\n" +
                "        ActionType actionType;\n" +
                "        String action = actionTable[state][tokenPositionMap.get(tokenType)];\n" +
                "        if (action == null) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        actionType = ActionType.getType(action.substring(0, 1));\n" +
                "        if (actionType == ActionType.ACCEPT)\n" +
                "            return new Action(actionType, 0);\n" +
                "        else\n" +
                "            return new Action(actionType, Integer.parseInt(action.substring(1)));\n" +
                "    }\n" +
                "\n" +
                "    public static int getGOTO(int state, String s) {\n" +
                "        return gotoTable[state];\n" +
                "    }\n" +
                "\n" +
                "    public static String getProduction(int p) {\n" +
                "        return productions[p];\n" +
                "    }\n" +
                "\n" +
                "    public static boolean belongToGOTOs(String s) {\n" +
                "        if (gotoSequence.contains(s)) {\n" +
                "            return true;\n" +
                "        }\n" +
                "        return false;\n" +
                "    }\n" +
                "}\n";

        String ActionContent="package lr1;\n" +
                "\n" +
                "public class Action {\n" +
                "    private int number;\n" +
                "    private ActionType actionType;\n" +
                "\n" +
                "    public Action(ActionType actionType, int number) {\n" +
                "        this.number = number;\n" +
                "        this.actionType = actionType;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    public int getNumber() {\n" +
                "        return number;\n" +
                "    }\n" +
                "\n" +
                "    public ActionType getActionType() {\n" +
                "        return actionType;\n" +
                "    }\n" +
                "\n" +
                "    public String toString() {\n" +
                "        return actionType==ActionType.ACCEPT?\"Accept\":actionType.toString().substring(0,1)+number;\n" +
                "    }\n" +
                "}\n";

        String ActionTypeContent = "package lr1;\n" +
                "\n" +
                "public enum ActionType {\n" +
                "    SHIFT,REDUCE,ACCEPT,UNKNOWN;\n" +
                "\n" +
                "    public static ActionType getType(String s){\n" +
                "        if (\"s\".equals(s)){\n" +
                "            return SHIFT;\n" +
                "        }else if (\"r\".equals(s)){\n" +
                "            return REDUCE;\n" +
                "        }else if (\"a\".equals(s)){\n" +
                "            return ACCEPT;\n" +
                "        }\n" +
                "        return UNKNOWN;\n" +
                "    }\n" +
                "}\n";

        String LR1AnalyserContent = "package lr1;\n" +
                "\n" +
                "import LexParser.Token;\n" +
                "import LexParser.TokenType;\n" +
                "\n" +
                "import java.util.*;\n" +
                "\n" +
                "public class LR1Analyser {\n" +
                "    private List<Token> tokens;\n" +
                "\n" +
                "    public LR1Analyser(List<Token> tokenList) {\n" +
                "        this.tokens = tokenList;\n" +
                "    }\n" +
                "\n" +
                "    public List<String> Analyse() {\n" +
                "        List<String> reductions = new ArrayList<>();\n" +
                "\n" +
                "        int currentState = 0;\n" +
                "        Stack<Integer> stateStack = new Stack<>();\n" +
                "        Stack<String> symbolStack = new Stack<>();\n" +
                "        symbolStack.push(\"$\");\n" +
                "        stateStack.push(0);\n" +
                "        Token currentToken;\n" +
                "\n" +
                "        System.out.println(\"State_Stack\t\t\t\t\tSymbol_Stack\t\t\t\t\tCurrent_Token\t\t\t\t\tAction\");\n" +
                "        String states = \"0\";\n" +
                "        String symbols=\"$\";\n" +
                "        while (true) {\n" +
                "            currentToken = tokens.get(0);\n" +
                "            if (currentToken == null || currentToken.getTokenType() == null) {\n" +
                "                break;\n" +
                "            }\n" +
                "            Action action = Table.getAction(currentState, currentToken.getTokenType());\n" +
                "\n" +
                "            if (action==null){\n" +
                "                System.out.println(\"Wrong input\");\n" +
                "                return reductions;\n" +
                "            }\n" +
                "\n" +
                "            if (action.getActionType() == ActionType.SHIFT) {\n" +
                "                symbolStack.push(currentToken.getLabel());\n" +
                "                currentState = action.getNumber();\n" +
                "                stateStack.push(currentState);\n" +
                "                states = currentState+\" \"+states;\n" +
                "                symbols = currentToken.getLabel()+\" \"+symbols;\n" +
                "                tokens.remove(0);\n" +
                "                System.out.println(states+\"\t\t\t\t\t\"+symbols+\"\t\t\t\t\t\"+currentToken.getLabel()+\"\t\t\t\t\t\"+action.toString());\n" +
                "            } else if (action.getActionType() == ActionType.REDUCE) {\n" +
                "                String reduce = Table.getProduction(action.getNumber());\n" +
                "                String [] args = reduce.split(\"->\");\n" +
                "                String [] front = args[0].trim().split(\"\");\n" +
                "                String [] back = args[1].trim().split(\"\");\n" +
                "\n" +
                "                for (int j = back.length-1; j >=0; j--) {\n" +
                "                    if (back[j].equals(symbolStack.peek())){\n" +
                "                        symbolStack.pop();\n" +
                "                        stateStack.pop();\n" +
                "                        symbols= symbols.substring(symbols.indexOf(\" \")+1);\n" +
                "                        states = states.substring(states.indexOf(\" \")+1);\n" +
                "                    }else {\n" +
                "                        System.out.println(back[j]);\n" +
                "                        System.out.println(symbolStack.peek());\n" +
                "                        System.out.println(\"Unknown syntax!\");\n" +
                "                        return reductions;\n" +
                "                    }\n" +
                "                }\n" +
                "\n" +
                "                for (int j = 0; j < front.length; j++) {\n" +
                "                    symbolStack.add(front[j]);\n" +
                "                    symbols = front[j]+\" \"+symbols;\n" +
                "                }\n" +
                "\n" +
                "                reductions.add(reduce);\n" +
                "                currentState = stateStack.peek();\n" +
                "\n" +
                "                System.out.println(states+\"\t\t\t\t\t\"+symbols+\"\t\t\t\t\t\"+currentToken.getLabel()+\"\t\t\t\t\t\"+action.toString());\n" +
                "\n" +
                "                if (Table.belongToGOTOs(symbolStack.peek())){\n" +
                "                    currentState = Table.getGOTO(currentState,symbolStack.peek());\n" +
                "                    stateStack.push(currentState);\n" +
                "                    states = currentState+\" \"+states;\n" +
                "                    System.out.println(states+\"\t\t\t\t\t\"+symbols+\"\t\t\t\t\t\"+currentToken.getLabel()+\"\t\t\t\t\t\"+action.toString());\n" +
                "                }\n" +
                "\n" +
                "            }else if (action.getActionType()==ActionType.ACCEPT){\n" +
                "                reductions.add(Table.getProduction(0));\n" +
                "                symbolStack.pop();\n" +
                "                stateStack.pop();\n" +
                "                symbols= symbols.substring(symbols.indexOf(\" \")+1);\n" +
                "                states = states.substring(states.indexOf(\" \")+1);\n" +
                "                System.out.println(states+\"\t\t\t\t\t\"+symbols+\"\t\t\t\t\t\"+currentToken.getLabel()+\"\t\t\t\t\t\"+action.toString());\n" +
                "                if (\"$\".equals(symbolStack.peek())){\n" +
                "                    symbolStack.pop();\n" +
                "                    System.out.println(\"Done\");\n" +
                "                }else {\n" +
                "                    System.out.println(\"Wrong input\");\n" +
                "                }\n" +
                "                break;\n" +
                "            }else {\n" +
                "                System.out.println(\"NULL\");\n" +
                "            }\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        return reductions;\n" +
                "    }\n" +
                "\n" +
                "}\n";


        String[] content = {tableContent,ActionContent,ActionTypeContent,LR1AnalyserContent};

        try {
            for (int i = 0; i < nameList.length; i++) {
                File file = new File(nameList[i]);

                if (!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();
                BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file));
                writer.write(content[i]);
                writer.flush();
                writer.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("over");
    }
}
