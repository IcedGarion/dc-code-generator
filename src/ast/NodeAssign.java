package ast;

import typecheck.TypeException;
import visitor.AbsVisitor;

public class NodeAssign extends NodeStm
{
	private NodeId id;
	private NodeAST expr;
	
	public NodeAssign(NodeId i, NodeExpr e)
	{
		this.id = i;
		this.expr = e;
	}
	
	public String toString()
	{
		return id.toString()+" = "+expr.toString();
	}
	
	public void accept(AbsVisitor visitor) throws TypeException
	{
		visitor.visit(this);
	}
	
	public NodeAST getExpr()
	{
		return expr;
	}
	
	public NodeId getId()
	{
		return id;
	}
}
