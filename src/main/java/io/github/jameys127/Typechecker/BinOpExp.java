package io.github.jameys127.Typechecker;

import io.github.jameys127.Parser.Exp;
import io.github.jameys127.Parser.Op;

public record BinOpExp(Exp left, Op op, Exp right) implements Exp{
    
}
