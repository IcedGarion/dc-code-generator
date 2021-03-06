package ast;

import typecheck.TypeException;
import visitor.AbsVisitor;
import visitor.VariableNotInizializedException;

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
	
	@Override
	public String toString()
	{
		return left.toString() + " " + operation + " " + right.toString();
	}
	
	public NodeExpr getLeft()
	{
		return left;
	}
	
	public NodeExpr getRight()
	{
		return right;
	}
	
	public LangOper getOperation()
	{
		return operation;
	}
	
	@Override
	public void accept(AbsVisitor visitor) throws VariableNotInizializedException, TypeException
	{
		visitor.visit(this);
	}
}
