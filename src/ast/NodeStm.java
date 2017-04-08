package ast;

import typecheck.TypeException;
import visitor.AbsVisitor;

public abstract class NodeStm extends NodeAST
{
	public abstract void accept(AbsVisitor v) throws TypeException;
}