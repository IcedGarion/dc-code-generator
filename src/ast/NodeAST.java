package ast;

import java.util.ArrayList;

import typecheck.TypeException;
import visitor.AbsVisitor;


public abstract class NodeAST
{
	public abstract void accept(AbsVisitor visitor) throws TypeException;

}
