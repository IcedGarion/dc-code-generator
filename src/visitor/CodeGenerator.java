package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConv;
import ast.NodeCost;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
import typecheck.TypeException;

public class CodeGenerator extends AbsVisitor
{
	//IMPORTANTI SOPRATTUTTI I NODEXPR
	
	@Override
	public void visit(NodeProgram n) throws TypeException
	{
		//crea il file
		for(NodeStm nd : n.getStms())
			nd.accept(this);
		//metti tutte le accept con CodeGenerator (negli AST)
		
		//segui algoritmo infix to postfix (solo + e -, no precedenze)
	}

	@Override
	public void visit(NodeId n)
	{
		//prende il valore e lo scrive nel file (push subito)
		//forse: nelle expr, si salva la parte sx di assegnamento in un REG. con stesso nome
		//SE FLOAT, INIZIA CAMBIANDO PRECISIONE
	}

	@Override
	public void visit(NodeDecl n)
	{
		//vuoto
	}

	@Override
	public void visit(NodePrint n)
	{
		//scrive 'p'
	}

	@Override
	public void visit(NodeAssign n) throws TypeException
	{
		//scompone in ID ed EXPR; chiama accept(NodeExpr) come l'altro visitor;
		//poi salva in un registro id?
		//POI SE ID È FLOAT, CAMBIA PRECISONE (SOLO IN ID CREDO)
	}

	@Override
	public void visit(NodeCost n)
	{
		//la stampa subito
	}

	@Override
	public void visit(NodeConv n)
	{
		//mette 'k' davanti all'espressione
	}

	@Override
	public void visit(NodeDeref n)
	{
		//prende il valore e lo stampa
	}

	@Override
	public void visit(NodeBinOp n) throws TypeException
	{
		//scompone in pezzi e poi delega
		//+ infix to postfix
		
	}
}