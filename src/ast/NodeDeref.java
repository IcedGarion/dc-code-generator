package ast;

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
}
