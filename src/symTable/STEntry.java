package symTable;

import ast.LangType;

public class STEntry {
	private LangType type;
	
	public STEntry(LangType type){
		this.type = type;
	}
	
	public LangType getType(){
		return type;
	}
	
	public String toString(){
		return type.toString();
	}

}
