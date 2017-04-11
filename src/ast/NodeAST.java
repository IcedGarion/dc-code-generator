package ast;

import typecheck.TypeException;
import visitor.AbsVisitor;


public abstract class NodeAST
{
	public abstract void accept(AbsVisitor visitor) throws TypeException;

}
