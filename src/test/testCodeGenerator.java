package test;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import ast.NodeProgram;
import parser.Parser;
import scanner.Scanner;
import visitor.CodeGenerator;
import visitor.TypeChecker;

public class testCodeGenerator
{
	private String dcOut = "./resources/dcOut";
	private String testFileName1 = "./resources/codeGenerator1";
	private BufferedReader reader;
	
	
	@Before
	public void writeFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer;
		
		writer = new PrintWriter(testFileName1, "UTF-8");
		//writer.write("i a\nf b\na = b + 3.2\npa ");
		writer.write("f a\na = 1 + 3.2\np a");
		writer.close();
	}
	
	@Test
	public void test1() throws Exception
	{
		Parser p = new Parser(new Scanner(testFileName1));
		NodeProgram np;
		TypeChecker visitor1 = new TypeChecker();
		CodeGenerator visitor2 = new CodeGenerator();
		reader = new BufferedReader(new FileReader(dcOut));
		String line, doc = "";
		
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
		
		line = reader.readLine();
		while(line != null)
		{
			doc += line + "\n";
			line = reader.readLine();
		}
		
		assertEquals(" 5k 1 3.2 + sa la p\n", doc);
	}
}
