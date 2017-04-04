package ast;

import visitor.AbsVisitor;

public class NodeDeref extends NodeExpr
{
	private NodeId id;
	
	public NodeDeref(NodeId i)
	{
		this.id = i;
	}
	
	public String toString()
	{
		return id.toString();
	}
	
	public void accept(AbsVisitor visitor)
	{
		visitor.visit(this);
	}
}
