package symTable;

import ast.LangType;

public class STEntry {
	private boolean init;
	private LangType type;
	
	public STEntry(LangType type, boolean initialized)
	{
		this.init = initialized;
		this.type = type;
	}
	
	public LangType getType(){
		return type;
	}
	
	public boolean isInitialized()
	{
		return init;
	}
	
	public String toString(){
		return type.toString();
	}

}
