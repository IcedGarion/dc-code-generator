package ast;

public class NodeAssign extends NodeStm
{
	private NodeId id;
	private NodeExpr expr;
	
	public NodeAssign(NodeId i, NodeExpr e)
	{
		this.id = i;
		this.expr = e;
	}
	
	public String toString()
	{
		return id.toString()+" = "+expr.toString();
	}
}
