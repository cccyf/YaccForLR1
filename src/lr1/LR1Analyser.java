package lr1;

import LexParser.Token;
import LexParser.TokenType;

import java.util.*;

public class LR1Analyser {
    private List<Token> tokens;

    public LR1Analyser(List<Token> tokenList) {
        this.tokens = tokenList;
    }

    public List<String> Analyse() {
        List<String> reductions = new ArrayList<>();

        int currentState = 0;
        Stack<Integer> stateStack = new Stack<>();
        Stack<String> symbolStack = new Stack<>();
        symbolStack.push("$");
        stateStack.push(0);
        Token currentToken;

        System.out.println("State_Stack					Symbol_Stack					Current_Token					Action");
        String states = "0";
        String symbols="$";
        while (true) {
            currentToken = tokens.get(0);
            if (currentToken == null || currentToken.getTokenType() == null) {
                break;
            }
            Action action = Table.getAction(currentState, currentToken.getTokenType());

            if (action==null){
                System.out.println("Wrong input");
                return reductions;
            }

            if (action.getActionType() == ActionType.SHIFT) {
                symbolStack.push(currentToken.getLabel());
                currentState = action.getNumber();
                stateStack.push(currentState);
                states = currentState+" "+states;
                symbols = currentToken.getLabel()+" "+symbols;
                tokens.remove(0);
                System.out.println(states+"					"+symbols+"					"+currentToken.getLabel()+"					"+action.toString());
            } else if (action.getActionType() == ActionType.REDUCE) {
                String reduce = Table.getProduction(action.getNumber());
                String [] args = reduce.split("->");
                String [] front = args[0].trim().split("");
                String [] back = args[1].trim().split("");

                for (int j = back.length-1; j >=0; j--) {
                    if (back[j].equals(symbolStack.peek())){
                        symbolStack.pop();
                        stateStack.pop();
                        symbols= symbols.substring(symbols.indexOf(" ")+1);
                        states = states.substring(states.indexOf(" ")+1);
                    }else {
                        System.out.println(back[j]);
                        System.out.println(symbolStack.peek());
                        System.out.println("Unknown syntax!");
                        return reductions;
                    }
                }

                for (int j = 0; j < front.length; j++) {
                    symbolStack.add(front[j]);
                    symbols = front[j]+" "+symbols;
                }

                reductions.add(reduce);
                currentState = stateStack.peek();

                System.out.println(states+"					"+symbols+"					"+currentToken.getLabel()+"					"+action.toString());

                if (Table.belongToGOTOs(symbolStack.peek())){
                    currentState = Table.getGOTO(currentState,symbolStack.peek());
                    stateStack.push(currentState);
                    states = currentState+" "+states;
                    System.out.println(states+"					"+symbols+"					"+currentToken.getLabel()+"					"+action.toString());
                }

            }else if (action.getActionType()==ActionType.ACCEPT){
                reductions.add(Table.getProduction(0));
                symbolStack.pop();
                stateStack.pop();
                symbols= symbols.substring(symbols.indexOf(" ")+1);
                states = states.substring(states.indexOf(" ")+1);
                System.out.println(states+"					"+symbols+"					"+currentToken.getLabel()+"					"+action.toString());
                if ("$".equals(symbolStack.peek())){
                    symbolStack.pop();
                    System.out.println("Done");
                }else {
                    System.out.println("Wrong input");
                }
                break;
            }else {
                System.out.println("NULL");
            }

        }

        return reductions;
    }

}
