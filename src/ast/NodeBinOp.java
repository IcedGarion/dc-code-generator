package ast;

import typecheck.TypeException;
import visitor.AbsVisitor;

public class NodeBinOp extends NodeExpr
{
	private LangOper operation;
	private NodeExpr left;
	private NodeExpr right;
	
	public NodeBinOp(LangOper operation, NodeExpr left, NodeExpr right)
	{
		this.operation = operation;
		this.left = left;
		this.right = right;
	}
	
	public String toString()
	{
		return left.toString() + " " + operation + " " + right.toString();
	}
	
	public void accept(AbsVisitor visitor) throws TypeException
	{
		visitor.visit(this);
	}
	
	public NodeExpr getLeft()
	{
		return left;
	}
	
	public NodeExpr getRight()
	{
		return right;
	}
}
