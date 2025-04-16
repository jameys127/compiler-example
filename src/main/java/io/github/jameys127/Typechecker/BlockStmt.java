package io.github.jameys127.Typechecker;

import java.util.List;

import io.github.jameys127.Parser.Stmt;

public record BlockStmt(List<Stmt> stmts) implements Stmt{
    
}
