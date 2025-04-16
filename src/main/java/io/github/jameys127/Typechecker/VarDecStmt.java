package io.github.jameys127.Typechecker;

import io.github.jameys127.Parser.Exp;
import io.github.jameys127.Parser.Stmt;

public record VarDecStmt(Type type, Variable var, Exp exp) implements Stmt{
    
}
