package io.github.jameys127;

public class Parser {
    public final Token[] tokens;
    public Parser(final Token[] tokens){
        this.tokens = tokens;
    }

    public Token readToken(final int pos) throws ParseException{
        if(pos < 0 || pos >= tokens.length){
            throw new ParseException("Ran out of tokens");
        }else{
            return tokens[pos];
        }
    }

    public ParseResult<Stmt> stmt(final int startPos) throws ParseException{

    }
}
