package ast;

import visitor.AbsVisitor;

public class NodePrint extends NodeStm
{
	private NodeId id;
	
	public NodePrint(NodeId id)
	{
		this.id = id;
	}
	
	@Override
	public String toString()
	{
		return "(print) "+id.toString();
	}
	
	public void accept(AbsVisitor visitor)
	{
		visitor.visit(this);
	}
}
