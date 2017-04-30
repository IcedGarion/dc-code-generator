package ast;

import visitor.AbsVisitor;
import visitor.VariableNotInizializedException;

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
	
	@Override
	public void accept(AbsVisitor visitor) throws VariableNotInizializedException
	{
		visitor.visit(this);
	}
}
