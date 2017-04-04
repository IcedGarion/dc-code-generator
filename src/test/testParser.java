package test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import ast.LangType;
import ast.NodeProgram;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import symTable.SymTable;

public class testParser
{
	private String dclsOnlyFileName = "./resources/dclsParse";
	private String dclsPrintsOnlyFileName = "./resources/dclsPrintsParse";
	private String dummyFileName = "./resources/dummyParse";
	private String assignCostIdOnlyFileName = "./resources/assignCostParse";
	private String exprFileName = "./resources/exprParse";
	private String symTabFileName = "./resources/symParse";
	private String symTabMoreFileName = "./resources/symMoreParse";
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		//scrive file per i test
		PrintWriter writer;

		writer = new PrintWriter(dclsOnlyFileName, "UTF-8");
		writer.write("f b\ni a\n");
		writer.close();
		
		writer = new PrintWriter(dclsPrintsOnlyFileName, "UTF-8");
		writer.write("f b\ni a\nf c\np a\np b\np c");
		writer.close();
		
		writer = new PrintWriter(assignCostIdOnlyFileName, "UTF-8");
		writer.write("f b\ni a\ni c\ni d\na = 5\nb = 5.5\nc = b");
		writer.close();
		
		writer = new PrintWriter(exprFileName, "UTF-8");
		writer.write("f a\ni b\nb = a + 2\na = 1 - 2");
		writer.close();
		
		writer = new PrintWriter(dummyFileName, "UTF-8");
		writer.write("f f\ni f\nb = a + ");
		writer.close();
		
		writer = new PrintWriter(symTabFileName, "UTF-8");
		writer.write("f a\ni b\nf c\nb = a + 2\na = 1 - 2");
		writer.close();
		
		writer = new PrintWriter(symTabMoreFileName, "UTF-8");
		writer.write("f a\ni a\n");
		writer.close();
	}
	
	@Test
	public void testParseNo() throws IOException, SyntacticException
	{
		Parser p = new Parser(new Scanner(dummyFileName));
		
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
		Parser p = new Parser(new Scanner(dclsOnlyFileName));
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
		Parser p = new Parser(new Scanner(dclsPrintsOnlyFileName));
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
		Parser p = new Parser(new Scanner(assignCostIdOnlyFileName));
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
		Parser p = new Parser(new Scanner(exprFileName));
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
		Parser p = new Parser(new Scanner(symTabFileName));
		
		p.parse();
		
		assertEquals(LangType.FLOAT, SymTable.lookup("a").getType());
		assertEquals(LangType.INT, SymTable.lookup("b").getType());
		assertEquals(LangType.FLOAT, SymTable.lookup("c").getType());
	}
	
	@Test
	public void testSymTabMore() throws Exception
	{
		Parser p = new Parser(new Scanner(symTabMoreFileName));
		
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