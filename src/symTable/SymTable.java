package symTable;


import java.util.HashMap;

import java.util.Map;

import parser.SyntacticException;
import ast.LangType;

public class SymTable {
	  private Map<String,STEntry> table;;  

	  public  SymTable(){
	    table = new HashMap<String,STEntry>();
	  }

	  public  boolean enter(String id, STEntry entry)  {
		STEntry value = table.get(id); 
	    if (value!=null) return false;
	    table.put(id, entry);
	    return true;
	  }

	  public  STEntry lookup(String id) {
	    return table.get(id);
	  }

	  public  String toString() {    // for output with print
	    StringBuilder res = new StringBuilder("symbol table\n=============\n");
	    
	    for (Map.Entry<String,STEntry> entry : table.entrySet())
	      res.append(entry.getKey()).append("   \t").append(entry.getValue()).append("\n");

	    return res.toString();
	  }

	  public int size() {
	    return(table.size()); 
	  }
	}
