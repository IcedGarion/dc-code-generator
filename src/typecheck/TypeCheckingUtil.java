package typecheck;

import ast.LangType;
import ast.NodeConv;
import ast.NodeExpr;

/**
 * Class TypeChecking util fornisce delle funzioni utili al typeChecker, per il controllo dei tipi di variabili
 * 
 * @author Garion Musetta
 */
public class TypeCheckingUtil
{

	// Ritorna FLOAT se uno dei due e' FLOAT , INT altrimenti
	private static LangType generalize(LangType tipo1, LangType tipo2)
	{
		if(tipo1 == LangType.FLOAT || tipo2 == LangType.FLOAT)
			return LangType.FLOAT;
		
		return LangType.INT;
	}

	/**
	 * Rende i due nodi consistenti e li restituisce come posizione 0 e 1 dell'array ritornato
	 * 
	 * @param e0					La prima espressione
	 * @param e1					la seconda espressione
	 * @return						un array contenente le due espressioni in input, convertite se necessario
	 * @throws TypeException		se le due espressioni non possono essere resi consistenti
	 */
	public static NodeExpr[] consistent(NodeExpr e0, NodeExpr e1) throws TypeException
	{
		NodeExpr[] ret = new NodeExpr[2];
		LangType type = generalize(e0.getType(), e1.getType());
		
		
		ret[0] = convert(e0, type);
		ret[1] = convert(e1, type);
		
		return ret;
	}

	/**
	 * Se 'expr' è di tipo 'type' lo ritorna, altrimenti se 'expr' ha tipo INT e 'type' ha tipo FLOAT
	 * ritorna un NodeConv che contiene expr; viceversa Errore
	 * 
	 * @param expr				L'espressione che si vuole tentare di convertire
	 * @param type				Il tipo in cui si vorrebbe convertire l'espressione
	 * @return					Un nodo dell'albero che abbia stesso tipo di type, cioè convertito (se serve)
	 * @throws TypeException	Se i due tipi non sono compatibili
	 */
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
