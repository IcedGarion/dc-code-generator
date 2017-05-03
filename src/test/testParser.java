package test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.Test;
import ast.LangType;
import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import symTable.SymTable;

public class testParser
{
	private String fileName = "./resources/testParser";
	
	@Test
	public void testParseNo() throws IOException, SyntacticException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f f\ni f\nb = a + ");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		
		//programma sintatticamente non corretto
		try
		{
			p.parse();
			
			fail("Exception expected");
		}
		catch(Exception e)
		{
			assertEquals("Expected ID but was FLOATDCL", e.getMessage());
		}
	}
	
	@Test
	public void testParseDcls() throws IOException, SyntacticException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f b\ni a\n");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		
		try
		{
			np = p.parse();
			
			assertEquals("Dcls:\n[b: FLOAT, a: INT]\nStms:\n[]", np.toString());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			fail();
		}
	}
	
	@Test
	public void testParseDclsPrints() throws FileNotFoundException, IOException, SyntacticException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f b\ni a\nf c\np a\np b\np c");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		
		try
		{
			np = p.parse();
			
			assertEquals("Dcls:\n[b: FLOAT, a: INT, c: FLOAT]\nStms:\n[(print) a, (print) b, (print) c]", np.toString());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			fail();
		}
	}
	
	@Test
	public void testParseAssignsCostId() throws FileNotFoundException, IOException, SyntacticException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f b\ni a\ni c\ni d\na = 5\nb = 5.5\nc = b");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		
		try
		{
			np = p.parse();
			
			assertEquals("Dcls:\n[b: FLOAT, a: INT, c: INT, d: INT]\nStms:\n[a = 5, b = 5.5, c = b]", np.toString());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			fail();
		}
	}
	
	@Test
	public void testParseExpr() throws FileNotFoundException, IOException, SyntacticException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f a\ni b\nb = a + 2\na = 1 - 2");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		
		try
		{
			np = p.parse();
			
			assertEquals("Dcls:\n[a: FLOAT, b: INT]\nStms:\n[b = a PLUS 2, a = 1 MINUS 2]", np.toString());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace(System.out);
			fail();
		}
	}
	
	@Test
	public void testSimpleSymTab() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f a\ni b\nf c\nb = a + 2\na = 1 - 2");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		
		p.parse();
		
		assertEquals(LangType.FLOAT, SymTable.lookup("a").getType());
		assertEquals(LangType.INT, SymTable.lookup("b").getType());
		assertEquals(LangType.FLOAT, SymTable.lookup("c").getType());
	}
	
	@Test
	public void testSymTabMore() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f a\ni a\n");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		
		try
		{
			p.parse();
		}
		catch(SyntacticException e)
		{
			assertEquals(e.getMessage(), "Duplicate variable a");
		}
	}
}