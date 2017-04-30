package ast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import typecheck.TypeException;
import visitor.AbsVisitor;
import visitor.VariableNotInizializedException;


public abstract class NodeAST
{
	public abstract void accept(AbsVisitor visitor) throws TypeException, FileNotFoundException, UnsupportedEncodingException, VariableNotInizializedException;

}
