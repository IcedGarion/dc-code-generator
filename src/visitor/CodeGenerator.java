package visitor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Stack;
import ast.LangOper;
import ast.LangType;
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
	private final String outputFileName = "./resources/dcOut";
	private PrintWriter writer;
	private Stack<String> operators;
	
	public CodeGenerator() throws FileNotFoundException, UnsupportedEncodingException
	{
		writer = new PrintWriter(outputFileName, "UTF-8");	
		operators = new Stack<String>();
	}
	
	@Override
	public void visit(NodeProgram n) throws FileNotFoundException, UnsupportedEncodingException, TypeException
	{
		for(NodeStm nd : n.getStms())
			nd.accept(this);
		
		writer.write("\n");
		writer.close();
		operators.clear();
	}

	@Override
	public void visit(NodeId n)
	{
		//riassunto nella assign
	}

	@Override
	public void visit(NodeDecl n)
	{
		//vuoto
	}

	@Override
	public void visit(NodePrint n)
	{
		//carica sullo stack il registro con nome corrispondente a ID e lo stampa
		writer.write(" l" + n.getId() + " p");
	}

	@Override
	public void visit(NodeAssign n) throws TypeException, FileNotFoundException, UnsupportedEncodingException
	{
		String id;
		
		//inizia cambiando la precisione, se la parte dx è float
		if(n.getExpr().getType().equals(LangType.FLOAT))
			writer.write(" 5k");
		
		//scompone in ID ed EXPR: ID sarà il nome del registro in cui salvare il risultato
		id = n.getId().toString();
		
		//chiama accept(NodeExpr) come l'altro visitor;
		n.getExpr().accept(this);
		
		//poi salva il risultato (in cima allo stack) in un registro "ID", pronto per la stampa
		writer.write(" s" + id);
	}

	@Override
	public void visit(NodeCost n)
	{
		writer.write(" " + n.toString());
	}

	@Override
	public void visit(NodeConv n)
	{
		//riassunto nella binOp
	}

	@Override
	public void visit(NodeDeref n)
	{
		writer.write(" " + n.toString());
	}

	@Override
	public void visit(NodeBinOp n) throws TypeException, FileNotFoundException, UnsupportedEncodingException
	{
		//visita tutta la expr e stampa trasformata
		visitRic(n);
		
		//svuota lo stack operatori se ne è rimasto qualcuno
		while(! operators.empty())
			writer.write(" " + operators.pop());
	}
	
	private void visitRic(NodeBinOp n) throws FileNotFoundException, UnsupportedEncodingException, TypeException
	{
		if(n.getOperation().equals(LangOper.MINUS))		//gestisce operatori
		{
			while(! operators.empty())
				writer.write(" " + operators.pop());
				
			operators.push("-");
		}
		else if(n.getOperation().equals(LangOper.PLUS))
		{
			while(! operators.empty())
				writer.write(" " + operators.pop());
				
			operators.push("+");
		}
		
		writer.write(" " + n.getLeft());			//poi aggiunge operatore destro
		
		if(n.getRight() instanceof NodeBinOp)			//poi se la parte destra è un'altra operazione,
			visitRic((NodeBinOp) n.getRight());			//chiama in ricorsione;
		else											
			n.getRight().accept(this);					//altrimenti rimane solo un valore e sarà cost o deref
	}													//quindi chiama su di loro
}