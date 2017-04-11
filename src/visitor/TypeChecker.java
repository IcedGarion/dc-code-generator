package visitor;

import ast.LangType;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConv;
import ast.NodeCost;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
import symTable.SymTable;
import typecheck.TypeCheckingUtil;
import typecheck.TypeException;

public class TypeChecker extends AbsVisitor
{
	@Override
	public void visit(NodeProgram np) throws TypeException
	{
		for (NodeDecl d : np.getDecl())
			d.accept(this);
		for (NodeStm s : np.getStms())
			s.accept(this);
	}
	
	@Override
	public void visit(NodeBinOp n) throws TypeException
	{
		NodeExpr result[];
		
		result = TypeCheckingUtil.consistent(n.getLeft(), n.getRight());
		if(result[0].getType().equals(LangType.INT))
		{
			n.setType(LangType.INT);
		}
		else if(result[0].getType().equals(LangType.FLOAT))
		{
			n.setType(LangType.FLOAT);
		}
	}
	
	@Override
	public void visit(NodeAssign n) throws TypeException
	{
		LangType exprType;
		LangType idType;
		NodeExpr tmp;
		String exprName = n.getExpr().getClass().getSimpleName();
		
		//prima calcola il tipo dell'espressione (a seconda di che espressione è)
		switch(exprName)
		{
			case "NodeBinOp":
				visit((NodeBinOp) n.getExpr());
				break;
			case "NodeCost":
				visit((NodeCost) n.getExpr());
				break;
			case "NodeDeref":
				visit((NodeDeref) n.getExpr());
				break;
		}
		
		//poi controlla se 'id è dello stesso tipo dell'espressione
		//(quindi accept sulle expr diventa inutile)
		tmp = (NodeExpr) n.getExpr();
		exprType = tmp.getType();
		idType = SymTable.lookup(n.getId().toString()).getType();
		
		//un float può contenere int, viceversa no
		if(exprType.equals(LangType.FLOAT) && idType.equals(LangType.INT))
			throw new TypeException("Type mismatch in " + n.toString() + ": cannot convert from " + exprType + " to " + idType);
	}
	
	//per gli altri nodi cosa bisogna fare?
	@Override
	public void visit(NodeId n)
	{
		
	}

	@Override
	public void visit(NodeDecl n)
	{
		
	}

	@Override
	public void visit(NodePrint n)
	{
		
	}

	@Override
	public void visit(NodeCost n)
	{
		
	}

	@Override
	public void visit(NodeConv n)
	{
		
	}

	@Override
	public void visit(NodeDeref n)
	{
		
	}
}
