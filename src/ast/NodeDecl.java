package ast;

public class NodeDecl extends NodeAST
{
	private String id;
	private LangType type;
	
	public NodeDecl(String id, LangType type)
	{
		this.id = id;
		this.type = type;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String newId)
	{
		this.id = newId;
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
		return "Type = " +type+ ", id = "+id;
	}
}
