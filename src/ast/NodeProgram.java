package ast;

import java.util.ArrayList;
import typecheck.TypeException;
import visitor.AbsVisitor;
import visitor.VariableNotInizializedException;

public class NodeProgram extends NodeAST
{
	private ArrayList<NodeDecl> Dcls;
	private ArrayList<NodeStm> Stms;
	
	public NodeProgram(ArrayList<NodeDecl> d, ArrayList<NodeStm> s)
	{
		this.Dcls = d;
		this.Stms = s;
	}
	
	@Override
	public String toString()
	{
		return "Dcls:\n" + Dcls.toString() + "\nStms:\n" + Stms.toString();
	}
	
	
	public ArrayList<NodeDecl> getDecl()
	{
		return Dcls;
	}
	
	public ArrayList<NodeStm> getStms()
	{
		return Stms;
	}
	
	@Override
	public void accept(AbsVisitor visitor) throws VariableNotInizializedException, TypeException
	{
		visitor.visit(this);
	}
}