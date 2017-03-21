package test;

import static org.junit.Assert.*;

import org.junit.Test;

import ast.LangType;
import ast.NodeAST;
import ast.NodeDecl;

public class testAst
{
	@Test
	public void testNodes()
	{
		NodeAST dcl = new NodeDecl("a", LangType.INT);
		
		assertEquals("a", ((NodeDecl) dcl).getId());
		assertEquals(LangType.INT, ((NodeDecl) dcl).getType());
		assertEquals("Type = INT, id = a", ((NodeDecl) dcl).toString());		
		((NodeDecl) dcl).setType(LangType.FLOAT);
		assertEquals(LangType.FLOAT, ((NodeDecl) dcl).getType());
		((NodeDecl) dcl).setId("b");
		assertEquals("b", ((NodeDecl) dcl).getId());
		assertEquals("Type = FLOAT, id = b", ((NodeDecl) dcl).toString());		
	}
}
