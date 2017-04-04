package typecheck;

import ast.LangType;
import ast.NodeConv;
import ast.NodeExpr;

public class TypeCheckingUtil
{

	// Ritorna FLOAT se uno dei due e' FLOAT , INT altrimenti
	private static LangType generalize(LangType tipo1, LangType tipo2)
	{
		if(tipo1 == LangType.FLOAT || tipo2 == LangType.FLOAT)
			return LangType.FLOAT;
		
		return LangType.INT;
	}

	// Rende i due nodi consistenti e li restituisce come posizione 0 e 1
	// dell'array ritornata	
	public static NodeExpr[] consistent(NodeExpr e0, NodeExpr e1) throws TypeException
	{
		NodeExpr[] ret = new NodeExpr[2];
		
		//setta nuovo tipo in nodeExpr
		if(e0.getType() != e1.getType())
		{
			if(e0.getType() == LangType.INT)
				e0.setType(LangType.FLOAT);
			else
				e1.setType(LangType.FLOAT);
		}	
		
		ret[0] = e0;
		ret[1] = e1;
		
		return ret;
	}

	// Se NodeExpr e' di tipo "type" lo ritorna, altrimenti se NodeExpr
	// ha tipo INT e type=FLOAT ritorna un nodo NodeConv che contiene
	// expr, viceversa ritorna ERRORE
	public static NodeExpr convert(NodeExpr expr, LangType type) throws TypeException
	{
		if(expr.getType() == type)
			return expr;
		else if((expr.getType() == LangType.INT) && (type == LangType.FLOAT))
			return new NodeConv(expr);
		else
			throw new TypeException("Expected " + type + " but was " + expr.getType());
	}
}
