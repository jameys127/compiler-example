package io.github.jameys127.Parser;

public record AssignStmt(String name, Exp e) implements Stmt{
    // public class PrintStmt implements Stmt{
    //     public final Exp e;
    //     public AssignStmt(FinalString name, Exp e){
    //         this.name = name;
    //         this.e = e;
    //     }
    // }
}
