package io.github.jameys127;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class TokenizerTest {
    @Test
    public void testEmpty(){
        final Tokenizer tokenizer = new Tokenizer("");
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
    
}
