package typecheck;

import ast.NodeDecl;
import ast.NodeProgram;
import ast.NodeStm;
import visitor.ConcreteVisitor;

public class TypeChecker
{
	public static void check(NodeProgram np) throws TypeException
	{
		ConcreteVisitor v = new ConcreteVisitor();
		
		np.accept(v);
		for (NodeDecl d : np.getDecl())
			d.accept(v);
		for (NodeStm s : np.getStms())
			s.accept(v);
	}
}
