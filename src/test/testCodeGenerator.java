package test;

import static org.junit.Assert.*;
import java.io.BufferedReader;
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
	private String testFileName1 = "./resources/codeGenerator1";
	private String testFileName2 = "./resources/codeGenerator2";
	private String testFileName3 = "./resources/codeGenerator3";
	private BufferedReader reader;
	
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(testFileName1, "UTF-8");
		writer.write("f a\na = 1 + 3.2\np a");
		writer.close();
		
		writer = new PrintWriter(testFileName2, "UTF-8");
		writer.write("f a\nf b\nb = 1\na = b + 3.2\np a ");
		writer.close();
		
		writer = new PrintWriter(testFileName3, "UTF-8");
		writer.write("f a\nf b\na = b + 3.2\np a ");
		writer.close();
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
	public void test1() throws Exception
	{
		Parser p = new Parser(new Scanner(testFileName1));
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
	public void test2() throws Exception
	{
		Parser p = new Parser(new Scanner(testFileName2));
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
			e.printStackTrace();
			fail("No exception expected");
		}
		
		assertEquals(" 1 sb lb 3.2 + sa la p\n", readDoc());
	}
	
	@Test
	public void test3() throws Exception
	{
		Parser p = new Parser(new Scanner(testFileName3));
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
}
