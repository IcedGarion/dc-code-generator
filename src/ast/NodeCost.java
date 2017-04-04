package ast;

import visitor.AbsVisitor;

public class NodeCost extends NodeExpr
{
	private String value;
	
	public NodeCost(String value)
	{
		this.value = value;
	}
	
	public String toString()
	{
		return value;
	}
	
	public void accept(AbsVisitor visitor)
	{
		visitor.visit(this);
	}
}
