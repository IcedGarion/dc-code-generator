package ast;

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
}
