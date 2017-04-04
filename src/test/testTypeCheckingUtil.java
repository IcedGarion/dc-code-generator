package test;

import static org.junit.Assert.*;


import org.junit.Test;

import ast.LangType;
import ast.NodeConv;
import ast.NodeCost;
import ast.NodeExpr;
import typecheck.TypeCheckingUtil;
import typecheck.TypeException;

public class testTypeCheckingUtil
{
	@Test
	public void testConvert() throws TypeException
	{
		NodeExpr expri = new NodeCost("10");
		NodeExpr exprf = new NodeCost("10");
		expri.setType(LangType.INT);
		exprf.setType(LangType.FLOAT);
		
		assertEquals(new NodeConv(expri).getClass(), (TypeCheckingUtil.convert(expri, LangType.FLOAT).getClass()));
		assertEquals(expri, (TypeCheckingUtil.convert(expri, LangType.INT)));
		
		assertEquals(exprf, (TypeCheckingUtil.convert(exprf, LangType.FLOAT)));
		
		try
		{
			TypeCheckingUtil.convert(exprf, LangType.INT);
		}
		catch(TypeException e)
		{
			assertEquals(e.getMessage(), "Expected INT but was FLOAT");
		}
	}

}
