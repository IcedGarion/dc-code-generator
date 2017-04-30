package visitor;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import ast.*;
import typecheck.TypeException;

public abstract class AbsVisitor
{
	
	public abstract void visit(NodeProgram n) throws TypeException, FileNotFoundException, UnsupportedEncodingException;
	
	public abstract void visit(NodeId n);
	
	public abstract void visit(NodeDecl n);
	
	public abstract void visit(NodePrint n);

	public abstract void visit(NodeAssign n) throws TypeException, FileNotFoundException, UnsupportedEncodingException;

	public abstract void visit(NodeCost n);

	public abstract void visit(NodeConv n);

	public abstract void visit(NodeDeref n);

	public abstract void visit(NodeBinOp n) throws TypeException, FileNotFoundException, UnsupportedEncodingException;

}
