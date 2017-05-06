package symTable;

import ast.LangType;

/**
 * Class STEntry rappresenta la parte valore nella SymTable:
 * è un oggetto contenente informazioni sul Tipo della variabile
 * e se questa è stata inizializzata o no
 * 
 * @author Garion Musetta
 */
public class STEntry
{
	private boolean init;
	private LangType type;
	
	/**
	 * Costruttore: inizializza l'oggetto con informazioni sul tipo e sull'inizializzazione
	 * 
	 * @param type			Il tipo della variabile (int o float)
	 * @param initialized	se la variabile è già stata inizializzata o no
	 */
	public STEntry(LangType type, boolean initialized)
	{
		this.init = initialized;
		this.type = type;
	}
	
	/**
	 * Restituisce il tipo della variabile 
	 * 
	 * @return		Il tipo della variabile
	 */
	public LangType getType()
	{
		return type;
	}
	
	/**
	 * Dice se la variabile è stata inizializzata o no
	 * 
	 * @return		true se la variabile è stata inizializzata
	 */
	public boolean isInitialized()
	{
		return init;
	}
	
	/**
	 * Rappresentazione in stringa della STEntry
	 * 
	 * @return 	una stringa che rappresenta la STEntry
	 */
	public String toString()
	{
		return type.toString();
	}
}