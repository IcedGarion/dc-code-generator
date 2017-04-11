package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import ast.NodeProgram;
import parser.Parser;
import scanner.Scanner;
import visitor.TypeChecker;
import typecheck.TypeException;

public class testTypeCheck
{
	private String excFileName = "./resources/typeCheck";
	private String testFileName = "./resources/typeCheck2";
	private String excFileName2 = "./resources/typeCheck3";
	private String excFileName3 = "./resources/typeCheck4";
	private String testFileName2 = "./resources/typeCheck6";
	private String testFileName3 = "./resources/typeCheck7";
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(excFileName, "UTF-8");
		writer.write("i a\nf b\na = b + 3.2 ");
		writer.close();
		
		writer = new PrintWriter(excFileName2, "UTF-8");
		writer.write("i a\nf b\na = b");
		writer.close();
		
		writer = new PrintWriter(excFileName3, "UTF-8");
		writer.write("i a\na = 3.2");
		writer.close();
		
		writer = new PrintWriter(testFileName, "UTF-8");
		writer.write("i a\nf b\nb = a + 2\na = 1 - 2");
		writer.close();
		
		writer = new PrintWriter(testFileName2, "UTF-8");
		writer.write("i a\ni b\na = b + 3");
		writer.close();
		
		writer = new PrintWriter(testFileName3, "UTF-8");
		writer.write("i a\nf b\nb = a + 3");
		writer.close();
	}
	
	@Test
	public void testExc1() throws Exception
	{
		Parser p = new Parser(new Scanner(excFileName));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
			fail("Exception expected");
		}
		catch(TypeException e)
		{
			assertEquals("Type mismatch in a = b PLUS 3.2: cannot convert from FLOAT to INT", e.getMessage());
		}	
	}
	
	@Test
	public void testExc2() throws Exception
	{
		Parser p = new Parser(new Scanner(excFileName2));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
			fail("Exception expected");
		}
		catch(TypeException e)
		{
			assertEquals("Type mismatch in a = b: cannot convert from FLOAT to INT", e.getMessage());
		}	
	}
	
	@Test
	public void testExc3() throws Exception
	{
		Parser p = new Parser(new Scanner(excFileName3));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
			fail("Exception expected");
		}
		catch(TypeException e)
		{
			assertEquals("Type mismatch in a = 3.2: cannot convert from FLOAT to INT", e.getMessage());
		}	
	}

	@Test
	public void testOk() throws Exception
	{
		Parser p = new Parser(new Scanner(testFileName));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
	}
	
	@Test
	public void testOk2() throws Exception
	{
		Parser p = new Parser(new Scanner(testFileName2));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
	}
	
	@Test
	public void testOk3() throws Exception
	{
		Parser p = new Parser(new Scanner(testFileName3));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
	}
}
