package ast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
	public void accept(AbsVisitor visitor) throws TypeException, FileNotFoundException, UnsupportedEncodingException
	{
		visitor.visit(this);
	}
}
