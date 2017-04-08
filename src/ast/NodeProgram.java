package ast;

import java.util.ArrayList;
import visitor.AbsVisitor;

public class NodeProgram extends NodeAST
{
	private ArrayList<NodeDecl> Dcls;
	private ArrayList<NodeStm> Stms;
	
	public NodeProgram(ArrayList<NodeDecl> d, ArrayList<NodeStm> s)
	{
		this.Dcls = d;
		this.Stms = s;
	}
	
	public String toString()
	{
		return "Dcls:\n" + Dcls.toString() + "\nStms:\n" + Stms.toString();
	}
	
	public void accept(AbsVisitor visitor)
	{
		visitor.visit(this);
	}
	
	public ArrayList<NodeDecl> getDecl()
	{
		return Dcls;
	}
	
	public ArrayList<NodeStm> getStms()
	{
		return Stms;
	}
}