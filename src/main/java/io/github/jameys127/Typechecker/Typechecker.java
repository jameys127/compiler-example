package io.github.jameys127.Typechecker;

import java.util.HashMap;
import java.util.Map;

import io.github.jameys127.Parser.Exp;
import io.github.jameys127.Parser.IntExp;
import io.github.jameys127.Parser.PlusOp;
import io.github.jameys127.Parser.Stmt;

public class Typechecker {
    public static Map<Variable, Type> addMap(Map<Variable, Type> env, Variable var, Type type){
        final Map<Variable, Type> retval = new HashMap<Variable, Type>(env);
        retval.put(var, type);
        return retval;
    }
    public static Map<Variable, Type> typecheck(Stmt stmt, Map<Variable, Type> env) throws TypeErrorException{
        if(stmt instanceof WhileStmt ws){
            if(typeOf(ws.guard(), env) instanceof BoolType){
                typecheck(ws.body(), env);
                return env;
            }else{
                throw new TypeErrorException("Guard non-Boolean");
            }
        }else if(stmt instanceof BlockStmt block){
            Map<Variable, Type> innerEnv = env;
            for(Stmt innerStmt : block.stmts()){
                innerEnv = typecheck(innerStmt, innerEnv);
            }
            return env;
        }else if(stmt instanceof VarDecStmt vds){
            final Type receivedType = typeOf(vds.exp(), env);
            if(receivedType.equals(vds.type())){
                return addMap(env, vds.var(), receivedType);
            }else{
                throw new TypeErrorException("Received type: " + receivedType);
            }
        }else{
            assert false;
            throw new TypeErrorException("no such statement: " + stmt);
        }
    }
    public static Type typeOf(final Exp e, final Map<Variable, Type> env)throws TypeErrorException{
        if(e instanceof VarExp ve){
            final Variable name = ve.v();
            if(env.containsKey(name)){
                return env.get(name);
            }else{
                throw new TypeErrorException("Variable not in scope: " + name);
            }
        }else if(e instanceof IntExp){
            return new IntType();
        }else if(e instanceof TrueExp || e instanceof FalseExp){
            return new BoolType();
        }else if(e instanceof BinOpExp boe){
            Type leftType = typeOf(boe.left(), env);
            Type righType = typeOf(boe.right(), env);
            if(boe.op() instanceof PlusOp &&
               leftType instanceof IntType &&
               righType instanceof IntType){
                return new IntType();
            }else if(boe.op() instanceof LessThanOp &&
                     leftType instanceof IntType &&
                     righType instanceof IntType){
                return new BoolType();
            }else if(boe.op() instanceof AndOp &&
                     leftType instanceof BoolType &&
                     righType instanceof BoolType){
                return new BoolType();
            }else{
                throw new TypeErrorException("Bad Operator");
            }
        }else{
            throw new TypeErrorException("Unknown expression");
        }
    }
}
