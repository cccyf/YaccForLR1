package LexParser;

public enum TokenType {
    IF,
    ELSE,
    SEMICOLON,
    A,
    B,
    DOLLAR,
    INVALID;

    public static TokenType parseType(String str) {
        TokenType tokenType = INVALID;
        switch (str) {
            case "if":
                tokenType = IF;
                break;
            case "else":
                tokenType = ELSE;
                break;
            case "a":
                tokenType = A;
                break;
            case "b":
                tokenType=B;
                break;
            case ";":
                tokenType = SEMICOLON;
                break;
        }
        return tokenType;
    }
}
