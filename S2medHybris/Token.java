package S2medHybris;

// De olika token-typer vi har i grammatiken
enum TokenType {
    Data, Hex, Einstr, Dinstr, Repeat, Cinstr, Dot, Space, Cit, Invalid
    }

class Token {
    private TokenType type;
    private String exactType;
    private String data;
    private int line;
    private int indexInLine;

    public Token(TokenType type, String exactType, int line, int indexInLine) {
        this.type = type;
        this.exactType = exactType;
        this.line = line;
        this.indexInLine = indexInLine;
        this.data = null;
    }

    public Token(TokenType type, String exactType, int line, int indexInLine, String data) {
        this.type = type;
        this.exactType = exactType;
        this.line = line;
        this.indexInLine = indexInLine;
        this.data = data;
    }

    public TokenType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public int getIntData() {
        return Integer.parseInt((String) data);
    }

    public int getLine() {
        return line;
    }

    public int getIndexInLine() {
        return indexInLine;
    }

    public String getExactType() {
        return exactType;
    }
}

