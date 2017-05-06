package visitor;

import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.util.Stack;
import ast.LangOper;
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

/**
 * Class CodeGenerator genera codice dc a partire da un albero sintattico decorato con i tipi
 * (AST generato da TypeChecker)
 * 
 * @author Garion Musetta
 */
public class CodeGenerator extends AbsVisitor
{
	private final String outputFileName = "./resources/dcOut";
	private PrintWriter writer;
	private Stack<String> operators;
	
	/**
	 * Costruttore: inizializza i campi del CodeGenerator e crea il file di output
	 * 
	 * @throws FileNotFoundException			Se non riesce a generare il file di output	
	 */
	public CodeGenerator() throws FileNotFoundException
	{
		writer = new PrintWriter(outputFileName);	
		operators = new Stack<String>();
	}
	
	/**
	 * Visita nodeProgram e tutti gli altri nodi che contiene, e genera codice dc in un file dcOut
	 *
	 * @param nodeProgram						Il nodo principale dell'albero sintattico del programma, decorato con i tipi
	 * @throws TypeException 
	 * @throws VariableNotInitializedException	Se è presente una istruzione in cui compare a destra una variabile non inizializzata
	 */
	@Override
	public void visit(NodeProgram nodeProgram) throws VariableNotInizializedException, TypeException
	{
		for(NodeStm nd : nodeProgram.getStms())
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
	public void visit(NodeAssign n) throws VariableNotInizializedException, TypeException
	{
		String id;
		STEntry old, updated;
		
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
	public void visit(NodeBinOp n) throws VariableNotInizializedException, TypeException
	{
		//visita tutta la expr e stampa trasformata
		visitRic(n);
		
		//svuota lo stack operatori se ne è rimasto qualcuno
		while(! operators.empty())
			writer.write(" " + operators.pop());
	}
	
	private void visitRic(NodeBinOp n) throws VariableNotInizializedException, TypeException
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