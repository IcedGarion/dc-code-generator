package ast;

import typecheck.TypeException;
import visitor.AbsVisitor;
import visitor.VariableNotInizializedException;


public abstract class NodeAST
{
	public abstract void accept(AbsVisitor visitor) throws TypeException, VariableNotInizializedException;

}
