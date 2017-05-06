package visitor;

import java.io.FileNotFoundException;
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
	public void visit(NodeProgram np) throws TypeException, VariableNotInizializedException
	{
		for (NodeDecl d : np.getDecl())
			d.accept(this);
		for (NodeStm s : np.getStms())
			s.accept(this);
	}
	
	@Override
	public void visit(NodeBinOp n) throws TypeException, VariableNotInizializedException
	{
		NodeExpr result[];
		
		//se è una operazione "composita", chiama in ricorsione sulla operazione binaria che segue
		if(n.getRight() instanceof NodeBinOp)
			n.getRight().accept(this);
		
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
	public void visit(NodeAssign n) throws TypeException, VariableNotInizializedException
	{
		LangType exprType;
		LangType idType;
		NodeExpr tmp = n.getExpr();
		
		//prima calcola il tipo dell'espressione (a seconda di che expr è, chiama la accept giusta)
		tmp.accept(this);
		
		//poi controlla se 'id è dello stesso tipo dell'espressione
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
