package lr1;

public enum ActionType {
    SHIFT,REDUCE,ACCEPT,UNKNOWN;

    public static ActionType getType(String s){
        if ("s".equals(s)){
            return SHIFT;
        }else if ("r".equals(s)){
            return REDUCE;
        }else if ("a".equals(s)){
            return ACCEPT;
        }
        return UNKNOWN;
    }
}
