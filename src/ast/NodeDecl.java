package ast;

import visitor.AbsVisitor;

public class NodeDecl extends NodeAST
{
	private NodeId id;
	private LangType type;
	
	public NodeDecl(NodeId id, LangType type)
	{
		this.id = id;
		this.type = type;
	}
	
	public String getId()
	{
		return id.toString();
	}
	
	public LangType getType()
	{
		return type;
	}
	
	public void setType(LangType newType)
	{
		this.type = newType;
	}
	
	@Override
	public String toString()
	{
		return id.toString()+": "+type;
	}
	
	@Override
	public void accept(AbsVisitor visitor)
	{
		visitor.visit(this);
	}
}
