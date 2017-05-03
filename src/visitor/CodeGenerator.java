package visitor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import symTable.STEntry;
import symTable.SymTable;
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
	public void visit(NodeProgram n) throws FileNotFoundException, UnsupportedEncodingException, TypeException, VariableNotInizializedException
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
	public void visit(NodeAssign n) throws TypeException, FileNotFoundException, UnsupportedEncodingException, VariableNotInizializedException
	{
		String id;
		STEntry old, updated;
		
		/*	questione precisione ancora da vedere
		//inizia cambiando la precisione, se la parte dx è float
		if(n.getExpr().getType().equals(LangType.FLOAT))
			writer.write(" 5k");
		*/
		
		//scompone in ID ed EXPR: ID sarà il nome del registro in cui salvare il risultato
		id = n.getId().toString();
		
		//chiama accept(NodeExpr) come l'altro visitor;
		n.getExpr().accept(this);
		
		//poi salva il risultato (in cima allo stack) in un registro "ID", per riusarlo in altre operazioni o stamparlo
		writer.write(" s" + id);
		
		//aggiorna la entry della symbolTable mettendo true il campo "inizializzata"
		old = SymTable.lookup(id);
		updated = new STEntry(old.getType(), true);
		SymTable.replace(id, old, updated);
	}

	@Override
	public void visit(NodeCost n)
	{
		float value;
		
		//se il valore è negativo toglie il '-' e sostituisce con '_'
		if(n.getType() == LangType.FLOAT)
			value = Float.parseFloat(n.toString());
		else
			value = Integer.parseInt(n.toString());
		
		if(value < 0)
			writer.write(" " + n.toString().replace('-', '_'));
		else
			writer.write(" " + n.toString());
	}

	@Override
	public void visit(NodeConv n)
	{
		//riassunto nella binOp
	}

	@Override
	public void visit(NodeDeref n) throws VariableNotInizializedException
	{
		//controlla se la variabile esiste (cioè se inizializzata): tiene una lista
		if(! SymTable.lookup(n.toString()).isInitialized())
			throw new VariableNotInizializedException(n.toString());
		
		//carica il registro con nome = id
		writer.write(" l" + n.toString());
	}

	@Override
	public void visit(NodeBinOp n) throws TypeException, FileNotFoundException, UnsupportedEncodingException, VariableNotInizializedException
	{
		//visita tutta la expr e stampa trasformata
		visitRic(n);
		
		//svuota lo stack operatori se ne è rimasto qualcuno
		while(! operators.empty())
			writer.write(" " + operators.pop());
	}
	
	private void visitRic(NodeBinOp n) throws FileNotFoundException, UnsupportedEncodingException, TypeException, VariableNotInizializedException
	{
		n.getLeft().accept(this);						//gestisce operatore sinistro (NodeConst o NodeDeref)
		
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
		
		if(n.getRight() instanceof NodeBinOp)			//poi se la parte destra è un'altra operazione,
			visitRic((NodeBinOp) n.getRight());			//chiama in ricorsione;
		else											
			n.getRight().accept(this);					//altrimenti rimane solo un valore e sarà cost o deref
	}													//quindi chiama su di loro
}