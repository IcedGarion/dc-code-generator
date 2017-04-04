package ast;

import visitor.AbsVisitor;

public class NodeId extends NodeStm
{
	private String name;
	
	public NodeId(String id)
	{
		this.name = id;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public void accept(AbsVisitor visitor)
	{
		visitor.visit(this);
	}
}
