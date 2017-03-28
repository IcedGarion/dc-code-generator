package test;

import static org.junit.Assert.*;

import org.junit.Test;

import ast.LangType;
import ast.NodeAST;
import ast.NodeDecl;
import ast.NodeId;

public class testAst
{
	@Test
	public void testNodeId()
	{
		NodeAST dcl = new NodeDecl(new NodeId("a"), LangType.INT);
		
		assertEquals("a", ((NodeDecl) dcl).getId());
		assertEquals(LangType.INT, ((NodeDecl) dcl).getType());
		assertEquals("a: INT", ((NodeDecl) dcl).toString());		
		((NodeDecl) dcl).setType(LangType.FLOAT);
		assertEquals(LangType.FLOAT, ((NodeDecl) dcl).getType());
	}
}
