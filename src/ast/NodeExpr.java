package ast;

public abstract class NodeExpr extends NodeAST
{
	private LangType type;
	
	public void setType(LangType t)
	{
		this.type = t;
	}
	
	public LangType getType()
	{
		return type;
	}
}