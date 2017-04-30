package ast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import typecheck.TypeException;
import visitor.AbsVisitor;

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
	
	public void accept(AbsVisitor visitor) throws TypeException, FileNotFoundException, UnsupportedEncodingException
	{
		visitor.visit(this);
	}
	
	public NodeExpr getExpr()
	{
		return expr;
	}
	
	public NodeId getId()
	{
		return id;
	}
}
