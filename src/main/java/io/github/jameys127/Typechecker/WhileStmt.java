package io.github.jameys127.Typechecker;

import io.github.jameys127.Parser.Exp;
import io.github.jameys127.Parser.Stmt;

public record WhileStmt(Exp guard, Stmt body) implements Stmt{

}
