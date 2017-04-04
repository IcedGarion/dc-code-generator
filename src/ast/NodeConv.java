package ast;

public class NodeConv extends NodeExpr
{
	private NodeExpr expr;
	
	public NodeConv(NodeExpr expr)
	{
		this.expr = expr;
	}
	
	public String toString()
	{
		return expr.toString();
	}
}
