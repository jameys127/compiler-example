package io.github.jameys127;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import io.github.jameys127.Lexer.AddToken;
import io.github.jameys127.Lexer.DivisionToken;
import io.github.jameys127.Lexer.IdentifierToken;
import io.github.jameys127.Lexer.IntegerToken;
import io.github.jameys127.Lexer.Token;
import io.github.jameys127.Lexer.Tokenizer;
import io.github.jameys127.Lexer.TokenizerException;

public class TokenizerTest {
    @Test
    public void testEmpty(){
        final Tokenizer tokenizer = new Tokenizer("");
        Token token = new AddToken();
        Token token2 = new AddToken();
        Token token3 = new DivisionToken();
        System.out.println(token);   
        System.out.println(token2);   
        System.out.println(token3);   
        System.out.println(token.hashCode());   
        System.out.println(token2.hashCode());   
        System.out.println(token3.hashCode());   
        System.out.println(token.equals(token2));   
        tokenizer.skipWhitespace();
        assertEquals(0, tokenizer.getPosition());
    }

    @Test
    public void testSingleWhiteSpace(){
        final Tokenizer tokenizer = new Tokenizer(" ");
        tokenizer.skipWhitespace();
        assertEquals(1, tokenizer.getPosition());
    }
    
    @Test
    public void testSingleWhiteSpaceAndCharacter(){
        final Tokenizer tokenizer = new Tokenizer(" 1");
        tokenizer.skipWhitespace();
        assertEquals(1, tokenizer.getPosition());
    }

    @Test
    public void testReadSingleDigitInteger(){
        final Tokenizer tokenizer = new Tokenizer("1");
        assertEquals(Optional.of(new IntegerToken(1)),
                    tokenizer.tryReadIntegerToken());
    }

    @Test
    public void testReadDoubleDigitInteger(){
        final Tokenizer tokenizer = new Tokenizer("12");
        assertEquals(Optional.of(new IntegerToken(12)), tokenizer.tryReadIntegerToken());
    }

    @Test
    public void testReadDigitIntergerWithWhiteSpace(){
        final Tokenizer tokenizer = new Tokenizer("12 ");
        assertEquals(Optional.of(new IntegerToken(12)), tokenizer.tryReadIntegerToken());
    }

    @Test
    public void testReadIntegerWithEmpty(){
        final Tokenizer tokenizer = new Tokenizer("");
        assertEquals(Optional.empty(), tokenizer.tryReadIntegerToken());
    }

    @Test
    public void TestMultiTokenize() throws TokenizerException{
        //foo bar
        final Tokenizer tokenizer = new Tokenizer("foo bar");
        final ArrayList<Token> tokens = tokenizer.tokenize();
        assertEquals(new IdentifierToken("foo"), tokens.get(0));
        assertEquals(new IdentifierToken("bar"), tokens.get(1));
    }
    
}
