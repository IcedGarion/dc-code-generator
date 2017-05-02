package symTable;

import java.util.HashMap;
import java.util.Map;


public class SymTable
{
	private static Map<String, STEntry> table;

	public static void init()
	{
		table = new HashMap<String, STEntry>();
	}

	public static boolean enter(String id, STEntry entry)
	{
		STEntry value = table.get(id);
		
		if (value != null)
			return false;
		
		table.put(id, entry);
		
		return true;
	}

	public static boolean replace(String id, STEntry oldValue, STEntry newValue)
	{
		return table.replace(id, oldValue, newValue);
	}
	
	public static STEntry lookup(String id)
	{
		return table.get(id);
	}

	public static String toStr()
	{ 
		// Per output
		StringBuilder res = new StringBuilder("symbol table\n=============\n");

		for (Map.Entry<String, STEntry> entry : table.entrySet())
			res.append(entry.getKey()).append("   \t").append(entry.getValue())
					.append("\n");

		return res.toString();
	}

	public static int size()
	{
		return (table.size());
	}
}
