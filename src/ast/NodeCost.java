package ast;

public class NodeCost extends NodeExpr
{
	private String value;
	
	public NodeCost(String value, LangType type)
	{
		this.value = value;
		super.setType(type);
	}
	
	public String toString()
	{
		return value;
	}
}
