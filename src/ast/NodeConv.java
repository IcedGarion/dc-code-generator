package ast;

import visitor.AbsVisitor;

public class NodeConv extends NodeExpr
{
	private NodeExpr expr;
	
	public NodeConv(NodeExpr expr)
	{
		this.expr = expr;
		setType(LangType.FLOAT);
	}
	
	public String toString()
	{
		return expr.toString();
	}
	
	@Override
	public void accept(AbsVisitor visitor)
	{
		visitor.visit(this);
	}
}
