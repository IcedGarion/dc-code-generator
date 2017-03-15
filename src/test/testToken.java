package test;

import static org.junit.Assert.*;

import org.junit.Test;

import token.Token;
import token.TokenType;

public class testToken
{
	@Test
	public void simpleTest()
	{
		Token t = new Token(TokenType.FLOATDCL, "5.0");
		
		assertEquals(TokenType.FLOATDCL, t.getType());
		assertEquals("5.0", t.getValue());
		assertEquals("FLOATDCL,5.0", t.toString());
	}
	
	@Test
	public void testMore()
	{
		Token t = new Token(TokenType.INTDCL, "5");
		
		assertEquals(TokenType.INTDCL, t.getType());
		assertEquals("5", t.getValue());
		assertEquals("INTDCL,5", t.toString());
		
		t = new Token(TokenType.ASSIGN, null);
		assertEquals(TokenType.ASSIGN, t.getType());
		assertEquals(null, t.getValue());
		assertEquals("ASSIGN", t.toString());
		
		t = new Token(TokenType.PRINT, null);
		assertEquals(TokenType.PRINT, t.getType());
		assertEquals(null, t.getValue());
		assertEquals("PRINT", t.toString());
		
		t = new Token(TokenType.PLUS, null);
		assertEquals(TokenType.PLUS, t.getType());
		assertEquals(null, t.getValue());
		assertEquals("PLUS", t.toString());
	}
}
