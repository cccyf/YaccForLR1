package LexParser;

public class Token {
    private TokenType type;
    private String label;

    public Token(TokenType tokenType,String label){
        this.type = tokenType;
        this.label = label.substring(0,1);
    }

    public TokenType getTokenType(){
        return type;
    }

    public String getLabel(){
        return label;
    }

}
