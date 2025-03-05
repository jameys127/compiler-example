package io.github.jameys127;

import java.util.ArrayList;
import java.util.Optional;

public class Tokenizer {

    public final String input;
    private int position;

    public int getPosition(){
        return position;
    }

    public Tokenizer(final String input){
        this.input = input;
        position = 0;
    }

    public void skipWhitespace(){
        while (position < input.length() &&
        Character.isWhitespace(input.charAt(position))){
            position++;
        }
    }
    
    // private void readAnyToken(){
    //     try {
    //         return tryReadIntegerToken();
    //     } catch (ToeknizerException e){
    //         try {
    //             return tryReadIdentifierToken()
    //         } catch (ToeknizerException e){
    //             try {
    //                 return tryReadEqualsToken()
    //             } catch (ToeknizerException e){
    //                 try{
    //                     ...
    //                 }
    //             }
    //         }
    //     }
    // }


    public Optional<Token> tryReadIntegerToken(){
        String digits = "";
        while (position < input.length() && Character.isDigit(input.charAt(position))){
            digits += input.charAt(position);
            position++;
        }
        if(digits.length() == 0){
            //return null;  //without using Optional; Have to do a null check
            return Optional.empty();
        } else {
            // return new IntegerToken(Integer.parseInt(digits)); //without using Optional
            return Optional.of(new IntegerToken(Integer.parseInt(digits)));
        }
    }//tryReadIntegerToken

    public Optional<Token> tryReadIdentifierToken(){
        if(position < input.length() && Character.isLetter(input.charAt(position))){
            String chars = "" + input.charAt(position);
            position++;
            while(position < input.length() && Character.isLetterOrDigit(input.charAt(position))){
                chars += input.charAt(position);
                position++;
            }
            return Optional.of(new IdentifierToken(chars));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Token> tryReadIdentifierTokenOrReservedWord(){
        if (position < input.length() &&
        Character.isLetter(input.charAt(position))){
            String chars = "" + input.charAt(position);
            position++;
            while (position < input.length() && Character.isLetterOrDigit(input.charAt(position))) {
                chars += input.charAt(position);
                position++;
            }
            if(chars.equals("print")){
                return Optional.of(new PrintToken());
            } else {
                return Optional.of(new IdentifierToken(chars));
            }
        }else {
            return Optional.empty();
        }
    }

    public Optional<Token> readSymbol(){
        String symbol = "";
        if (input.startsWith("(", position)){
            position++;
            return Optional.of(new LParenToken(symbol));
        }else if (input.startsWith(")", position)){
            position++;
            return Optional.of(new RParenToken(symbol));
        }else {
            return Optional.empty();
        }
        // continue with all symbols
    }

    // assumes we aren't on whitespace and we are in range
    public Token readToken() throws TokenizerException{
        Optional<Token> token1;
        if ((token1 = tryReadIntegerToken()).isPresent() ||
            (token1 = tryReadIdentifierToken()).isPresent() ||
            (token1 = readSymbol()).isPresent()){
            return token1.get();
        }else{
            throw new TokenizerException("Invalid char: " + input.charAt(position));
        }


        // Optional<Token> token = tryReadIntegerToken();
        // if (token.isPresent()){
        //     return token.get();
        // }else {
        //     token = tryReadIdentifierToken();
        //     if(token.isPresent()){
        //         return token.get();
        //     }else{
        //         token = readSymbol();
        //         if(token.isPresent()){
        //             return token.get();
        //         }else{
        //             throw new TokenizerException("Invalid char: " + input.charAt(position));
        //         }
        //     }
        // }
    }

    public ArrayList<Token> tokenize() throws TokenizerException {
        final ArrayList<Token> tokens = new ArrayList<Token>();
        skipWhitespace();
        while (position < input.length()){
            tokens.add(readToken());
            skipWhitespace();
        }
        return tokens;
    }
    
}
