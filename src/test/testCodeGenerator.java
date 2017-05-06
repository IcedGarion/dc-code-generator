package test;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import ast.NodeProgram;
import parser.Parser;
import scanner.Scanner;
import visitor.CodeGenerator;
import visitor.TypeChecker;
import visitor.VariableNotInizializedException;

public class testCodeGenerator
{
	private String dcOut = "./resources/dcOut";
	private String fileName = "./resources/testCodeGenerator";
	private BufferedReader reader;
	
	@Before
	public void createDir()
	{
		new File("resources").mkdir();
	}
	
	private String readDoc() throws Exception
	{
		String line, out = "";
		reader = new BufferedReader(new FileReader(dcOut));
		
		line = reader.readLine();
		while(line != null)
		{
			out += line + "\n";
			line = reader.readLine();
		}
		
		return out;
	}
	
	@Test
	public void testConst() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f a\na = 1 + 3.2\np a");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		
		np = p.parse();
		np.accept(visitor1);
		try
		{
			np.accept(visitor2);
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
		
		assertEquals(" 1 3.2 + sa la p\n", readDoc());
	}
	
	@Test
	public void testSimple() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f a\nf b\nb = 1\na = b + 3.2\np a ");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		
		np = p.parse();
		np.accept(visitor1);
		try
		{
			np.accept(visitor2);
		}
		catch(Exception e)
		{
			fail("No exception expected");
		}
		
		assertEquals(" 1 sb lb 3.2 + sa la p\n", readDoc());
	}
	
	@Test
	public void testUninitialized() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("f a\nf b\na = b + 3.2\np a ");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		
		np = p.parse();
		np.accept(visitor1);
		try
		{
			np.accept(visitor2);
			fail("Exception expected");
		}
		catch(VariableNotInizializedException e)
		{
			assertEquals("b", e.getMessage());
		}
	}
	
	@Test
	public void testMoreId() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nf c\na = 1\nb = a + a + a + 3\np b");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		
		np = p.parse();
		np.accept(visitor1);
		try
		{
			np.accept(visitor2);
			
		}
		catch(Exception e)
		{
			fail("Exception expected");
		}
		
		assertEquals(" 1 sa la la + la + 3 + sb lb p\n", readDoc());
	}
	
	@Test
	public void testMoreId2() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nf c\nf e\na = 1\nb = a + 3\nc = b + a - 3.2\ne = a + b - c - c - c - c - c\np e");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		
		np = p.parse();
		np.accept(visitor1);
		try
		{
			np.accept(visitor2);
			
		}
		catch(Exception e)
		{
			fail("Exception expected");
		}
		
		assertEquals(" 1 sa la 3 + sb lb la + 3.2 - sc la lb + lc - lc - lc - lc - lc - se le p\n", readDoc());
	}
	
	@Test
	public void testNeg1() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nf c\nf e\na = _1\nb = a - 3\nc = b + a - 3.2\ne = a + b - c + c\np e");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		
		np = p.parse();
		np.accept(visitor1);
		try
		{
			np.accept(visitor2);
			
		}
		catch(Exception e)
		{
			fail("Exception expected");
		}
		
		assertEquals(" _1 sa la 3 - sb lb la + 3.2 - sc la lb + lc - lc + se le p\n", readDoc());
	}
	
	@Test
	public void testNeg2() throws Exception
	{
		PrintWriter writer;
		
		writer = new PrintWriter(fileName, "UTF-8");
		writer.write("i a\nf b\nf c\nf e\na = _1\nb = a - _3\nc = b + a - _3.2\ne = a + b - c + c\np e");
		writer.close();
		
		Parser p = new Parser(new Scanner(fileName));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		
		np = p.parse();
		np.accept(visitor1);
		try
		{
			np.accept(visitor2);
			
		}
		catch(Exception e)
		{
			fail("Exception expected");
		}
		
		assertEquals(" _1 sa la _3 - sb lb la + _3.2 - sc la lb + lc - lc + se le p\n", readDoc());
	}
}