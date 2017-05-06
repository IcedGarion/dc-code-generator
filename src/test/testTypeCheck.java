package test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import ast.NodeProgram;
import parser.Parser;
import scanner.Scanner;
import visitor.TypeChecker;
import typecheck.TypeException;

public class testTypeCheck
{
	private String fileName = "./resources/testTypeCheck";
	
	@Before
	public void createDir()
	{
		new File("resources").mkdir();
	}
	
	@Test
	public void testExc1() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\na = b + 3.2 ");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
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
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\na = b");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
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
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\na = 3.2");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
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
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nb = a + 2\na = 1 - 2");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
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
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\ni b\na = b + 3");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
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
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nb = a + 3");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
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
	public void testOk4() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nb = a + 3");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
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
	public void testExc4() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\na = 1 + 3.2");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
			fail("Exception expected");
		}
		catch(Exception e)
		{
			assertEquals("Type mismatch in a = 1 PLUS 3.2: cannot convert from FLOAT to INT", e.getMessage());
		}
	}
	
	@Test
	public void testMoreExprs() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nf c\na = 1\nb = a + 3\nc = b + a - 3.2\n");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor = new TypeChecker();
		
		np = p.parse();
		
		try
		{
			np.accept(visitor);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("No exception expected");
		}
	}
}
