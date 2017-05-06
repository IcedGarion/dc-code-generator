package symTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Class SymTable fornisce una struttura dati dove memorizzare informazioni sulle variabili
 * 
 * @author Paola Giannini
 */
public class SymTable
{
	private static Map<String, STEntry> table;

	/**
	 * Inizializza la struttura dati che mappa chiave-valore
	 */
	public static void init()
	{
		table = new HashMap<String, STEntry>();
	}

	/**
	 * Inserisce nella struttura dati una nuova associazione chiave-valore (String-STEntry)
	 * 
	 * @param id		(chiave) il nome della variabile
	 * @param entry		(valore) STEntry: informazioni associate alla variabile
	 * @return			true se l'inserimento va a buon fine, false se l'elemento esisteva già
	 */
	public static boolean enter(String id, STEntry entry)
	{
		STEntry value = table.get(id);
		
		if (value != null)
			return false;
		
		table.put(id, entry);
		
		return true;
	}

	/**
	 * Sostituisce la precedente associazione chiave-valore con una nuova chiave-nuovoValore
	 * 
	 * @param id			(chiave) il nome della variabile
	 * @param oldValue		(valore) la vecchia STEntry associata a id
	 * @param newValue		(valore) la nuova STEntry che si vuole associare a id
	 * @return				true se il valore è stato correttamente rimpiazzato
	 */
	public static boolean replace(String id, STEntry oldValue, STEntry newValue)
	{
		return table.replace(id, oldValue, newValue);
	}
	
	/**
	 * Cerca nella SymbolTable se esiste l'associazione chiave-valore, data una chiave
	 * 
	 * @param id		chiave di ricerca (nome della variabile di cui si vogliono le informazioni)
	 * @return			La STEntry (valore) corrispondente o null se non esiste
	 */
	public static STEntry lookup(String id)
	{
		return table.get(id);
	}

	/**
	 * Rappresentazione in Stringa di tutta la SymTable
	 * 
	 * @return	la stringa che rappresenta la SymTable
	 */
	public static String toStr()
	{ 
		// Per output
		StringBuilder res = new StringBuilder("symbol table\n=============\n");

		for (Map.Entry<String, STEntry> entry : table.entrySet())
			res.append(entry.getKey()).append("   \t").append(entry.getValue())
					.append("\n");

		return res.toString();
	}

	/**
	 * Restituisce il numero di elementi memorizzati nella SymTable
	 * 
	 * @return		il numero di elementi memorizzati nella SymTable
	 */
	public static int size()
	{
		return (table.size());
	}
}
