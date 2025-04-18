package io.github.jameys127.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.jameys127.Lexer.AddToken;
import io.github.jameys127.Lexer.EqualsToken;
import io.github.jameys127.Lexer.IdentifierToken;
import io.github.jameys127.Lexer.MinusToken;
import io.github.jameys127.Lexer.PrintToken;
import io.github.jameys127.Lexer.ReturnToken;
import io.github.jameys127.Lexer.SemicolonToken;
import io.github.jameys127.Lexer.Token;

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
    public ParseResult<Exp> multExp(final int startPos) throws ParseException{
        return null;
    }

    public ParseResult<Exp> addExp(final int startPos) throws ParseException{
        final ParseResult<Exp> m = multExp(startPos);
        Exp result = m.result();
        boolean shouldRun = true;
        int pos = m.nextPos();
        while(shouldRun){
            final Token t = readToken(pos);
            final Op op;
            if(t instanceof AddToken){
                op = new PlusOp();
            }else if (t instanceof MinusToken) {
                op = new MinusOp();
            }
        }
        return null;
    }

    public ParseResult<Exp> exp(final int startPos) throws ParseException{
        return addExp(startPos);
    }

    public void assertTokenIs(final int pos, final Token expected) throws ParseException{
        final Token received = readToken(pos);
        if(!expected.equals(received)){
            throw new ParseException("Expected: " + expected.toString() +
                                    "; received: " + received.toString());
        }
    }

    /*
     * This shit needs fixing look at his code
     * on github and try to figure it out.
     */


    // stmt ::= Identifier '=' exp ';' | 'print' exp ';'
    public ParseResult<Stmt> stmt(final int startPos) throws ParseException{
        final Token token = readToken(startPos);
        if(token instanceof IdentifierToken id){
            String name = id.name();
            assertTokenIs(startPos + 1, new EqualsToken());
            ParseResult<Exp> expression = exp(startPos + 2);
            assertTokenIs(expression.nextPos(), new SemicolonToken());
            AssignStmt assign = new AssignStmt(name, expression.result());
            return new ParseResult<Stmt>(assign, expression.nextPos() + 1);
        }else if(token instanceof PrintToken){
            ParseResult<Exp> expression = exp(startPos + 1);
            assertTokenIs(expression.nextPos(), new SemicolonToken());
            PrintStmt print = new PrintStmt(expression.result());
            return new ParseResult<Stmt>(print, expression.nextPos() + 1);
        }else if(token instanceof ReturnToken){
            ParseResult<Optional<Exp>> opExpression;
            try{
                ParseResult<Exp> expression = exp(startPos + 1);
                opExpression = new ParseResult<Optional<Exp>>(Optional.of(expression.result()), expression.nextPos());
            } catch (ParseException e){
                opExpression = new ParseResult<Optional<Exp>>(Optional.empty(), startPos + 1);
            }
            assertTokenIs(opExpression.nextPos(), new SemicolonToken());
            return new ParseResult<Stmt>(new ReturnStmt(opExpression.result()), opExpression.nextPos() + 1);
        }
        else{
            throw new ParseException("Expected statement, got: " + token);
        }
    }//stmt

    //program ::= stmt*
    public ParseResult<Program> program(final int startPos){
        final List<Stmt> stmts = new ArrayList<Stmt>();
        boolean shouldRun = true;
        int pos = startPos;
        while(shouldRun){
            try{
                final ParseResult<Stmt> stmtRes = stmt(pos);
                stmts.add(stmtRes.result());
                pos = stmtRes.nextPos();
            } catch (ParseException e){
                shouldRun = false;
            }
        }
        return new ParseResult<Program>(new Program(stmts), pos);
    }// program

    public Program parseWholeProgram() throws ParseException{
        final ParseResult<Program> p = program(0);
        if(p.nextPos() == tokens.length){
            return p.result();
        }else{
            throw new ParseException("Invalid token at position: " + p.nextPos());
        }
    }// parseWholeProgram
}
