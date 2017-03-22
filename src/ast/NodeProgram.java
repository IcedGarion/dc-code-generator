package ast;

import java.util.ArrayList;

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
}