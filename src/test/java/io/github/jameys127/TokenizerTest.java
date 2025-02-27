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
    public void testReadSingleDigitInteger(){
        final Tokenizer tokenizer = new Tokenizer("1");
        assertEquals(Optional.of(new IntegerToken(1)),
                    tokenizer.tryReadIntegerToken());
    }
}
